package com.letiproject.foodplanner.app.web.dto;

/*
 * Created by @belrbeZ on 6.04.2017.
 */

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.letiproject.foodplanner.app.postgres.model.type.UserType;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Default Comment
 */
public class UserDTO {

    public static final UserDTO EMPTY = new UserDTO();

    @NotNull
    private Long id;

    private UserType type;

    private String name;
    private String email;
    private String password;

    private String phone;

    private String about;
    private Boolean notified;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime muteEnd;

    private ZoneDTO zone;

    private UserDTO() {
        this.id = UserType.EMPTY.getValue();
        this.type = UserType.EMPTY;
        this.name = "";
        this.email = "";
        this.phone = "";
        this.password = "";
        this.about = "";
        this.notified = false;
        this.muteEnd = LocalDateTime.MIN;
    }

    public UserDTO(Long id, UserType type, String name, String email, String phone, String password, String about) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.about = about;
    }

    public UserDTO(Long id, UserType type, String name,
                   String email, String phone, String about, String password,
                   boolean notified, Timestamp muteEnd) {
        this(id, type, name, email, phone, password, about);
        this.notified = notified;
        this.muteEnd = (muteEnd == null) ? null : muteEnd.toLocalDateTime();
    }

    public UserDTO(Long id, UserType type, String name,
                   String email, String phone, String about, String password,
                   boolean notified, Timestamp muteEnd,
                   ZoneDTO zone) {
        this(id, type, name, email, phone, about, password, notified, muteEnd);
        this.zone = zone;
    }

    //<editor-fold desc="GetterAndSetter">

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Boolean getNotified() {
        return notified;
    }

    public void setNotified(Boolean notified) {
        this.notified = notified;
    }

    public LocalDateTime getMuteEnd() {
        return muteEnd;
    }

    public void setMuteEnd(LocalDateTime muteEnd) {
        this.muteEnd = muteEnd;
    }

    public ZoneDTO getZone() {
        return zone;
    }

    public void setZone(ZoneDTO zone) {
        this.zone = zone;
    }
    //</editor-fold>

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO that = (UserDTO) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
