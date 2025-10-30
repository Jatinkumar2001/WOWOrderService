package com.enterprisex.wallsofwonder.oms.DTO.Response;

import lombok.Data;

@Data
public class ResponseHandler {
    private Boolean isSuccess;
    private String message;
    private Integer status;
    private Object data;
}