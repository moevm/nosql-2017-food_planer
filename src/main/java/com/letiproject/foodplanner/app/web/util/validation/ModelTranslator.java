package com.letiproject.foodplanner.app.web.util.validation;

/*
 * Created by @belrbeZ on 7.04.2017.
 */

import com.letiproject.foodplanner.app.postgres.model.User;
import com.letiproject.foodplanner.app.postgres.model.Zone;
import com.letiproject.foodplanner.app.postgres.model.type.UserType;
import com.letiproject.foodplanner.app.web.dto.UserDTO;
import com.letiproject.foodplanner.app.web.dto.UserFormDTO;
import com.letiproject.foodplanner.app.web.dto.ZoneDTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.letiproject.foodplanner.app.web.util.validation.Validator.*;

/**
 * Translate and full fill DTO object to DAO object and revert
 */
public class ModelTranslator {

    //<editor-fold desc="toDTO">


    public static UserDTO toDTO(User model) {
        return (model == null)
                ? UserDTO.EMPTY
                : new UserDTO(model.getId(),
                model.getType(),
                model.getName(),
                model.getEmail(),
                model.getPhone(),
                model.getAbout(),
                model.getPassword(),
                model.getNotified(),
                model.getMuteEnd(),
                toDTO(model.getZone()));
    }

    public static ZoneDTO toDTO(Zone model) {
        return (model == null)
                ? ZoneDTO.EMPTY
                : new ZoneDTO(model.getUserId(),
                model.getCity(),
                model.getCountry(),
                model.getRegisterDate());
    }

    //</editor-fold>

    //<editor-fold desc="toListsDTO">


    public static List<UserDTO> usersToDTO(List<User> models) {
        return (models == null)
                ? Collections.emptyList()
                : models.stream().map(ModelTranslator::toDTO).collect(Collectors.toList());
    }


    //</editor-fold>

    //<editor-fold desc="toDAO">

    public static User toDAO(UserDTO model) {
        if (model == null)
            throw new NullPointerException();

        return new User(model.getType(),
                model.getName(),
                model.getEmail(),
                model.getPhone(),
                model.getPassword(),
                model.getAbout(),
                model.getNotified(),
                model.getMuteEnd());
    }

    public static User toDAO(UserFormDTO model) {
        if (model == null)
            throw new NullPointerException();

        return new User(UserType.USER,
                model.getName(),
                model.getEmail(),
                "",
                model.getPassword(),
                "");
    }

    public static Zone toDAO(ZoneDTO model) {
        if (model == null)
            throw new NullPointerException();

        return new Zone(model.getProfileId(),
                model.getCity(),
                model.getCountry()
        );
    }

    //</editor-fold>

    //<editor-fold desc="DAO update strategy">

    public static User updateDAO(User dao, UserDTO dto) throws NullPointerException, NumberFormatException {
        if (dto == null)
            throw new NullPointerException("NULLABLE dto");

        if (dao == null)
            throw new NullPointerException("NULLABLE dao");

        if (!Objects.equals(dao.getId(), dto.getId()))
            throw new NumberFormatException("DAO id differ from DTO id");

        dao.setEmail(isEmailValid(dto.getEmail()) ? dto.getEmail() : dao.getEmail());
        dao.setName(!isStrEmpty(dto.getName()) ? dto.getName() : dao.getName());
        dao.setAbout(!isStrEmpty(dto.getAbout()) ? dto.getAbout() : dao.getAbout());
        dao.setPhone(isPhoneValid(dto.getPhone()) ? dto.getPhone() : dao.getPhone());
        dao.setPassword(isPassValid(dto.getPassword()) ? dto.getPassword() : dao.getPassword());
        dao.setNotified((dto.getNotified() != null) ? dto.getNotified() : dao.getNotified());
        dao.setMuteEnd((dto.getMuteEnd() != null && dto.getMuteEnd().isAfter(LocalDateTime.now())) ? Timestamp.valueOf(dto.getMuteEnd()) : dao.getMuteEnd());
        dao.setType(dto.getType() != null ? dto.getType() : dao.getType());

        return dao;
    }

    public static Zone updateDAO(Zone dao, ZoneDTO dto) throws NullPointerException, NumberFormatException {
        if (dto == null)
            throw new NullPointerException("NULLABLE dto");

        if (dao == null)
            throw new NullPointerException("NULLABLE dao");

        if (!Objects.equals(dao.getUserId(), dto.getProfileId()))
            throw new NumberFormatException("DAO id differ from DTO id");

        dao.setCity(!isStrEmpty(dto.getCity()) ? dto.getCity() : dao.getCity());
        dao.setCountry((!isStrEmpty(dto.getCity())) ? dto.getCity() : dao.getCity());

        return dao;
    }

    //</editor-fold>
}
