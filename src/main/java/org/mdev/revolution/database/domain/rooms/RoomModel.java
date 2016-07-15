package org.mdev.revolution.database.domain.rooms;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "room_models")
public class RoomModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
}
