package com.amanefer.crm.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", schema = "task_system")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name can't be empty or null")
    @Size(min = 2, max = 30, message = "Name should contain from 2 to 30 symbols")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Password can't be empty or null")
    @Size(min = 3, max = 128, message = "Password should contain from 3 to 20 symbols")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Email can't be empty or null")
    @Email(message = "Enter the correct email")
    @Size(min = 6, max = 30, message = "Email should contain from 6 to 30 symbols")
    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "author", cascade = CascadeType.MERGE)
    private Set<Task> authoredTasks;

    @OneToMany(mappedBy = "assignee", cascade = CascadeType.MERGE)
    private Set<Task> assignedTasks;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "users_roles", schema = "task_system",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}
