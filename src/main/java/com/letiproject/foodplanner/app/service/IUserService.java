package com.letiproject.foodplanner.app.service;

import com.letiproject.foodplanner.app.postgres.model.User;
import com.letiproject.foodplanner.app.postgres.model.VerificationToken;
import com.letiproject.foodplanner.app.web.dto.UserDto;
import com.letiproject.foodplanner.app.web.error.UserAlreadyExistException;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IUserService {

    User findByEmail(String email);

    User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException;

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void deleteUser(User user);

    void createVerificationTokenForUser(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String token);

    User findUserByEmail(String email);

    User getUserByID(long id);

    void changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String password);

    String validateVerificationToken(String token);

    String generateQRUrl(User user) throws UnsupportedEncodingException;

    User updateUser2FA(boolean use2FA);

    List<String> getUsersFromSessionRegistry();

}
