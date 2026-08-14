package com.share.device.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.share.device.domain.Station;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StationMapper extends BaseMapper<Station> {
    @Select({"<script>" +
            "SELECT id, name, image_url, business_hours, longitude, latitude, province_code, city_code, district_code,address, " +
            "full_address, head_name, head_phone, cabinet_id, fee_rule_id, status, create_time, create_by, update_time, " +
            "update_by, del_flag, remark FROM station WHERE del_flag = 0" +
            "<if test=\"name != null  and name != ''\"> and name like concat('%', #{name}, '%')</if>\n" +
            "<if test=\"provinceCode != null  and provinceCode != ''\"> and provinceCode = #{province_code}</if>\n" +
            "<if test=\"cityCode != null  and cityCode != ''\"> and cityCode = #{city_code}</if>\n" +
            "<if test=\"districtCode != null  and districtCode != ''\"> and districtCode = #{district_code}</if>\n" +
            "<if test=\"address != null  and address != ''\"> and address = #{address}</if>\n" +
            "<if test=\"headName != null  and headName != ''\"> and head_name like concat('%', #{headName}, '%')</if>\n" +
            "<if test=\"headPhone != null  and headPhone != ''\"> and head_phone = #{headPhone}</if>" +
            "</script>"})
    List<Station> selectStationList(Station station);
}
