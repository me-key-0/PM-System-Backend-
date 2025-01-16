package com.projectM.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueDTO {
    private long id;

    private String title;

    private String description;

    private String status;

    private String priority;

    private Long projectId;

    private LocalDate dueDate;

    private List<String> tags = new ArrayList<>();

    private User assignee;

    private Project project;
}
