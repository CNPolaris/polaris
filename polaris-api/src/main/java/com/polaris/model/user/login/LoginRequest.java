package com.polaris.model.user.login;

import lombok.Data;

/**
 * @author polaris
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
}
