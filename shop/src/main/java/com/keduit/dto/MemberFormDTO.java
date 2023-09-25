package com.keduit.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberFormDTO {

    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    @NotEmpty(message = "이메일은 필수 입력값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    @Length(min = 4, max = 12, message = "비밀번호는 4~12자리 입니다")
    private String password;

    @NotBlank(message = "주소는 필수 입력값입니다.")
    private String address;
}
