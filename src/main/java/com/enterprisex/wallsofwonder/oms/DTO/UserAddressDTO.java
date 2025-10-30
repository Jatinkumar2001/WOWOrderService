package com.enterprisex.wallsofwonder.oms.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserAddressDTO implements Serializable {

    private Long id;

    private String  registerPhoneNumber;

    private String email;

    private String firstName;

    private String lastName;
    private String dialCode;
    private String phoneNumber;

    private Integer pinCode;

    private String state;
    private String area;

    private String city;

    private String landmark;

    private String streetAddress;

    private String type;

    @JsonIgnore
    private Boolean active;

}
