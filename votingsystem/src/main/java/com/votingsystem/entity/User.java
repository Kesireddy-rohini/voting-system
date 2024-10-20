package com.votingsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    
    @Id
    private Long userId;

    @NotBlank(message = "Please provide your name")
    private String name;

    @NotNull(message = "Please provide your age")
    private Integer age;

    @NotBlank(message = "Please provide your gender")
    private String gender; // e.g., "Male", "Female", "Other"

    @NotBlank(message = "Please provide your phone number")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phonenumber;

    @NotBlank(message = "Please provide your profession")
    private String profession;

    @NotBlank(message = "Please provide your email")
    @Email(message = "Please provide a valid email address")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Please provide your password")
    private String password;
}
