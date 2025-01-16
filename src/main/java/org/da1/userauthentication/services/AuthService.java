package org.da1.userauthentication.services;


import org.da1.userauthentication.exceptions.PassWordMismatchException;
import org.da1.userauthentication.exceptions.UserAlreadyExistExceptions;
import org.da1.userauthentication.exceptions.UserNotRegisterred;
import org.da1.userauthentication.models.Role;
import org.da1.userauthentication.models.User;
import org.da1.userauthentication.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public User signUp(String email, String password) throws UserAlreadyExistExceptions {
        Optional<User> userOptional=userRepo.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistExceptions("Please try log in");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        Role role=new Role();
        role.setValue("ROLE_USER");
        userRepo.save(user);
        return user;

    }

    @Override
    public User login(String email, String password) throws UserNotRegisterred, PassWordMismatchException {
        Optional<User> userOptional=userRepo.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserNotRegisterred("Please Sign up");
        }
        String storePassword = userOptional.get().getPassword();
        if (!password.equals(storePassword)) {
            throw new PassWordMismatchException("Please add correct pwd");
        }

        return userOptional.get();
    }
}
