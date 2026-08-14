package com.share.device.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.share.device.domain.Cabinet;
import com.share.device.domain.StationLocation;
import com.share.device.mapper.StationMapper;
import com.share.device.domain.Station;
import com.share.device.repository.StationLocationRepository;
import com.share.device.service.ICabinetService;
import com.share.device.service.IRegionService;
import com.share.device.service.IStationService;
import jakarta.annotation.Resource;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class StationServiceImpl extends ServiceImpl<StationMapper, Station> implements IStationService {

    @Resource
    private StationMapper stationMapper;

    @Resource
    private ICabinetService cabinetService;

    @Resource
    private IRegionService regionService;

    @Resource
    StationLocationRepository stationLocationRepository;

    @Override
    public List<Station> selectStationList(Station station) {
        List<Station> list = stationMapper.selectStationList(station);
        if(CollectionUtils.isNotEmpty(list)){
            list.forEach(item -> {
                Cabinet cabinet = cabinetService.getById(item.getCabinetId());
                item.setCabinetNo(cabinet != null ? cabinet.getCabinetNo() : null);
            });
        }
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveStation(Station station) {
        String provinceName = regionService.getNameByCode(station.getProvinceCode());
        String cityName = regionService.getNameByCode(station.getCityCode());
        String districtName = regionService.getNameByCode(station.getDistrictCode());
        station.setFullAddress(provinceName + cityName + districtName + station.getAddress());
        this.save(station);

        //保存站点信息到mongodb
        StationLocation stationLocation = new StationLocation();
        stationLocation.setId(ObjectId.get().toString());
        stationLocation.setStationId(station.getId());
        stationLocation.setLocation(new GeoJsonPoint(station.getLongitude().doubleValue(), station.getLatitude().doubleValue()));
        stationLocation.setCreateTime(new Date());
        stationLocationRepository.save(stationLocation);
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateStation(Station station) {
        String provinceName = regionService.getNameByCode(station.getProvinceCode());
        String cityName = regionService.getNameByCode(station.getCityCode());
        String districtName = regionService.getNameByCode(station.getDistrictCode());
        station.setFullAddress(provinceName + cityName + districtName + station.getAddress());
        this.updateById(station);

        StationLocation stationLocation = stationLocationRepository.getByStationId(station.getId());
        stationLocation.setLocation(new GeoJsonPoint(station.getLongitude().doubleValue(), station.getLatitude().doubleValue()));
        stationLocationRepository.save(stationLocation);
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int setData(Station station) {
        this.updateById(station);

        //更正柜机使用状态
        Cabinet cabinet = cabinetService.getById(station.getCabinetId());
        cabinet.setStatus("1");
        cabinetService.updateById(cabinet);
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeByIds(Collection<?> list) {
        for (Object id : list){
            stationLocationRepository.deleteByStationId(Long.parseLong(id.toString()) );
        }
        return super.removeByIds(list);
    }
}
