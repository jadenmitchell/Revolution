package org.mdev.revolution.database.models;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class Player {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
