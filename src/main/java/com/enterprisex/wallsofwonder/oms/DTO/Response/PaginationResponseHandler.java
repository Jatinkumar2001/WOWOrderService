package com.enterprisex.wallsofwonder.oms.DTO.Response;


import lombok.Data;

@Data
public class PaginationResponseHandler {

    private Boolean success;
    private String message;
    private Object data;

    private Object currentPage;

    private Object nextPage;

    private long totalNumberOfElement;

    private long totalNumberOfPages;
}
