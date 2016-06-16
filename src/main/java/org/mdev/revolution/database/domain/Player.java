package org.mdev.revolution.database.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class Player implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "mail", unique = true, nullable = false)
    private String email;

    public Player() {
        super();
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}

