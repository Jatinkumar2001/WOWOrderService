package com.enterprisex.wallsofwonder.oms.DTO.Response;

import lombok.Data;

@Data
public class AuthResponse <T>{
    private Boolean isSuccess;
    private String message;
    private Integer status;
    private T data;
}
