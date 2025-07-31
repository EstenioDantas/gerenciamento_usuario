package com.sigeps.test.register_user.util;

import com.sigeps.test.register_user.model.Role;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "Test secret key";

    public String generateToken(String username, Set<Role> roles) {
        String roleNames = roles.stream()
                                .map(Role::getName)
                                .collect(Collectors.joining(","));
        return username + ":" + roleNames;
    }

    public boolean validateToken(String token) {
        return token != null && token.contains(":");
    }

    public String getUsernameFromToken(String token) {
        if (validateToken(token) && token.contains(":")) {
            return token.split(":")[0];
        }
        return null;
    }

    public Set<String> getRolesFromToken(String token) {
        if (validateToken(token) && token.contains(":")) {
            String[] parts = token.split(":");
            if (parts.length > 1) {
                String[] roleArray = parts[1].split(",");
                return Set.of(roleArray);
            }
        }
        return Collections.emptySet();
    }
}