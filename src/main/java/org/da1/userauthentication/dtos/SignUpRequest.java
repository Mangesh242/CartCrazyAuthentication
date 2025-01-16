package org.da1.userauthentication.dtos;

import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class SignUpRequest {
    private String email;
    private String password;

}
