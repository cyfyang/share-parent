package com.share.device.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.share.device.domain.PowerBank;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PowerBankMapper extends BaseMapper<PowerBank> {

    @Select({"<script>" +
            "select " +
            " id, power_bank_no, electricity, description, status, del_flag, create_by, create_time, update_by, update_time, remark " +
            "from power_bank where del_flag = 0 " +
            "<if test=\"powerBankNo != null  and powerBankNo != ''\"> and power_bank_no = #{powerBankNo}</if>\n" +
            "<if test=\"electricity != null \"> and electricity = #{electricity}</if>\n" +
            "<if test=\"description != null  and description != ''\"> and description = #{description}</if>\n" +
            "<if test=\"status != null  and status != ''\"> and status = #{status}</if>" +
            "</script>"})
    List<PowerBank> selectPowerBankList(PowerBank powerBank);
}
