package cn.tedu.csmall.passport.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AdminAddNewParam implements Serializable {

    @NotNull(message = "添加管理员失败，必须有用户名!")
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private String phone;
    private String email;
    private String description;
    private Integer enable;

}
