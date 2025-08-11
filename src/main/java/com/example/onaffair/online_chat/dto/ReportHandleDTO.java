package com.example.onaffair.online_chat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportHandleDTO {
    
    @NotNull(message = "举报ID不能为空")
    private Long reportId;
    
    @NotBlank(message = "处理人账号不能为空")
    private String handlerAccount;
    
    @NotBlank(message = "处理措施不能为空")
    private String handleAction;
    
    private String handleComment;
}