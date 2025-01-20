package org.da1.userauthentication.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Session extends BaseModel{
    private String token;

    @ManyToOne
    private User user;

}
