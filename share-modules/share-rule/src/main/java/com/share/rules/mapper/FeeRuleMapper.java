package com.share.rules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.share.rules.domain.FeeRule;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FeeRuleMapper extends BaseMapper<FeeRule> {

    @Select({"<script>" +
            "select id, name, rule, description, status, create_time, create_by, update_time, update_by, del_flag, remark " +
            "from fee_rule where del_flag = 0" +
            "<if test=\"name != null  and name != ''\"> and name like concat('%', #{name}, '%')</if> " +
            "<if test=\"status != null  and status != ''\"> and status = #{status}</if>" +
            "</script>"})
    List<FeeRule> selectFeeRuleList(FeeRule feeRule);
}
