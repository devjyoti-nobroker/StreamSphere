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
    @Column(name="id")
    private Long id;

    @Column(name="email")
    private String email;

    @Column(name="name")
    private String name;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "active")
    private boolean active;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

}
