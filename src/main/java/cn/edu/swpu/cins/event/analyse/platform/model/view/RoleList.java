package cn.edu.swpu.cins.event.analyse.platform.model.view;

import java.util.List;

/**
 * Created by muyi on 17-7-8.
 */
public class RoleList {
    private List<Role> roleVIP;
    private List<Role> roleNORMAL;

    public RoleList(List<Role> roleVIP, List<Role> roleNORMAL) {
        this.roleVIP = roleVIP;
        this.roleNORMAL = roleNORMAL;
    }

    public List<Role> getRoleVIP() {
        return roleVIP;
    }

    public void setRoleVIP(List<Role> roleVIP) {
        this.roleVIP = roleVIP;
    }

    public List<Role> getRoleNORMAL() {
        return roleNORMAL;
    }

    public void setRoleNORMAL(List<Role> roleNORMAL) {
        this.roleNORMAL = roleNORMAL;
    }
}
