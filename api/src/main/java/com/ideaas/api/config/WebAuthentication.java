package com.ideaas.api.config;

import com.ideaas.services.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Collections;

@Configuration
public class WebAuthentication implements AuthenticationProvider {

    private UsuarioService usuarioService;

    @Autowired
    public WebAuthentication(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if ("externaluser".equals(username) && "pass".equals(password)) {

            return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());


        } else {
            throw new BadCredentialsException("External system authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
