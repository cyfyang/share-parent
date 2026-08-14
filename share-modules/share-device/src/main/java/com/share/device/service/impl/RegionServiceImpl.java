package com.share.device.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.share.device.domain.Region;
import com.share.device.mapper.RegionMapper;
import com.share.device.service.IRegionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements IRegionService {
    @Override
    public List<Region> treeSelect(String code) {
        List<Region> list = this.list(new LambdaQueryWrapper<Region>().eq(Region::getParentCode, code));
        if(CollectionUtils.isNotEmpty(list)){
            list.forEach(item->{
                long count = this.count(new LambdaQueryWrapper<Region>().eq(Region::getParentCode, item.getCode()));
                if(count > 0){
                    item.setHasChildren(true);
                }else{
                    item.setHasChildren(false);
                }
            });
        }
        return list;
    }

    @Override
    public String getNameByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return "";
        }
        Region region = this.getOne(new LambdaQueryWrapper<Region>().eq(Region::getCode,code).select(Region::getName));
        if(null != region) {
            return region.getName();
        }
        return "";
    }
}
