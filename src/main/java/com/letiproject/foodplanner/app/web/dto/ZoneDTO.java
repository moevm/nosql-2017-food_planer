package com.letiproject.foodplanner.app.web.dto;

/*
 * Created by @belrbeZ on 6.04.2017.
 */

import com.letiproject.foodplanner.app.postgres.model.type.UserType;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Default Comment
 */
public class ZoneDTO {

    public static final ZoneDTO EMPTY = new ZoneDTO();

    @NotNull
    private Long profileId;

    private String city;
    private String country;
    private LocalDateTime registerDate;

    private ZoneDTO() {
        this.profileId = UserType.EMPTY.getValue();
        this.city = "";
        this.country = "";
        this.registerDate = LocalDateTime.MIN;
    }

    public ZoneDTO(Long profileId, String city, String country, Timestamp timestamp) {
        this.profileId = profileId;
        this.city = city;
        this.country = country;
        this.registerDate = (timestamp != null) ? timestamp.toLocalDateTime() : null;
    }

    //<editor-fold desc="GetterAndSetter">

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    //</editor-fold>

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZoneDTO that = (ZoneDTO) o;

        return profileId.equals(that.profileId);
    }

    @Override
    public int hashCode() {
        return profileId.hashCode();
    }
}
