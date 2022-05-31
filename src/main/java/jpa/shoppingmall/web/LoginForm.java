package jpa.shoppingmall.web;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter @ToString @EqualsAndHashCode
public class LoginForm {
    @NotEmpty(message = "id를 입력해주세요")
    private String id;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String password;
}
