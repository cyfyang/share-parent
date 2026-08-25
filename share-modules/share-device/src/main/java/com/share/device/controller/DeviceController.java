package com.share.device.controller;

import com.share.common.core.web.controller.BaseController;
import com.share.common.core.web.domain.AjaxResult;
import com.share.device.domain.StationVo;
import com.share.device.service.IDeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "设备接口管理")
@RestController
@RequestMapping("/device")
public class DeviceController extends BaseController {

    @Resource
    private IDeviceService deviceService;

    @Operation(summary = "查询附近站点信息列表")
    @GetMapping("/nearbyStation/{latitude}/{longitude}")
    public AjaxResult nearbyStation(@PathVariable String latitude, @PathVariable String longitude) {

        List<StationVo> list = deviceService.nearbyStation(latitude, longitude);
        return AjaxResult.success(list);
    }


}
