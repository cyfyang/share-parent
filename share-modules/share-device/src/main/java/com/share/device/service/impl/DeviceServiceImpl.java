package com.share.device.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.share.common.core.utils.bean.BeanUtils;
import com.share.device.domain.Cabinet;
import com.share.device.domain.Station;
import com.share.device.domain.StationLocation;
import com.share.device.domain.StationVo;
import com.share.device.service.ICabinetService;
import com.share.device.service.IDeviceService;
import com.share.device.service.IStationService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class DeviceServiceImpl implements IDeviceService {

    @Resource
    private IStationService stationService;

    @Resource
    private ICabinetService cabinetService;
    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 获取附近站点信息列表
     * @param latitude
     * @param longitude
     * @return
     */
    @Override
    public List<StationVo> nearbyStation(String latitude, String longitude) {
        //确定中心点 经度、纬度
        GeoJsonPoint center = new GeoJsonPoint(Double.parseDouble(longitude), Double.parseDouble(latitude));
        //设置查询半径，比如查询50公里
        Distance distance = new Distance(50, Metrics.KILOMETERS);

        //画圆
        Circle circle = new Circle(center, distance);

        //条件排除自己
        //查询mongoDB数据
        Query query = new Query(Criteria.where("location").within(circle));
        List<StationLocation> stationLocations = this.mongoTemplate.find(query, StationLocation.class);
        if(CollectionUtils.isEmpty(stationLocations)) return null;

        List<Long> stationIdList = stationLocations.stream().map(StationLocation::getStationId).collect(Collectors.toList());
        //获取站点列表信息
        List<Station> stationList = stationService.list(new LambdaQueryWrapper<Station>().in(Station::getId, stationIdList).isNotNull(Station::getCabinetId));

        //查询其他需要数据，进行封装
        //根据mongoDB查询结果获取站点其他数据
        List<Long> cabinetIdList = stationList.stream().map(Station::getCabinetId).collect(Collectors.toList());
        Map<Long, Cabinet> cabinetMap = cabinetService.listByIds(cabinetIdList).stream().collect(Collectors.toMap(Cabinet::getId, cabinet -> cabinet));

        List<StationVo> stationVoList = new ArrayList<>();
        stationList.stream().forEach(item ->{
            StationVo vo= new StationVo();
            BeanUtils.copyBeanProp(item,vo);

            Cabinet cabinet = cabinetMap.get(item.getCabinetId());
            //可用充电宝数量大于0，可借用
            if(cabinet.getAvailableNum() > 0) {
                vo.setIsUsable("1");
            } else {
                vo.setIsUsable("0");
            }
            // 获取空闲插槽数量大于0，可归还
            if (cabinet.getFreeSlots() > 0) {
                vo.setIsReturn("1");
            } else {
                vo.setIsReturn("0");
            }
            stationVoList.add(vo);
        });
        return stationVoList;
    }
}
