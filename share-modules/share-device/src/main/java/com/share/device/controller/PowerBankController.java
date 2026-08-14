package com.share.device.controller;

import com.share.common.core.web.controller.BaseController;
import com.share.common.core.web.domain.AjaxResult;
import com.share.common.core.web.page.TableDataInfo;
import com.share.common.security.utils.SecurityUtils;
import com.share.device.domain.PowerBank;
import com.share.device.service.IPowerBankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Tag(name = "充电宝管理", description = "充电宝管理")
@RestController
@RequestMapping("/powerBank")
public class PowerBankController extends BaseController {

    @Resource
    private IPowerBankService powerBankService;

    @Operation(summary = "查询充电宝列表")
    @GetMapping(value = "/list")
    public TableDataInfo list(PowerBank powerBank) {
        startPage();
        List<PowerBank> list = powerBankService.selectPowerBankList(powerBank);
        return getDataTable(list);
    }

    @Operation(summary = "查询充电宝详细")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(powerBankService.getById(id));
    }

    @Operation(summary = "新增充电宝")
    @PostMapping
    public AjaxResult add(@RequestBody @Validated PowerBank powerBank) {
        powerBank.setCreateBy(SecurityUtils.getUsername());
        powerBank.setCreateTime(new Date());
        return toAjax(powerBankService.savePowerBank(powerBank));
    }

    @Operation(summary = "修改充电宝柜机")
    @PutMapping
    public AjaxResult edit(@RequestBody @Validated PowerBank powerBank) {
        powerBank.setUpdateBy(SecurityUtils.getUsername());
        powerBank.setUpdateTime(new Date());
        return toAjax(powerBankService.updatePowerBank(powerBank));
    }

    @Operation(summary = "删除充电宝柜机")
    @DeleteMapping("{ids}")
    public AjaxResult delete(@PathVariable("ids") Long[] ids) {
        return toAjax(powerBankService.removeByIds(Arrays.asList(ids)));
    }
}
