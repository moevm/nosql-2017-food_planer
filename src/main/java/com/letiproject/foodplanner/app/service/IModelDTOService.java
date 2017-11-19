package com.letiproject.foodplanner.app.service;

/*
 * Created by @belrbeZ on 08.05.2017.
 */

import java.util.Optional;

/**
 * Default Comment
 */
public interface IModelDTOService<DAO, DTO> {

    /**
     * TRANSACTIONAL
     */
    Optional<DAO> saveDTO(DTO model);

    /**
     * TRANSACTIONAL
     */
    Optional<DAO> updateDTO(DTO model);
}
