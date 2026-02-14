package com.ahmad.book.application.port.in;

import com.ahmad.book.domain.Member;

import java.util.List;

public interface MemberServicePort {
    Member create(Member member);
    List<Member> getAllMembers();
    Member getMemberById(Long id);
}
