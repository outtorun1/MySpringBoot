package com.shopping.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

// dto 객체 : 가입 화면에서 데이터(Data)가 전송(Transfer)될 때 값을 저장할 객체(Object)
@Getter @Setter
public class MemberFormDto {
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name ;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 넣어 주세요.")
    private String email ;

    @NotEmpty(message = "비밀 번호는 필수 입력 값입니다.")
    @Length(min = 8, max = 16, message = "비밀 번호는 8자 이상 16자 이하로 입력해 주세요.")
    private String password ;

    @NotEmpty(message = "주소는 필수 입력 값입니다.")
    private String address ;
}