package org.da1.userauthentication.services;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import org.antlr.v4.runtime.misc.Pair;
import org.da1.userauthentication.exceptions.PassWordMismatchException;
import org.da1.userauthentication.exceptions.UserAlreadyExistExceptions;
import org.da1.userauthentication.exceptions.UserNotRegisterred;
import org.da1.userauthentication.models.Role;
import org.da1.userauthentication.models.User;
import org.da1.userauthentication.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
    public Pair<User,String> login(String email, String password) throws UserNotRegisterred, PassWordMismatchException {
        Optional<User> userOptional=userRepo.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserNotRegisterred("Please Sign up");
        }
        String storePassword = userOptional.get().getPassword();
        if (!bCryptPasswordEncoder.matches(password,storePassword)) {
            throw new PassWordMismatchException("Please add correct pwd");
        }
        System.out.println("Email that we received : "+email);

        Map<String,Object> payLoad=new HashMap<>();
        payLoad.put("email",email);
        Long nowInMillis=System.currentTimeMillis();
        payLoad.put("iat",nowInMillis); //issued at time
        payLoad.put("exp",nowInMillis+24*60*60*1000);
        payLoad.put("userId",userOptional.get().getId());
        payLoad.put("iss","crazycart");
        payLoad.put("scope",userOptional.get().getRoles());


//        String message="{ \"email\": \""+email+"\", \"password\": \""+storePassword+"\" }";
//        byte[] content=message.getBytes(StandardCharsets.UTF_8);


//        String token= Jwts.builder().content(content).compact();
        MacAlgorithm algorithm=Jwts.SIG.HS256;
        SecretKey secretKey= algorithm.key().build();
        String token=Jwts.builder().claims(payLoad).signWith(secretKey).compact();

        return new Pair<>(userOptional.get(),token);
    }
}
