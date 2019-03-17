package com.jbaldwin.flowist.domain;

import lombok.Data;

@Data
public class JwtUser {
    private String id;
    private String username;
    private String role;
}
