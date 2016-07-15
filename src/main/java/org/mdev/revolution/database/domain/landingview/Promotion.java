package org.mdev.revolution.database.domain.landingview;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "hotelview_promos")
public class Promotion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 35)
    private String title;

    @Column
    private String text;

    @Column(name = "button_text", length = 25)
    private String buttonText;

    @Column(name = "button_type")
    private int buttonType;

    @Column(name = "button_link", length = 90)
    private String buttonLink;

    @Column(name = "image_link", length = 120)
    private String imageLink;

    public Promotion() {
        super();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getButtonText() {
        return buttonText;
    }

    public int getButtonType() {
        return buttonType;
    }

    public String getButtonLink() {
        return buttonLink;
    }

    public String getImageLink() {
        return imageLink;
    }
}
