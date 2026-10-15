package com.ruoyi.system.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 职系 sys_series
 */
public class SysSeries extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键（不参与导入模板，导出可选） */
    @Excel(name = "职系ID", type = Excel.Type.EXPORT)
    private Long seriesId;

    /** 职系编码 M/T/S/F/O */
    @Excel(name = "职系编码", sort = 1)
    private String seriesCode;

    /** 职系名称 */
    @Excel(name = "职系名称", sort = 2)
    private String seriesName;

    /** 排序 */
    @Excel(name = "排序", sort = 3)
    private Integer sortNum;

    /** 状态 0正常 1停用 */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用", sort = 4)
    private String status;

    /** 备注 */
    @Excel(name = "备注", sort = 5)
    private String remark;

    public Long getSeriesId()
    {
        return seriesId;
    }

    public void setSeriesId(Long seriesId)
    {
        this.seriesId = seriesId;
    }

    public String getSeriesCode()
    {
        return seriesCode;
    }

    public void setSeriesCode(String seriesCode)
    {
        this.seriesCode = seriesCode;
    }

    public String getSeriesName()
    {
        return seriesName;
    }

    public void setSeriesName(String seriesName)
    {
        this.seriesName = seriesName;
    }

    public Integer getSortNum()
    {
        return sortNum;
    }

    public void setSortNum(Integer sortNum)
    {
        this.sortNum = sortNum;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("seriesId", getSeriesId())
                .append("seriesCode", getSeriesCode())
                .append("seriesName", getSeriesName())
                .append("sortNum", getSortNum())
                .append("status", getStatus())
                .append("remark", getRemark())
                .toString();
    }
}
