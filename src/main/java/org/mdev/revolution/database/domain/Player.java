package org.mdev.revolution.database.domain;

import org.mdev.revolution.game.players.PlayerGender;

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

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private PlayerGender gender;

    public Player() {
        super();
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public PlayerGender getGender() {
        return gender;
    }
}

