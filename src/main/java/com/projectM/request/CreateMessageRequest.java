package com.projectM.request;

import lombok.Data;

@Data
public class CreateMessageRequest {
    private Long projectId;
    private String message;
}
