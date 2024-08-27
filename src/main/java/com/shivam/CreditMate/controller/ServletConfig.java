package com.shivam.CreditMate.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jakarta.servlet.http.HttpServlet;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@Configuration
public class ServletConfig {

    @Bean
    public ServletRegistrationBean<MyCustomServlet> myServlet() {
        return new ServletRegistrationBean<>(new MyCustomServlet(), "/test-servlet/*");
    }

    public static class MyCustomServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("Hello from custom Servlet!");
        }
    }
}
