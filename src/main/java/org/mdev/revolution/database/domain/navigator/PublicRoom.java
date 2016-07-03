package org.mdev.revolution.database.domain.navigator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "navigator_frontpage")
public class PublicRoom implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
}