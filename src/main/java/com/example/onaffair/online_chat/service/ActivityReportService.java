package com.example.onaffair.online_chat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.onaffair.online_chat.dto.ReportHandleDTO;
import com.example.onaffair.online_chat.dto.ReportSubmitDTO;
import com.example.onaffair.online_chat.entity.ActivityReport;
import com.example.onaffair.online_chat.enums.ReportType;

import java.util.List;

public interface ActivityReportService {
    
    /**
     * 获取所有举报类型
     * @return 举报类型列表
     */
    List<ReportType> getReportTypes();
    
    /**
     * 提交举报
     * @param reportSubmitDTO 举报信息
     * @return 是否成功
     */
    boolean submitReport(ReportSubmitDTO reportSubmitDTO);
    
    /**
     * 分页获取举报列表（管理员功能）
     * @param page 分页参数
     * @param status 举报状态（可选）
     * @return 举报列表
     */
    IPage<ActivityReport> getReportList(Page<ActivityReport> page, String status);
    
    /**
     * 获取举报详情（管理员功能）
     * @param reportId 举报ID
     * @return 举报详情
     */
    ActivityReport getReportDetail(Long reportId);
    
    /**
     * 处理举报（管理员功能）
     * @param reportHandleDTO 处理信息
     * @return 是否成功
     */
    boolean handleReport(ReportHandleDTO reportHandleDTO);
}