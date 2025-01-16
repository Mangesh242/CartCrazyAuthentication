package org.da1.userauthentication.services;


import org.da1.userauthentication.exceptions.PassWordMismatchException;
import org.da1.userauthentication.exceptions.UserAlreadyExistExceptions;
import org.da1.userauthentication.exceptions.UserNotRegisterred;
import org.da1.userauthentication.models.Role;
import org.da1.userauthentication.models.User;
import org.da1.userauthentication.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User signUp(String email, String password) throws UserAlreadyExistExceptions {
        Optional<User> userOptional=userRepo.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistExceptions("Please try log in");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        Role role=new Role();
        role.setValue("ROLE_USER");
        List<Role> roles=new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
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
        if (!bCryptPasswordEncoder.matches(password,storePassword)) {
            throw new PassWordMismatchException("Please add correct pwd");
        }

        return userOptional.get();
    }
}
