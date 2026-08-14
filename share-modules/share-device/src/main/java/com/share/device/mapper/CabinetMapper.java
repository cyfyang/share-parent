package com.share.device.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.share.device.domain.Cabinet;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CabinetMapper extends BaseMapper<Cabinet> {

    @Select({"<script>" +
            "select c.id, c.cabinet_no, c.name, c.cabinet_type_id, c.total_slots, c.free_slots, c.used_slots, " +
            " c.available_num, c.description, c.location_id, c.status, c.del_flag, c.create_by, c.create_time, " +
            " c.update_by, c.update_time, c.remark, ct.name as cabinet_type_name " +
            " from cabinet c left join cabinet_type ct on c.cabinet_type_id = ct.id" +
            " WHERE c.del_flag = 0 and ct.del_flag = 0 " +
            "<if test=\"cabinet.cabinetNo != null  and cabinet.cabinetNo != ''\"> and c.cabinet_no = #{cabinet.cabinetNo}</if>\n" +
            "<if test=\"cabinet.name != null  and cabinet.name != ''\"> and c.name like concat('%', #{cabinet.name}, '%')</if>\n" +
            "<if test=\"cabinet.cabinetTypeId != null \"> and c.cabinet_type_id = #{cabinet.cabinetTypeId}</if>\n" +
            "<if test=\"cabinet.status != null  and cabinet.status != ''\"> and c.status = #{cabinet.status}</if>" +
            "</script>"})
    List<Cabinet> selectCabinetList(@Param("cabinet") Cabinet cabinet);
}
