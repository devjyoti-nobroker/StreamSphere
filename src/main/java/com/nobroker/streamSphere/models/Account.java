package com.nobroker.streamSphere.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name="accounts")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", insertable = false, updatable = false, nullable = false)
    private int id;

    @Column(nullable = false, unique = true, name="email")
    private String email;

    @Column(nullable = false, unique = true, name="name")
    private String name;

    @Column(nullable = false, name = "password_hash")
    private String passwordHash;

    @Column(nullable = false, name = "active")
    private boolean active;

    @Column(nullable = false, updatable = false, name = "created")
    private LocalDateTime created;

    @Column(nullable = false, updatable = false, name = "last_updated")
    private LocalDateTime lastUpdated;

}
