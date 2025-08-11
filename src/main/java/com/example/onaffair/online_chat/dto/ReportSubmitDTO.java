package com.example.onaffair.online_chat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportSubmitDTO {
    
    @NotNull(message = "活动ID不能为空")
    private Integer activityId;
    
    @NotBlank(message = "举报人账号不能为空")
    private String reporter;
    
    @NotBlank(message = "举报类型不能为空")
    private String reportType;
    
    @NotBlank(message = "举报描述不能为空")
    @Size(min = 10, message = "举报描述至少需要10个字符")
    private String description;
    
    @Size(max = 5, message = "最多上传5张证据图片")
    private List<String> evidenceImages;
}