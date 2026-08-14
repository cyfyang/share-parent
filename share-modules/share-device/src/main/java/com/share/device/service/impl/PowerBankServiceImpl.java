package com.share.device.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.share.common.core.exception.ServiceException;
import com.share.device.mapper.PowerBankMapper;
import com.share.device.domain.PowerBank;
import com.share.device.service.IPowerBankService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PowerBankServiceImpl extends ServiceImpl<PowerBankMapper, PowerBank> implements IPowerBankService {

    @Resource
    private PowerBankMapper powerBankMapper;
    @Override
    public List<PowerBank> selectPowerBankList(PowerBank powerBank) {
        return powerBankMapper.selectPowerBankList(powerBank);
    }

    @Override
    public int savePowerBank(PowerBank powerBank) {
        long count = this.count(new LambdaQueryWrapper<PowerBank>().eq(PowerBank::getPowerBankNo, powerBank.getPowerBankNo()));
        if (count > 0) {  //如果存在，则不添加
            throw new ServiceException("充电宝编号已存在");
        }

        return this.save(powerBank) ? 1 : 0;
    }

    @Override
    public int updatePowerBank(PowerBank powerBank) {
        PowerBank old = this.getById(powerBank.getId());
        if (old != null && "0".equals(old.getStatus())){
            return this.updateById(powerBank) ? 1 : 0;
        }
        return 0;
    }
}
