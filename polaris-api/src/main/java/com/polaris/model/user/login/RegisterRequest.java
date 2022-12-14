package com.polaris.model.user.login;

import lombok.Data;
/**
 * @author polaris
 */
@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private Integer role = 1;
    private String avatar;
}
