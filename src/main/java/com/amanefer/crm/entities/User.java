package com.amanefer.crm.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name can't be empty or null")
    @Size(min = 2, max = 20, message = "Name should contain from 3 to 20 symbols")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Password can't be empty or null")
    @Size(min = 2, max = 20, message = "Password should contain from 3 to 20 symbols")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Email can't be empty or null")
    @Email(message = "Enter the correct email")
    @Size(min = 2, max = 20, message = "Email should contain from 3 to 20 symbols")
    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "author")
    private Set<Task> authoredTasks;

    @OneToMany(mappedBy = "assignee")
    private Set<Task> assignedTasks;
}
