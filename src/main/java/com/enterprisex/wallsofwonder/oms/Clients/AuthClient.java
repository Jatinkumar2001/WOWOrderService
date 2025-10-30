package com.enterprisex.wallsofwonder.oms.Clients;

import com.enterprisex.wallsofwonder.oms.DTO.Response.AuthResponse;
import com.enterprisex.wallsofwonder.oms.DTO.Response.ResponseHandler;
import com.enterprisex.wallsofwonder.oms.FeignClientConfig;
import com.enterprisex.wallsofwonder.oms.ServiceImpl.UserDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@Service
@FeignClient(name = "auth",url = "http://localhost:8081/wow/auth/api",configuration = FeignClientConfig.class)
public interface AuthClient {
    @GetMapping("auth/validate")       // Endpoint in Auth Service
    ResponseEntity<AuthResponse<UserDetail>> validateToken(@RequestHeader("Authorization") String token);
}
