package com.example.onaffair.online_chat.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.onaffair.online_chat.dto.ReportHandleDTO;
import com.example.onaffair.online_chat.dto.ReportSubmitDTO;
import com.example.onaffair.online_chat.entity.ActivityReport;
import com.example.onaffair.online_chat.enums.ReportType;
import com.example.onaffair.online_chat.enums.ResultCode;
import com.example.onaffair.online_chat.service.ActivityReportService;
import com.example.onaffair.online_chat.util.Result;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
public class ActivityReportController {
    
    @Autowired
    private ActivityReportService activityReportService;
    
    /**
     * 获取举报类型列表
     * @return 举报类型列表
     */
    @GetMapping("/types")
    public Result<Map<String,Object>> getReportTypes() {
        try {
            Map<String,Object> res = new HashMap<>();
            List<ReportType> reportTypes = activityReportService.getReportTypes();
            reportTypes.forEach(item ->{
                res.put(item.getValue(),item.getName());
            });
            return Result.success(res);
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR,"获取举报类型失败: " + e.getMessage());
        }
    }

    
    /**
     * 提交活动举报
     * @param reportSubmitDTO 举报信息
     * @return 提交结果
     */
    @PostMapping("/submit")
    public Result<String> submitReport(@Valid @RequestBody ReportSubmitDTO reportSubmitDTO) {
        try {
            boolean success = activityReportService.submitReport(reportSubmitDTO);
            if (success) {
                return Result.success("举报提交成功");
            } else {
                return Result.error(ResultCode.ERROR,"举报提交失败");
            }
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR,"举报提交失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取举报列表（管理员功能）
     * @param current 当前页
     * @param size 每页大小
     * @param status 举报状态（可选）
     * @return 举报列表
     */
    @GetMapping("/list")
    public Result<IPage<ActivityReport>> getReportList(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String status) {
        try {
            Page<ActivityReport> page = new Page<>(current, size);
            IPage<ActivityReport> reportPage = activityReportService.getReportList(page, status);
            return Result.success(reportPage);
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR,"获取举报列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取举报详情（管理员功能）
     * @param reportId 举报ID
     * @return 举报详情
     */
    @GetMapping("/detail/{reportId}")
    public Result<ActivityReport> getReportDetail(@PathVariable Long reportId) {
        try {
            ActivityReport report = activityReportService.getReportDetail(reportId);
            if (report != null) {
                return Result.success(report);
            } else {
                return Result.error(ResultCode.ERROR,"举报不存在");
            }
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR,"获取举报详情失败: " + e.getMessage());
        }
    }
    
    /**
     * 处理举报（管理员功能）
     * @param reportHandleDTO 处理信息
     * @return 处理结果
     */
    @PostMapping("/handle")
    public Result<String> handleReport(@Valid @RequestBody ReportHandleDTO reportHandleDTO) {
        try {
            boolean success = activityReportService.handleReport(reportHandleDTO);
            if (success) {
                return Result.success("举报处理成功");
            } else {
                return Result.error(ResultCode.ERROR,"举报处理失败");
            }
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR,"举报处理失败: " + e.getMessage());
        }
    }
}