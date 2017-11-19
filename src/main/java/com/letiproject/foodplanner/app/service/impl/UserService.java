package com.letiproject.foodplanner.app.service.impl;

import com.letiproject.foodplanner.app.postgres.model.User;
import com.letiproject.foodplanner.app.postgres.repository.UserRepository;
import com.letiproject.foodplanner.app.service.IUserService;
import com.letiproject.foodplanner.app.web.dto.UserDTO;
import com.letiproject.foodplanner.app.web.util.resolvers.ErrorMessageResolver;
import com.letiproject.foodplanner.app.web.util.validation.ModelTranslator;
import com.letiproject.foodplanner.app.web.util.validation.Validator;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.letiproject.foodplanner.app.web.util.resolvers.ErrorMessageResolver.*;


/**
 * Default Comment
 */
@Service
public class UserService extends PrimeModelService<User, Long> implements IUserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
        this.primeRepository = repository;
    }

    @Override
    public User getEmpty() {
        return User.EMPTY;
    }

    @Override
    public boolean existsByEmail(String email) {
        if (Validator.isStrEmpty(email)) {
            logger.warn(ErrorMessageResolver.GET_NULLABLE_ID);
            return false;
        }

        return repository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        if (Validator.isStrEmpty(phone)) {
            logger.warn(ErrorMessageResolver.GET_NULLABLE_ID);
            return false;
        }

        return repository.existsByPhone(phone);
    }

    @Override
    public Optional<User> getAuthorized() {
        return getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
    public Optional<User> getByEmail(String email) {
        if (Validator.isStrEmpty(email)) {
            logger.warn(ErrorMessageResolver.GET_NULLABLE_ID);
            return Optional.empty();
        }

        return repository.findOneByEmail(email);
    }

    @Override
    public Optional<User> getByPhone(String phone) {
        if (Validator.isStrEmpty(phone)) {
            logger.warn(ErrorMessageResolver.GET_NULLABLE_ID);
            return Optional.empty();
        }

        return repository.findOneByPhone(phone);
    }

    @Transactional
    @Override
    public Optional<User> saveDTO(UserDTO model) {
        if (model == null) {
            logger.warn(CREATE_MODEL_NULLABLE);
            return Optional.empty();
        }

        return super.save(ModelTranslator.toDAO(model));
    }

    @Transactional
    @Override
    public Optional<User> updateDTO(UserDTO model) {
        if (model == null) {
            logger.warn(UPDATE_MODEL_NULLABLE);
            throw new NullPointerException("");
        }
        Optional<User> toSave = get(model.getId());

        if (!toSave.isPresent()) {
            logger.warn(UPDATE_NOT_FOUND);
            return Optional.empty();
        }

        return super.save(ModelTranslator.updateDAO(toSave.get(), model));
    }

    @Transactional
    @Override
    public Optional<User> removeByEmail(@NotEmpty String email) {
        Optional<User> user = repository.removeByEmail(email);
        return user;
    }

    @Transactional
    @Override
    public Optional<User> removeByPhone(@NotEmpty String phone) {
        Optional<User> user = repository.removeByEmail(phone);
        return user;
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> user = repository.findOneByEmail(email);
        return user.orElseGet(this::getEmpty);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        super.remove(id);
    }

}
