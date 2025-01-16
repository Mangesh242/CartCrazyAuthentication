package org.da1.userauthentication.controllers;

import org.da1.userauthentication.dtos.LoginRequest;
import org.da1.userauthentication.dtos.SignUpRequest;

import org.da1.userauthentication.dtos.UserDTO;
import org.da1.userauthentication.exceptions.PassWordMismatchException;
import org.da1.userauthentication.exceptions.UserAlreadyExistExceptions;
import org.da1.userauthentication.exceptions.UserNotRegisterred;
import org.da1.userauthentication.models.User;
import org.da1.userauthentication.repos.UserRepo;
import org.da1.userauthentication.services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth/v1")
public class AuthController {

    @Autowired
    private IAuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody SignUpRequest signUpRequest) {
      try{

          User user= authService.signUp(signUpRequest.getEmail(), signUpRequest.getPassword());
          return new ResponseEntity<>(from(user),HttpStatus.CREATED);
      }catch (UserAlreadyExistExceptions exception){
          return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
      }
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequest loginRequest) {
        try{
            User user=this.authService.login(loginRequest.getEmail(),loginRequest.getPassword());
            return new ResponseEntity<>(from(user),HttpStatus.OK);
        }
        catch (UserNotRegisterred ex){
               return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        catch (PassWordMismatchException exp){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        } catch (UserAlreadyExistExceptions e) {
            throw new RuntimeException(e);
        }
    }

    public UserDTO from(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setId(user.getId());
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }
}
