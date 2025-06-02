package com.example.onaffair.online_chat.controller;


import com.example.onaffair.online_chat.dto.*;
import com.example.onaffair.online_chat.entity.Activity;
import com.example.onaffair.online_chat.entity.ActivityComment;
import com.example.onaffair.online_chat.entity.UserParticipation;
import com.example.onaffair.online_chat.enums.ActivityCategory;
import com.example.onaffair.online_chat.enums.ActivityStatus;
import com.example.onaffair.online_chat.enums.CityENUM;
import com.example.onaffair.online_chat.enums.ResultCode;
import com.example.onaffair.online_chat.service.ActivityCommentService;
import com.example.onaffair.online_chat.service.ActivityService;
import com.example.onaffair.online_chat.service.GroupService;
import com.example.onaffair.online_chat.service.UserService;
import com.example.onaffair.online_chat.util.Result;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityCommentService activityCommentService;

    @Autowired
    private GroupService groupService;

    @PostMapping("/launch")
    public Result<GroupResponse> launchActivity(@Valid @RequestBody ActivityRequest activityRequest) {

        SecurityContext context = SecurityContextHolder.getContext();
        String organizer = context.getAuthentication().getName();

        if (userService.findByAccount(organizer) == null){
            return Result.error(ResultCode.BAD_REQUEST,"用户不存在");
        }
        try {
            Activity activity = new Activity(){{
                setOrganizer(organizer);
                setTitle(activityRequest.getTitle());
                setCategoryId(activityRequest.getCategoryId());
                setHighlight(activityRequest.getHighlight());
                setContent(activityRequest.getContent());
                setImages(activityRequest.getImages());
                setCityId(activityRequest.getCity());
                setAddress(activityRequest.getAddress());
                setBeginTime(activityRequest.getBeginTime());
                setEndTime(activityRequest.getEndTime());
                setLeastJoinNum(activityRequest.getLeastJoinNum());
                setMostJoinNum(activityRequest.getMostJoinNum());
                setCreatedAt(LocalDateTime.now());
                setUpdatedAt(LocalDateTime.now());
            }};

            activityService.createActivity(activity);

            UserParticipation userParticipation = new UserParticipation() {{
                setUserAccount(organizer);
                setActivityId(activity.getId());
            }};


            activityService.joinActivity(userParticipation);

            /*  活动发起完成后自动创建群组*/
            GroupResponse groupResponse = groupService.createGroupByActivity(activity);

            return Result.success(groupResponse);
        }catch (Exception e){
            return Result.error(ResultCode.ERROR,"服务器错误");
        }
    }


    @GetMapping("/public/city-category")
    public Result<Object> getCityAndCategoryList(){
        try {
            List<CityENUM> cities = CityENUM.zhejiangCities();
            List<ActivityCategory> categories = Arrays.asList(ActivityCategory.values());

            List<Object> cityList = cities.stream()
                    .map(city -> new HashMap<String,Object>(){{
                        put("id",city.getId());
                        put("name",city.getName());
                    }})
                    .collect(Collectors.toList());
            List<Object> categoryList = categories.stream()
                    .map(category -> new HashMap<String,Object>(){{
                        put("id",category.getId());
                        put("name",category.getName());
                    }})
                    .collect(Collectors.toList());

            return Result.success(Map.of("cities",cityList,"categories",categoryList));

        }catch (Exception e){
            return Result.error(ResultCode.ERROR,"服务器错误");
        }
    }
    @GetMapping("/public/activity-list")
    public Result<List<Activity>> getActivityList(@RequestParam(name = "categoryId",defaultValue = "0") Integer categoryId,
                                               @RequestParam(name = "page",defaultValue = "1")  Integer page,
                                               @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize,
                                               @RequestParam(name = "keyword",defaultValue = "")String keyword){
        try {

            if (page < 1 || pageSize < 1){
                return Result.error(ResultCode.BAD_REQUEST,"页码和页大小必须大于0");
            }
            List<Activity> res = activityService.getActivityList(categoryId,page,pageSize,keyword);
            return Result.success(res);
        }catch (Exception e){
            return Result.error(ResultCode.ERROR,"服务器错误");
        }
    }

    @GetMapping("/public/activity-detail")
    public Result<ActivityDetailResponse> ActivityDetail(@RequestParam(value = "id") Integer id){
        try {
            ActivityDetailResponse res = new ActivityDetailResponse();

            Activity activity = activityService.getActivityById(id);
            UserInfoResponse organizerInfo = userService.getUserInfo(List.of(activity.getOrganizer())).get(0);
            List<ActivityComment> activityComments = activityCommentService.getCommentByActivityID(activity.getId());

            List<UserCommentResponse> comments = new ArrayList<>();
            activityComments.forEach(activityComment -> {
                UserCommentResponse comment = new UserCommentResponse();
                comment.setComment(activityComment);
                UserInfoResponse userInfo = userService.getUserInfo(List.of(activityComment.getUserId())).get(0);
                comment.setUserinfo(userInfo);

                comments.add(comment);
            });

            res.setActivity(activity);
            res.setOrganizerInfo(organizerInfo);
            res.setComments(comments);

            return Result.success(res);
        }catch (Exception e){
            return Result.error(ResultCode.ERROR,"服务器错误");
        }
    }

    @PostMapping("/activity-join")
    public Result<Integer> ActivityJoin(@RequestBody Integer id){
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            String account = context.getAuthentication().getName();

            Activity currentActivity = activityService.getActivityById(id);

            if (activityService.getActivityJoined(account).stream().filter(activity -> activity.getId() == currentActivity.getId()) != null){
                 return Result.error(ResultCode.BAD_REQUEST,"已报名");
            }

            for (Activity activity : activityService.getActivityJoined(account)) {
                if (activity.getStatus() == ActivityStatus.ACTIVITY_CANCELLED.getId()) continue;
                if (currentActivity.getBeginTime().isBefore(activity.getEndTime()) && currentActivity.getEndTime().isAfter(activity.getBeginTime())){
                    return Result.error(ResultCode.BAD_REQUEST,"活动时间冲突");
                }
            }

            UserParticipation userParticipation = new UserParticipation(){{
                setActivityId(id);
                setUserAccount(account);
            }};
            if (activityService.joinActivity(userParticipation)){
                return Result.success(userParticipation.getActivityId());
            }
            return Result.error(ResultCode.BAD_REQUEST,"活动报名失败");
        }catch (RuntimeException ex){
            //活动不存在 / 报名人数已满
            return Result.error(ResultCode.BAD_REQUEST,ex.getMessage());
        }
        catch (Exception e){
            return Result.error(ResultCode.ERROR,"服务器错误");
        }
    }
    @GetMapping("/activity-joined")
    public Result<List<Activity>> ActivityJoined(){
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            String account = context.getAuthentication().getName();

            List<Activity> res = activityService.getActivityJoined(account);

            return Result.success(res);
        }catch (Exception e){
            return Result.error(ResultCode.ERROR,"服务器错误");
        }
    }

    @PostMapping("/activity-join-cancel")
    public Result<String> ActivityJoinCancel(@RequestBody Integer id){
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            String account = context.getAuthentication().getName();

            UserParticipation userParticipation = new UserParticipation(){{
                setActivityId(id);
                setUserAccount(account);
            }};

            if ( !activityService.cancelActivityJoin(userParticipation)){
                return Result.error(ResultCode.BAD_REQUEST,"活动报名取消失败");
            }
            return Result.success("活动报名取消成功");
        }catch (RuntimeException ex){
            return Result.error(ResultCode.BAD_REQUEST,ex.getMessage());
        } catch (Exception e){
            return Result.error(ResultCode.ERROR,"服务器错误");
        }
    }

    @GetMapping("/list")
    public Result<List<Activity>> ActivityList(@RequestParam(value = "userId",defaultValue = "null") String userId){
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            String account = context.getAuthentication().getName();
            if (!userId.equals("null")){
                account = userId;
            }
            List<Activity> res = activityService.getActivityList(account);
            return Result.success(res);
        }catch (Exception e){
            return Result.error(ResultCode.ERROR,"服务器错误");
        }
    }
    @PostMapping("/activity-cancel")
    public Result<String> ActivityCancel(@RequestBody Integer id){
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            String account = context.getAuthentication().getName();

            if (!activityService.cancelActivity(id,account)){
                return Result.error(ResultCode.BAD_REQUEST,"活动取消失败");
            }
            return Result.success("活动取消成功");
        }catch (RuntimeException ex){
            return Result.error(ResultCode.BAD_REQUEST,ex.getMessage());
        }
        catch (Exception e){
            return Result.error(ResultCode.ERROR,"服务器错误");
        }
    }

    @PostMapping("/activity-comment")
    public Result<String> ActivityComment(@RequestBody ActivityCommentRequest activityCommentRequest){
        try {
            ActivityComment activityComment = new ActivityComment(){{
                setActivityId(activityCommentRequest.getActivityId());
                setRating(activityCommentRequest.getRating());
                setImageUrl(activityCommentRequest.getImageUrl());
                setReplyHint(activityCommentRequest.getReplyHint());
                setTextContent(activityCommentRequest.getTextContent());
                setReplyText(activityCommentRequest.getReplyText());
                setUserId(activityCommentRequest.getUserId());
            }};
            activityCommentService.addComment(activityComment);
            return Result.success("评论成功");
        }catch (Exception e){
            return Result.error(ResultCode.ERROR,"服务器错误");
        }
    }

    @GetMapping("/public/top-activity")
    public Result<List<Activity>> getTopActivity(@RequestParam(name="num",defaultValue = "3") Integer num){
        try {
            List<Activity> res = activityService.getTopActivity(num);
            return Result.success(res);
        }catch (Exception e){
            return Result.error(ResultCode.ERROR,"服务器错误");
        }
    }

}
