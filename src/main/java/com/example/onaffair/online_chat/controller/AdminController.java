package com.example.onaffair.online_chat.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.onaffair.online_chat.dto.GroupResponse;
import com.example.onaffair.online_chat.dto.ReportHandleDTO;
import com.example.onaffair.online_chat.dto.UserInfoResponse;
import com.example.onaffair.online_chat.entity.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.onaffair.online_chat.enums.ResultCode;
import com.example.onaffair.online_chat.service.*;
import com.example.onaffair.online_chat.service.AnnouncementService;
import com.example.onaffair.online_chat.entity.RepairRequest;
import com.example.onaffair.online_chat.entity.ServiceReview;
import com.example.onaffair.online_chat.entity.User;
import com.example.onaffair.online_chat.entity.Announcement;

import com.example.onaffair.online_chat.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")


public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private GroupMessageService groupMessageService;

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private ActivityCommentService activityCommentService;

    @Autowired
    private ActivityReportService activityReportService;
    
    @Autowired
    private RepairRequestService repairRequestService;
    
    @Autowired
    private AnnouncementService announcementService;
    
    @Autowired
    private ServiceReviewService serviceReviewService;

    @GetMapping("/test")
    public String test(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("当前用户权限: " + auth.getAuthorities());
        return "admin test";
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @PostMapping("/updateUser")
    public Boolean updateUser(@RequestBody  User user){
        return userService.updateUser(user.getAccount(),user);
    }

    @GetMapping("/getAllActivities")
    public List<Activity> getAllActivities(){
        return activityService.getAllActivities();
    }
    @PostMapping("/updateActivity")
    public Boolean updateActivity(@RequestBody  Activity activity){
        return activityService.updateActivity(activity);
    }


    @GetMapping("/getAllGroups")
    public List<GroupResponse> getAllGroups(){
        try {
            List<Group> allGroups = groupService.getAllGroups();
            List<GroupResponse> groupResponses = new ArrayList<>();

            allGroups.forEach(group ->{
                List<GroupMember> groupMemberList = groupMemberService.getGroupMemberList(group.getGroupId());
                GroupResponse groupResponse = new GroupResponse() {{
                    setGroup(group);
                    List<String> userList = groupMemberService.getGroupMemberList(group.getGroupId()).stream().map(GroupMember::getUserAccount).toList();
                    List<UserInfoResponse> userInfo = userService.getUserInfo(userList);
                    setMembers(userInfo);
                }};
                groupResponses.add(groupResponse);
            });
            return groupResponses;
        }catch (Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }

    }

    @DeleteMapping("/delete-group")
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> deleteGroup(@RequestParam("groupId") Integer groupId) {
        try {
            Group group = groupService.getGroupById(groupId);
            if (group == null) {
                throw new RuntimeException("Group not found");
            }

            Integer activityId = group.getActivityId();

            if (!groupMemberService.deleteGroupMemberByGroupId(groupId)) {
                throw new RuntimeException("Failed to delete group members");
            }

            if (!groupMessageService.deleteGroupMessageByGroupId(groupId)) {
                throw new RuntimeException("Failed to delete group messages");
            }

            if (!activityCommentService.deleteCommentByActivityID(activityId)) {
                throw new RuntimeException("Failed to delete activity comments");
            }

            if (!activityService.deleteActivityParticipationByActivityId(activityId)) {
                throw new RuntimeException("Failed to delete activity participation");
            }

            if (!groupService.deleteGroup(groupId)) {
                throw new RuntimeException("Failed to delete group");
            }

            if (!activityService.deleteActivity(activityId)) {
                throw new RuntimeException("Failed to delete activity");
            }

            return Result.success(true);
        } catch (Exception e) {
            // Transaction will automatically rollback due to @Transactional
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/delete-group-member")
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> deleteGroupMember(@RequestParam("groupId") Integer groupId, 
                                           @RequestParam("userAccount") String userAccount) {
        try {
            Group group = groupService.getGroupById(groupId);
            if (group == null) {
                throw new RuntimeException("群组不存在");
            }

            // 从群组中移除成员
            GroupMember groupMember = new GroupMember() {{
                setGroupId(groupId);
                setUserAccount(userAccount);
            }};
            
            if (!groupMemberService.removeGroupMember(groupMember)) {
                throw new RuntimeException("移除群组成员失败");
            }

            // 取消该成员对相关活动的报名
            UserParticipation userParticipation = new UserParticipation() {{
                setActivityId(group.getActivityId());
                setUserAccount(userAccount);
            }};
            
            if (!activityService.cancelActivityJoin(userParticipation)) {
                throw new RuntimeException("取消活动报名失败");
            }

            return Result.success(true);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }

    /**
     * 获取举报列表（管理员功能）
     * @param current 当前页
     * @param size 每页大小
     * @param status 举报状态（可选）
     * @return 举报列表
     */
    @GetMapping("/report/list")
    public Result<IPage<ActivityReport>> getReportList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String status) {
        try {
            Page<ActivityReport> page = new Page<>(current, size);
            IPage<ActivityReport> reportList = activityReportService.getReportList( page,status);
            return Result.success(reportList);
        } catch (Exception e) {
            log.error("获取举报列表失败", e);
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }

    /**
     * 获取举报详情（管理员功能）
     * @param reportId 举报ID
     * @return 举报详情
     */
    @GetMapping("/report/detail/{reportId}")
    public Result<ActivityReport> getReportDetail(@PathVariable Integer reportId) {
        try {
            ActivityReport reportDetail = activityReportService.getReportDetail(Long.valueOf(reportId));
            if (reportDetail == null) {
                return Result.error(ResultCode.ERROR, "举报记录不存在");
            }
            return Result.success(reportDetail);
        } catch (Exception e) {
            log.error("获取举报详情失败", e);
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }

    /**
     * 处理举报（管理员功能）
     * @param handleReportDTO 处理举报请求
     * @return 处理结果
     */
    @PostMapping("/report/handle")
    public Result<Boolean> handleReport(@RequestBody ReportHandleDTO handleReportDTO) {
        try {
            boolean result = activityReportService.handleReport(handleReportDTO);
            if (result) {
                return Result.success(true);
            } else {
                return Result.error(ResultCode.ERROR, "处理举报失败");
            }
        } catch (Exception e) {
            log.error("处理举报失败", e);
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }

    /**
     * 获取所有维修请求（管理员功能）
     * @param current 当前页
     * @param size 每页大小
     * @param status 请求状态（可选）
     * @return 维修请求列表
     */
    @GetMapping("/repair/list")
    public Result<IPage<RepairRequest>> getAllRepairRequests(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String status) {
        try {
            Page<RepairRequest> page = new Page<>(current, size);
            IPage<RepairRequest> repairList = repairRequestService.getAllRepairRequests(page, status);
            return Result.success(repairList);
        } catch (Exception e) {
            log.error("获取维修请求列表失败", e);
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }

    /**
     * 分配维修任务给维修员（管理员功能）
     * @param requestId 维修请求ID
     * @param technicianAccount 维修员账号
     * @return 分配结果
     */
    @PostMapping("/repair/assign")
     public Result<Boolean> assignRepairTask(@RequestBody java.util.Map<String, Object> request) {
         Long requestId = Long.valueOf(request.get("requestId").toString());
         String technicianAccount = request.get("technicianAccount").toString();
        try {
            boolean result = repairRequestService.assignRepairTask(requestId, technicianAccount);
            if (result) {
                return Result.success(true);
            } else {
                return Result.error(ResultCode.ERROR, "分配维修任务失败");
            }
        } catch (Exception e) {
            log.error("分配维修任务失败", e);
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }

    /**
     * 拒绝维修请求（管理员功能）
     * @param requestId 维修请求ID
     * @return 拒绝结果
     */
    @PostMapping("/repair/reject")
     public Result<Boolean> rejectRepairRequest(@RequestBody java.util.Map<String, Object> request) {
         Long requestId = Long.valueOf(request.get("requestId").toString());
        try {
            boolean result = repairRequestService.rejectRepairRequest(requestId);
            if (result) {
                return Result.success(true);
            } else {
                return Result.error(ResultCode.ERROR, "拒绝维修请求失败");
            }
        } catch (Exception e) {
            log.error("拒绝维修请求失败", e);
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }

    /**
     * 获取所有维修员列表（管理员功能）
     * @return 维修员列表
     */
    @GetMapping("/repair/technicians")
    public Result<List<User>> getAllTechnicians() {
        try {
            List<User> technicians = userService.getUsersByRole(2); // role=2表示维修员
            return Result.success(technicians);
        } catch (Exception e) {
            log.error("获取维修员列表失败", e);
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }

    /**
     * 获取服务评价列表（管理员功能）
     * @param current 当前页
     * @param size 每页大小
     * @return 评价列表
     */
    @GetMapping("/repair/reviews")
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
    
    // ==================== 公告管理相关接口 ====================
    
    /**
     * 管理员获取所有公告
     * @return 公告列表
     */
    @GetMapping("/announcements")
    public Result<List<Announcement>> getAllAnnouncements() {
        try {
            List<Announcement> announcements = announcementService.getAllAnnouncements();
            return Result.success(announcements);
        } catch (Exception e) {
            log.error("获取公告列表失败", e);
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }
    
    /**
     * 管理员创建公告
     * @param announcement 公告信息
     * @return 创建结果
     */
    @PostMapping("/announcements")
    public Result<Announcement> createAnnouncement(@RequestBody Announcement announcement) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String adminAccount = authentication.getName();
            
            Announcement createdAnnouncement = announcementService.createAnnouncement(announcement, adminAccount);
            return Result.success(createdAnnouncement);
        } catch (Exception e) {
            log.error("创建公告失败", e);
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }
    
    /**
     * 管理员更新公告
     * @param announcementId 公告ID
     * @param announcement 公告信息
     * @return 更新结果
     */
    @PutMapping("/announcements/{announcementId}")
    public Result<Announcement> updateAnnouncement(
            @PathVariable String announcementId,
            @RequestBody Announcement announcement) {
        try {
            announcement.setAnnouncementId(announcementId);
            Announcement updatedAnnouncement = announcementService.updateAnnouncement(announcement);
            return Result.success(updatedAnnouncement);
        } catch (Exception e) {
            log.error("更新公告失败", e);
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }
    
    /**
     * 管理员删除公告
     * @param announcementId 公告ID
     * @return 删除结果
     */
    @DeleteMapping("/announcements/{announcementId}")
    public Result<Boolean> deleteAnnouncement(@PathVariable String announcementId) {
        try {
            boolean deleted = announcementService.deleteAnnouncement(announcementId);
            return Result.success(deleted);
        } catch (Exception e) {
            log.error("删除公告失败", e);
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }
    
    /**
     * 获取待审核的活动列表
     * @param page 页码
     * @param pageSize 页大小
     * @return 待审核活动列表
     */
    @GetMapping("/activities/pending")
    public Result<IPage<Activity>> getPendingActivities(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        try {
            if (page < 1 || pageSize < 1) {
                return Result.error(ResultCode.BAD_REQUEST, "页码和页大小必须大于0");
            }
            IPage<Activity> pendingActivities = activityService.getPendingActivities(page, pageSize);
            return Result.success(pendingActivities);
        } catch (Exception e) {
            log.error("获取待审核活动失败", e);
            return Result.error(ResultCode.ERROR, "服务器错误");
        }
    }
    
    /**
     * 审核通过活动
     * @param activityId 活动ID
     * @return 审核结果
     */
    @PostMapping("/activities/{activityId}/approve")
    @Transactional(rollbackFor = Exception.class)
    public Result<String> approveActivity(@PathVariable Integer activityId) {
        try {
            boolean success = activityService.approveActivity(activityId);
            if (success) {
                return Result.success("活动审核通过");
            } else {
                return Result.error(ResultCode.BAD_REQUEST, "活动审核失败");
            }
        } catch (Exception e) {
            log.error("活动审核通过失败", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.error(ResultCode.ERROR, "服务器错误");
        }
    }
    
    /**
     * 审核拒绝活动
     * @param activityId 活动ID
     * @return 审核结果
     */
    @PostMapping("/activities/{activityId}/reject")
    public Result<String> rejectActivity(@PathVariable Integer activityId) {
        try {
            boolean success = activityService.rejectActivity(activityId);
            if (success) {
                return Result.success("活动审核拒绝");
            } else {
                return Result.error(ResultCode.BAD_REQUEST, "活动审核失败");
            }
        } catch (Exception e) {
            log.error("活动审核拒绝失败", e);
            return Result.error(ResultCode.ERROR, "服务器错误");
        }
    }
}
