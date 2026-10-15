package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysSeries;
import java.util.List;

public interface ISysSeriesService
{
    /**
     * 查询职系列表
     */
    List<SysSeries> selectSysSeriesList(SysSeries sysSeries);

    /**
     * 根据id获取
     */
    SysSeries selectSysSeriesById(Long seriesId);

    /**
     * 新增职系
     */
    int insertSysSeries(SysSeries sysSeries);

    /**
     * 修改职系
     */
    int updateSysSeries(SysSeries sysSeries);

    /**
     * 删除职系
     */
    int deleteSysSeriesByIds(Long[] seriesIds);

    /**
     * 校验编码唯一
     */
    boolean checkSeriesCodeUnique(SysSeries sysSeries);

    // ========== 新增这个接口方法声明 ==========
    /**
     * 导入职系数据
     * @param seriesList 解析后的excel数据集合
     * @param updateSupport 是否支持更新已有数据
     * @param operName 操作人账号
     * @return 导入结果提示文本
     */
    String importSeries(List<SysSeries> seriesList, Boolean updateSupport, String operName);
}
