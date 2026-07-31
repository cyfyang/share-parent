package com.share.device.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.share.device.domain.CabinetType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CabinetTypeMapper extends BaseMapper<CabinetType> {
    @Select({"<script>SELECT id, name, total_slots, description, status, del_flag, create_by, create_time, update_by, update_time, remark " +
            "FROM cabinet_type WHERE del_flag = 0 " +
            "<if test='cabinetType.name != null and cabinetType.name != \"\"'>" +
            " AND name LIKE CONCAT('%', #{cabinetType.name}, '%') " +
            "</if>" +
            "<if test='cabinetType.totalSlots != null '>" +
            " AND total_slots = #{cabinetType.totalSlots} " +
            "</if>" +
            "<if test='cabinetType.description != null and cabinetType.description != \"\"'>" +
            " AND description LIKE CONCAT('%', #{cabinetType.description}, '%') " +
            "</if>" +
            "<if test='cabinetType.status != null and cabinetType.status != \"\"'>" +
            " AND status = #{cabinetType.status} " +
            "</if>" +
            "ORDER BY create_time DESC</script>"})
    List<CabinetType> selectCabinetTypeList(@Param("cabinetType") CabinetType cabinetType);
}
