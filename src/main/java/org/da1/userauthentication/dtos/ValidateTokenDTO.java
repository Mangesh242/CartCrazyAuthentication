package org.da1.userauthentication.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateTokenDTO {
    private String token;
    private Long userId;
}
