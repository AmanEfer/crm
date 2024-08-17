package com.amanefer.crm.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name can't be null")
    @Size(min = 2, max = 20, message = "Name should contain from 3 to 20 symbols")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Password can't be null")
    @Size(min = 2, max = 20, message = "Password should contain from 3 to 20 symbols")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "Email can't be null")
    @Email(message = "Enter the correct email")
    @Size(min = 2, max = 20, message = "Email should contain from 3 to 20 symbols")
    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "author")
    private List<Task> authoredTasks;

    @OneToMany(mappedBy = "assignee")
    private List<Task> assignedTasks;
}
