package com.ahmad.book.infrastructure.web.dto;

import com.ahmad.book.domain.Member;
import lombok.Data;

@Data
public class MemberRequest {
    private String name;
    private String email;

    public Member toMember() {
        return new Member()
                .setName(this.name)
                .setEmail(this.email);
    }
}
