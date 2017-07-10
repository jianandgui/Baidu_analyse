package cn.edu.swpu.cins.event.analyse.platform.model.view;

/**
 * Created by muyi on 17-7-8.
 */
public class Role {

    private String username;
    private String role;
    private String password;

    public Role(String username, String role, String password) {
        this.username = username;
        this.role = role;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
