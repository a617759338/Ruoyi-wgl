package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysSeries;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface SysSeriesMapper
{
    /**
     * 查询职系列表
     */
    List<SysSeries> selectSysSeriesList(SysSeries sysSeries);

    /**
     * 根据id查询
     */
    SysSeries selectSysSeriesById(Long seriesId);

    /**
     * 根据编码查询
     */
    SysSeries selectSysSeriesByCode(String seriesCode);

    /**
     * 新增
     */
    int insertSysSeries(SysSeries sysSeries);

    /**
     * 修改
     */
    int updateSysSeries(SysSeries sysSeries);

    /**
     * 批量删除
     */
    int deleteSysSeriesByIds(@Param("ids") Long[] ids);
}
