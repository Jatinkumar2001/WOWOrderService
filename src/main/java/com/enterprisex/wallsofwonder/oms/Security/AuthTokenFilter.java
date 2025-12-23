package com.enterprisex.wallsofwonder.oms.Security;

import com.enterprisex.wallsofwonder.oms.Clients.AuthClient;
import com.enterprisex.wallsofwonder.oms.DTO.Response.AuthResponse;
import com.enterprisex.wallsofwonder.oms.DTO.Response.ResponseHandler;
import com.enterprisex.wallsofwonder.oms.ServiceImpl.UserDetail;
import com.enterprisex.wallsofwonder.oms.UserContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private  AuthClient authClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            responseHandler.setMessage("Missing or invalid Authorization header");
            responseHandler.setIsSuccess(false);
            ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = mapper.writeValueAsString(responseHandler);
            response.getWriter().write(jsonResponse);
            return;
        }

//        String token = authHeader.substring(7);

        try {
            ResponseEntity<AuthResponse<UserDetail>> validation = authClient.validateToken(authHeader);
            AuthResponse<UserDetail> validateResponse = validation.getBody();
            if (!Objects.requireNonNull(validateResponse).getIsSuccess()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                ResponseHandler responseHandler = new ResponseHandler();
                responseHandler.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                responseHandler.setMessage("Invalid token");
                responseHandler.setIsSuccess(false);
                ObjectMapper mapper = new ObjectMapper();
                String jsonResponse = mapper.writeValueAsString(responseHandler);
                response.getWriter().write(jsonResponse);
                return;
            }
            // ✅ Save to ThreadLocal
            UserContext.setUser(validateResponse.getData());
            System.out.println(validateResponse.getData().getPhoneNumber());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            responseHandler.setMessage("Auth service not reachable or token invalid");
            responseHandler.setIsSuccess(false);
            ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = mapper.writeValueAsString(responseHandler);
            response.getWriter().write(jsonResponse);
            return;
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            UserContext.clear();
        }
    }
}
