package cn.edu.swpu.cins.event.analyse.platform.model.view;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by muyi on 17-6-6.
 */
public class UserUpdatePwd {

    private String oldPassword;
    private String newPassword;

    public UserUpdatePwd(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public UserUpdatePwd(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public UserUpdatePwd() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
