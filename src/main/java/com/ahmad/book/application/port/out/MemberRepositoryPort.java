package com.ahmad.book.application.port.out;

import com.ahmad.book.domain.Member;

import java.util.List;

public interface MemberRepositoryPort {
    Member save(Member member);
    Member findById(Long id);
    Member findByEmail(String email);
    List<Member> findAll();
    void update(Member member);
}
