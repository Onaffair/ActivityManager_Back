package com.example.onaffair.online_chat.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.onaffair.online_chat.entity.RepairRequest;
import com.example.onaffair.online_chat.entity.ServiceReview;
import com.example.onaffair.online_chat.enums.ResultCode;
import com.example.onaffair.online_chat.service.RepairRequestService;
import com.example.onaffair.online_chat.service.ServiceReviewService;
import com.example.onaffair.online_chat.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/repair")
public class RepairController {
    
    @Autowired
    private RepairRequestService repairRequestService;
    
    @Autowired
    private ServiceReviewService serviceReviewService;
    
    /**
     * 用户提交维修请求
     */
    @PostMapping("/submit")
    public Result<Boolean> submitRepairRequest(@RequestBody RepairRequest repairRequest) {
        try {
            boolean success = repairRequestService.submitRepairRequest(repairRequest);

            if (success) {
                return Result.success(true);
            } else {
                return Result.error(ResultCode.ERROR, "提交维修请求失败");
            }
        } catch (Exception e) {
            log.error("提交维修请求失败", e);
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }
    
    /**
     * 获取用户的维修请求列表
     */
    @GetMapping("/user/list")
    public Result<List<RepairRequest>> getUserRepairRequests(@RequestParam String userAccount) {
        try {
            List<RepairRequest> requests = repairRequestService.getRepairRequestsByUser(userAccount);
            return Result.success(requests);
        } catch (Exception e) {
            log.error("获取用户维修请求列表失败", e);
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }
    
    /**
     * 获取维修请求详情
     */
    @GetMapping("/detail/{requestId}")
    public Result<RepairRequest> getRepairRequestDetail(@PathVariable Long requestId) {
        try {
            RepairRequest request = repairRequestService.getRepairRequestById(requestId);
            if (request != null) {
                return Result.success(request);
            } else {
                return Result.error(ResultCode.ERROR, "维修请求不存在");
            }
        } catch (Exception e) {
            log.error("获取维修请求详情失败", e);
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }
    
    /**
     * 用户提交服务评价
     */
    @PostMapping("/review")
    public Result<Boolean> submitServiceReview(@RequestBody ServiceReview serviceReview) {
        try {
            boolean success = serviceReviewService.submitServiceReview(serviceReview);
            if (success) {
                return Result.success(true);
            } else {
                return Result.error(ResultCode.ERROR, "提交评价失败");
            }
        } catch (Exception e) {
            log.error("提交服务评价失败", e);
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }
    
    /**
     * 获取维修员的任务列表
     */
    @GetMapping("/technician/tasks")
    public Result<List<RepairRequest>> getTechnicianTasks(
            @RequestParam String technicianAccount,
            @RequestParam(required = false) String status) {
        try {
            List<RepairRequest> tasks = repairRequestService.getTasksByTechnician(technicianAccount, status);
            return Result.success(tasks);
        } catch (Exception e) {
            log.error("获取维修员任务列表失败", e);
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }
    
    /**
     * 维修员开始任务
     */
    @PostMapping("/technician/start/{requestId}")
    public Result<Boolean> startRepairTask(@PathVariable Long requestId) {
        try {
            boolean success = repairRequestService.startRepairTask(requestId);
            if (success) {
                return Result.success(true);
            } else {
                return Result.error(ResultCode.ERROR, "开始任务失败");
            }
        } catch (Exception e) {
            log.error("开始维修任务失败", e);
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }
    
    /**
     * 维修员完成任务
     */
    @PostMapping("/technician/complete/{requestId}")
    public Result<Boolean> completeRepairTask(@PathVariable Long requestId) {
        try {
            boolean success = repairRequestService.completeRepairTask(requestId);
            if (success) {
                return Result.success(true);
            } else {
                return Result.error(ResultCode.ERROR, "完成任务失败");
            }
        } catch (Exception e) {
            log.error("完成维修任务失败", e);
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }
    
    /**
     * 获取服务评价列表
     */
    @GetMapping("/reviews")
    public Result<IPage<ServiceReview>> getServiceReviews(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        try {
            Page<ServiceReview> page = new Page<>(current, size);
            IPage<ServiceReview> reviews = serviceReviewService.getAllReviews(page);
            return Result.success(reviews);
        } catch (Exception e) {
            log.error("获取服务评价列表失败", e);
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }
}