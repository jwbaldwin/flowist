package com.jbaldwin.flowist.domain;

import lombok.Data;

@Data
public class JwtUser {
    private Long id;
    private String username;
    private String role;
}
