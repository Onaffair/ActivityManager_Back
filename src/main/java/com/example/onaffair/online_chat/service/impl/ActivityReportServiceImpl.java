package com.example.onaffair.online_chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.onaffair.online_chat.dto.ReportHandleDTO;
import com.example.onaffair.online_chat.dto.ReportSubmitDTO;
import com.example.onaffair.online_chat.entity.ActivityReport;
import com.example.onaffair.online_chat.enums.HandleAction;
import com.example.onaffair.online_chat.enums.ReportStatus;
import com.example.onaffair.online_chat.enums.ReportType;
import com.example.onaffair.online_chat.mapper.ActivityReportMapper;
import com.example.onaffair.online_chat.service.ActivityReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class ActivityReportServiceImpl implements ActivityReportService {
    
    @Autowired
    private ActivityReportMapper activityReportMapper;
    
    @Override
    public List<ReportType> getReportTypes() {
        return Arrays.asList(ReportType.values());
    }
    
    @Override
    public boolean submitReport(ReportSubmitDTO reportSubmitDTO) {
        try {
            // 验证举报类型是否有效
            ReportType.getByValue(reportSubmitDTO.getReportType());
            
            ActivityReport report = new ActivityReport();
            report.setActivityId(reportSubmitDTO.getActivityId());
            report.setReporter(reportSubmitDTO.getReporter());
            report.setReportType(reportSubmitDTO.getReportType());
            report.setDescription(reportSubmitDTO.getDescription());
            report.setEvidenceImages(reportSubmitDTO.getEvidenceImages());
            report.setReportStatus(ReportStatus.PENDING.getValue());
            report.setCreatedAt(LocalDateTime.now());
            
            return activityReportMapper.insert(report) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public IPage<ActivityReport> getReportList(Page<ActivityReport> page, String status) {
        QueryWrapper<ActivityReport> queryWrapper = new QueryWrapper<>();
        
        if (StringUtils.hasText(status)) {
            queryWrapper.eq("report_status", status);
        }
        
        queryWrapper.orderByDesc("created_at");
        
        return activityReportMapper.selectPage(page, queryWrapper);
    }
    
    @Override
    public ActivityReport getReportDetail(Long reportId) {
        return activityReportMapper.selectById(reportId);
    }
    
    @Override
    public boolean handleReport(ReportHandleDTO reportHandleDTO) {
        try {
            // 验证处理措施是否有效
            HandleAction.getByValue(reportHandleDTO.getHandleAction());
            
            ActivityReport report = activityReportMapper.selectById(reportHandleDTO.getReportId());
            if (report == null) {
                return false;
            }
            
            // 更新举报处理信息
            report.setHandlerAccount(reportHandleDTO.getHandlerAccount());
            report.setHandleAction(reportHandleDTO.getHandleAction());
            report.setHandleComment(reportHandleDTO.getHandleComment());
            report.setHandledAt(LocalDateTime.now());
            
            // 根据处理措施设置举报状态
            if (HandleAction.ACCEPT.getValue().equals(reportHandleDTO.getHandleAction()) ||
                HandleAction.ACTIVITY_REMOVED.getValue().equals(reportHandleDTO.getHandleAction()) ||
                HandleAction.WARNING_SENT.getValue().equals(reportHandleDTO.getHandleAction()) ||
                HandleAction.USER_BANNED.getValue().equals(reportHandleDTO.getHandleAction())) {
                report.setReportStatus(ReportStatus.RESOLVED.getValue());
            } else if (HandleAction.REJECT.getValue().equals(reportHandleDTO.getHandleAction())) {
                report.setReportStatus(ReportStatus.REJECTED.getValue());
            }
            
            return activityReportMapper.updateById(report) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}