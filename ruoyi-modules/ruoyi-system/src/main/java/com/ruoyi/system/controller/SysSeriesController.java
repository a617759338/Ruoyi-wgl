package com.ruoyi.system.controller;

import java.util.List;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.utils.SecurityUtils;

import com.ruoyi.system.domain.SysSeries;
import com.ruoyi.system.service.ISysSeriesService;

import javax.servlet.http.HttpServletResponse;

/**
 * 职系管理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/series")
public class SysSeriesController extends BaseController
{
    @Autowired
    private ISysSeriesService sysSeriesService;

    /**
     * 获取职系列表
     */
    @RequiresPermissions("system:series:list")
    @GetMapping("/list")
    public AjaxResult list(SysSeries sysSeries)
    {
        List<SysSeries> list = sysSeriesService.selectSysSeriesList(sysSeries);
        return success(list);
    }

    // 导出接口
    @RequiresPermissions("system:series:export")
    @Log(title = "职系管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysSeries sysSeries)
    {
        List<SysSeries> list = sysSeriesService.selectSysSeriesList(sysSeries);
        ExcelUtil<SysSeries> util = new ExcelUtil<>(SysSeries.class);
        util.exportExcel(response, list, "职系数据");
    }

    // 下载导入模板
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<SysSeries> util = new ExcelUtil<>(SysSeries.class);
        util.importTemplateExcel(response, "职系数据");
    }

    // 导入数据接口
    @RequiresPermissions("system:series:import")
    @Log(title = "职系管理", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SysSeries> util = new ExcelUtil<>(SysSeries.class);
        // 内部执行源码中 importExcel(inputStream) 方法，解析为List<SysSeries>
        List<SysSeries> seriesList = util.importExcel(file.getInputStream());
        String message = sysSeriesService.importSeries(seriesList, updateSupport, SecurityUtils.getUsername());
        return success(message);
    }

    /**
     * 根据职系编号获取详细信息
     */
    @RequiresPermissions("system:series:query")
    @GetMapping(value = "/{seriesId}")
    public AjaxResult getInfo(@PathVariable Long seriesId)
    {
        return success(sysSeriesService.selectSysSeriesById(seriesId));
    }

    /**
     * 新增职系
     */
    @RequiresPermissions("system:series:add")
    @Log(title = "职系管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysSeries sysSeries)
    {
        if (!sysSeriesService.checkSeriesCodeUnique(sysSeries))
        {
            return error("新增职系'" + sysSeries.getSeriesName() + "'失败，职系编码已存在");
        }
        sysSeries.setCreateBy(SecurityUtils.getUsername());
        return toAjax(sysSeriesService.insertSysSeries(sysSeries));
    }

    /**
     * 修改职系
     */
    @RequiresPermissions("system:series:edit")
    @Log(title = "职系管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysSeries sysSeries)
    {
        if (!sysSeriesService.checkSeriesCodeUnique(sysSeries))
        {
            return error("修改职系'" + sysSeries.getSeriesName() + "'失败，职系编码已存在");
        }
        sysSeries.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(sysSeriesService.updateSysSeries(sysSeries));
    }

    /**
     * 删除职系
     */
    @RequiresPermissions("system:series:remove")
    @Log(title = "职系管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{seriesIds}")
    public AjaxResult remove(@PathVariable Long[] seriesIds)
    {
        return toAjax(sysSeriesService.deleteSysSeriesByIds(seriesIds));
    }



}
