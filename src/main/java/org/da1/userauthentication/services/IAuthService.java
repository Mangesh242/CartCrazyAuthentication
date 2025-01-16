package org.da1.userauthentication.services;


import org.da1.userauthentication.exceptions.PassWordMismatchException;
import org.da1.userauthentication.exceptions.UserAlreadyExistExceptions;
import org.da1.userauthentication.exceptions.UserNotRegisterred;
import org.da1.userauthentication.models.User;

public interface IAuthService {

    User signUp(String email,String password) throws UserAlreadyExistExceptions;

    User login(String email, String password) throws UserAlreadyExistExceptions, UserNotRegisterred, PassWordMismatchException;
}
