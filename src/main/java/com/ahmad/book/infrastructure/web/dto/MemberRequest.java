package com.ahmad.book.infrastructure.web.dto;

import com.ahmad.book.domain.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberRequest {
    @NotBlank(message = "book.member.name.required")
    private String name;
    @NotBlank(message = "book.member.email.required")
    private String email;

    public Member toMember() {
        return new Member()
                .setName(this.name)
                .setEmail(this.email);
    }
}
