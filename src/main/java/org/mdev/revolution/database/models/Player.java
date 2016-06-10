package org.mdev.revolution.database.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class Player implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    public Player() {
        super();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}

