package com.example.onaffair.online_chat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.onaffair.online_chat.entity.RepairRequest;

import java.util.List;

public interface RepairRequestService {
    
    /**
     * 提交维修请求
     */
    boolean submitRepairRequest(RepairRequest repairRequest);
    
    /**
     * 根据用户账号获取维修请求列表
     */
    List<RepairRequest> getRepairRequestsByUser(String userAccount);
    
    /**
     * 根据ID获取维修请求详情
     */
    RepairRequest getRepairRequestById(Long requestId);
    
    /**
     * 获取所有维修请求（管理员）
     */
    IPage<RepairRequest> getAllRepairRequests(Page<RepairRequest> page, String status);
    
    /**
     * 分配维修任务给维修员
     */
    boolean assignRepairTask(Long requestId, String technicianAccount);
    
    /**
     * 拒绝维修请求
     */
    boolean rejectRepairRequest(Long requestId);
    
    /**
     * 根据维修员账号获取任务列表
     */
    List<RepairRequest> getTasksByTechnician(String technicianAccount, String status);
    
    /**
     * 维修员开始任务
     */
    boolean startRepairTask(Long requestId);
    
    /**
     * 维修员完成任务
     */
    boolean completeRepairTask(Long requestId);
    
    /**
     * 更新维修请求
     */
    boolean updateRepairRequest(RepairRequest repairRequest);
    
    /**
     * 删除维修请求
     */
    boolean deleteRepairRequest(Long requestId);
}