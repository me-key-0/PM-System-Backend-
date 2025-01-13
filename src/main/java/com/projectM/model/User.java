package com.projectM.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String full_name;
    private String email;
    private  String password;

    private int projectSize;

    @JsonIgnore
    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL)
    private List<Issue> assignedIssue = new ArrayList<Issue>();

//    @JsonIgnore
//    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
//    private List<Message> messages = new ArrayList<>();
//
//    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
//    private List<Chat> chat = new ArrayList<>();

}
