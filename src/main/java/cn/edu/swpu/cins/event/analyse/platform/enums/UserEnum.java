package cn.edu.swpu.cins.event.analyse.platform.enums;

/**
 * Created by muyi on 17-6-6.
 */
public enum UserEnum {
    UPDATE_PWD_SUCCESS("修改密码成功"),
    OLDPWD_ERROR("原始密码不匹配，请重新输入原始密码"),
    UPDATE_PWD_FAILED("修改密码失败");

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    UserEnum() {
    }

    UserEnum(String msg) {
        this.msg = msg;
    }
}
