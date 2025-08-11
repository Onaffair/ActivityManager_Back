package com.example.onaffair.online_chat.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.onaffair.online_chat.entity.RepairRequest;
import com.example.onaffair.online_chat.mapper.RepairRequestMapper;
import com.example.onaffair.online_chat.service.RepairRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RepairRequestServiceImpl implements RepairRequestService {
    
    @Autowired
    private RepairRequestMapper repairRequestMapper;
    
    @Override
    public boolean submitRepairRequest(RepairRequest repairRequest) {
        repairRequest.setRequestStatus("pending");
        repairRequest.setCreatedAt(LocalDateTime.now());
        return repairRequestMapper.insert(repairRequest) > 0;
    }
    
    @Override
    public List<RepairRequest> getRepairRequestsByUser(String userAccount) {
        return repairRequestMapper.selectByUserAccount(userAccount);
    }
    
    @Override
    public RepairRequest getRepairRequestById(Long requestId) {
        return repairRequestMapper.selectById(requestId);
    }
    
    @Override
    public IPage<RepairRequest> getAllRepairRequests(Page<RepairRequest> page, String status) {
        return repairRequestMapper.selectByStatus(page, status);
    }
    
    @Override
    public boolean assignRepairTask(Long requestId, String technicianAccount) {
        RepairRequest repairRequest = repairRequestMapper.selectById(requestId);
        if (repairRequest != null && "pending".equals(repairRequest.getRequestStatus())) {
            repairRequest.setTechnicianAccount(technicianAccount);
            repairRequest.setRequestStatus("assigned");
            repairRequest.setAssignedAt(LocalDateTime.now());
            return repairRequestMapper.updateById(repairRequest) > 0;
        }
        return false;
    }
    
    @Override
    public boolean rejectRepairRequest(Long requestId) {
        RepairRequest repairRequest = repairRequestMapper.selectById(requestId);
        if (repairRequest != null && "pending".equals(repairRequest.getRequestStatus())) {
            repairRequest.setRequestStatus("rejected");
            return repairRequestMapper.updateById(repairRequest) > 0;
        }
        return false;
    }
    
    @Override
    public List<RepairRequest> getTasksByTechnician(String technicianAccount, String status) {
        return repairRequestMapper.selectByTechnicianAndStatus(technicianAccount, status);
    }
    
    @Override
    public boolean startRepairTask(Long requestId) {
        RepairRequest repairRequest = repairRequestMapper.selectById(requestId);
        if (repairRequest != null && "assigned".equals(repairRequest.getRequestStatus())) {
            repairRequest.setRequestStatus("in_progress");
            return repairRequestMapper.updateById(repairRequest) > 0;
        }
        return false;
    }
    
    @Override
    public boolean completeRepairTask(Long requestId) {
        RepairRequest repairRequest = repairRequestMapper.selectById(requestId);

        if (repairRequest != null) {
            repairRequest.setRequestStatus("completed");
            repairRequest.setCompletedAt(LocalDateTime.now());
            return repairRequestMapper.updateById(repairRequest) > 0;
        }
        return false;
    }
    
    @Override
    public boolean updateRepairRequest(RepairRequest repairRequest) {
        return repairRequestMapper.updateById(repairRequest) > 0;
    }
    
    @Override
    public boolean deleteRepairRequest(Long requestId) {
        return repairRequestMapper.deleteById(requestId) > 0;
    }
}