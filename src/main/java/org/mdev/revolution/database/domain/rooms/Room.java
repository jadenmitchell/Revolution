package org.mdev.revolution.database.domain.rooms;

import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;
import org.mdev.revolution.database.converters.StringListConverter;
import org.mdev.revolution.game.rooms.RoomAccess;
import org.mdev.revolution.game.rooms.RoomType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "owner_id", nullable = false)
    private int ownerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    private RoomType type;

    @Column(nullable = false)
    private String caption;

    @Column
    private String description;

    @Column
    @Transient
    @Convert(converter = StringListConverter.class)
    private List<String> tags;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_type", nullable = false)
    private RoomAccess access;

    @Column
    private String password;

    @Column(name = "max_users", nullable = false)
    private int maxUsers;

    @Column
    private int score;

    @Column(nullable = false)
    private String model;

    @Column(name = "allow_pets", nullable = false)
    private int allowPets;

    @Column(name = "allow_pets_eating", nullable = false)
    private int allowPetsEating;

    @Column(name = "disable_blocking", nullable = false)
    private int disableBlocking;

    @Column(name = "hide_walls", nullable = false)
    private int hideWalls;

    @Column(name = "thickness_wall", nullable = false)
    private int wallThickness;

    @Column(name = "thickness_floor", nullable = false)
    private int floorThickness;

    @Column(nullable = false)
    private String decorations;

    public Room() {
        super();
    }

    public int getId() {
        return id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public RoomType getType() {
        return type;
    }

    public String getCaption() {
        return caption;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTags() {
        return tags;
    }

    public RoomAccess getAccess() {
        return access;
    }

    public String getPassword() {
        return password;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public int getScore() {
        return score;
    }

    public String getModel() {
        return model;
    }

    public int getAllowPets() {
        return allowPets;
    }

    public int getAllowPetsEating() {
        return allowPetsEating;
    }

    public int getDisableBlocking() {
        return disableBlocking;
    }

    public int getHideWalls() {
        return hideWalls;
    }

    public int getWallThickness() {
        return wallThickness;
    }

    public int getFloorThickness() {
        return floorThickness;
    }

    public TMap<String, String> getDecorations() {
        TMap<String, String> ret = new THashMap<>();
        String[] decorationBits = decorations.split("|");
        for (String decoration : decorationBits) {
            String[] s = decoration.split("=");
            if (s.length == 2) {
                ret.put(s[0], s[1]);
            }
        }
        return ret;
    }
}