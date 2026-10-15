package com.ruoyi.system.service.impl;


import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.domain.SysSeries;
import com.ruoyi.system.mapper.SysSeriesMapper;
import com.ruoyi.system.service.ISysSeriesService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class SysSeriesServiceImpl implements ISysSeriesService
{
    @Resource
    private SysSeriesMapper sysSeriesMapper;

    /** 系统内置不可删除编码 M T S F O */
    private static final List<String> INNER_CODES = Arrays.asList("M","T","S","F","O");

    @Override
    public List<SysSeries> selectSysSeriesList(SysSeries sysSeries)
    {
        return sysSeriesMapper.selectSysSeriesList(sysSeries);
    }

    @Override
    public SysSeries selectSysSeriesById(Long seriesId)
    {
        return sysSeriesMapper.selectSysSeriesById(seriesId);
    }

    @Override
    public int insertSysSeries(SysSeries sysSeries)
    {
        if(!checkSeriesCodeUnique(sysSeries)){
            throw new ServiceException("职系编码已存在");
        }
        return sysSeriesMapper.insertSysSeries(sysSeries);
    }

    @Override
    public int updateSysSeries(SysSeries sysSeries)
    {
        if(!checkSeriesCodeUnique(sysSeries)){
            throw new ServiceException("职系编码已存在");
        }
        return sysSeriesMapper.updateSysSeries(sysSeries);
    }

    @Override
    public int deleteSysSeriesByIds(Long[] seriesIds)
    {
        for(Long id : seriesIds){
            SysSeries series = sysSeriesMapper.selectSysSeriesById(id);
            if(series == null){
                continue;
            }
            // 内置职系禁止删除
            if(INNER_CODES.contains(series.getSeriesCode())){
                throw new ServiceException("内置职系【"+series.getSeriesName()+"】不允许删除，可执行停用");
            }
            // TODO 这里增加业务引用校验：判断是否被职种/员工/岗位使用，如果被使用抛出异常禁止删除
            // boolean used = xxxMapper.countBySeriesCode(series.getSeriesCode()) >0;
            // if(used) throw new ServiceException("职系已被业务引用，不能删除");
        }
        return sysSeriesMapper.deleteSysSeriesByIds(seriesIds);
    }

    @Override
    public boolean checkSeriesCodeUnique(SysSeries sysSeries)
    {
        Long seriesId = StringUtils.isNull(sysSeries.getSeriesId()) ? -1L : sysSeries.getSeriesId();
        SysSeries info = sysSeriesMapper.selectSysSeriesByCode(sysSeries.getSeriesCode());
        if(info != null && info.getSeriesId().longValue() != seriesId.longValue()){
            return false;
        }
        return true;
    }

    @Override
    public String importSeries(List<SysSeries> seriesList, Boolean updateSupport, String operName)
    {
        if (StringUtils.isNull(seriesList) || seriesList.size() == 0)
        {
            throw new ServiceException("导入职系数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (SysSeries series : seriesList)
        {
            try
            {
                // 校验必填
                if (StringUtils.isEmpty(series.getSeriesCode()) || StringUtils.isEmpty(series.getSeriesName()))
                {
                    failureNum++;
                    failureMsg.append("<br/>第" + failureNum + "行：职系编码或名称不能为空");
                    continue;
                }
                // 根据职系编码查询数据库记录
                SysSeries existing = sysSeriesMapper.selectSysSeriesByCode(series.getSeriesCode());
                if (StringUtils.isNull(existing))
                {
                    // 编码不存在 → 新增
                    series.setCreateBy(operName);
                    sysSeriesMapper.insertSysSeries(series);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、职系 " + series.getSeriesName() + " 导入成功");
                }
                else
                {
                    // ✅编码相同，【自动覆盖更新，不再判断updateSupport】
                    series.setSeriesId(existing.getSeriesId());
                    series.setUpdateBy(operName);
                    sysSeriesMapper.updateSysSeries(series);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、职系编码["+series.getSeriesCode()+"]已存在，执行覆盖更新");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、职系 " + (series.getSeriesName()!=null?series.getSeriesName():"") + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }

        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }


}
