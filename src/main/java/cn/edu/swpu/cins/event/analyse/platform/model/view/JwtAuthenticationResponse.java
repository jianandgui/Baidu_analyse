package cn.edu.swpu.cins.event.analyse.platform.model.view;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;
    private final String username;
    private final String role;


    public JwtAuthenticationResponse(String token, String username, String role) {

        this.token = token;
        this.username = username;
        this.role = role;

    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }


}
