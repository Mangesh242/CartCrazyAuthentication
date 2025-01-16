package org.da1.userauthentication.dtos;

import lombok.Getter;
import lombok.Setter;
import org.da1.userauthentication.models.Role;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String emial;
    private List <Role>  roles = new ArrayList<>();
}
