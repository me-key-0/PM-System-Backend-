package com.projectM.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class IssueRequest {
    private String title;
    private String description;
    private String status;
    private String priority;
    private Long projectId;
    private LocalDate dueDate;
}
