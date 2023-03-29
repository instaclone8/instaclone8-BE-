package com.example.instaclone.global.exception;

import com.example.instaclone.domain.user.dto.MessageResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ApiException extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request,response);
        }catch(UsernameNotFoundException e) {
            jwtExceptionHandler(response,e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (RuntimeException e){
            jwtExceptionHandler(response,e.getMessage().split(":")[0], HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            jwtExceptionHandler(response,e.getMessage().split(":")[0], HttpStatus.BAD_REQUEST);
        }
    }

    public void jwtExceptionHandler(HttpServletResponse response, String msg, HttpStatus httpStatus) {
        response.setStatus(httpStatus.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        try {
            String json = new ObjectMapper().writeValueAsString(new MessageResponseDto(httpStatus, msg));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
