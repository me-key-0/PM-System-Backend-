package com.projectM.request;

import lombok.Data;

@Data
public class InvitationRequest {
    private String userEmail;
    private Long projectId;
}
