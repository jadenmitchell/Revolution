package org.mdev.revolution.database.domain.navigator;

import org.mdev.revolution.game.navigator.NavigatorCategory;
import org.mdev.revolution.game.navigator.NavigatorCategoryType;
import org.mdev.revolution.game.navigator.NavigatorSearchAllowance;
import org.mdev.revolution.game.navigator.NavigatorViewMode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "flat_categories")
public class FlatCat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "public_name", unique = true, nullable = false)
    private String publicName;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private NavigatorCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_type", nullable = false)
    private NavigatorCategoryType categoryType;

    @Enumerated(EnumType.STRING)
    @Column(name = "view_mode", nullable = false)
    private NavigatorViewMode viewMode;

    @Column(name = "required_rank", nullable = false)
    private int requiredRank;

    @Enumerated(EnumType.STRING)
    @Column(name = "search_allowance", nullable = false)
    private NavigatorSearchAllowance searchAllowance;

    public FlatCat() {
        super();
    }

    public int getId() {
        return id;
    }

    public String getPublicName() {
        return publicName;
    }

    public NavigatorCategory getCategory() {
        return category;
    }

    public NavigatorCategoryType getCategoryType() {
        return categoryType;
    }

    public NavigatorViewMode getViewMode() {
        return viewMode;
    }

    public int getRequiredRank() {
        return requiredRank;
    }

    public NavigatorSearchAllowance getSearchAllowance() {
        return searchAllowance;
    }
}