package com.projectM.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
//    @JoinColumn(name = "assignee_id") // Foreign key column in the Issue table
    private User assignee;
}
