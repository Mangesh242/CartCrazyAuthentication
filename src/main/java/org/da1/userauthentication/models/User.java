package org.da1.userauthentication.models;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseModel {
    private String email;
    private String password;
    @ManyToMany()
    private List<Role> roles = new ArrayList<>();
}
