package com.enterprisex.wallsofwonder.oms.ServiceImpl;

import lombok.Data;
import org.springframework.stereotype.Service;


@Service
@Data
public class UserDetail {

	private static final String ROLE_PREFIX = "ROLE_";

    private String userName ;
    private String phoneNumber;
    private String otp;
    private String token;
    private String role;
    private Long id;
    private String password;


}
