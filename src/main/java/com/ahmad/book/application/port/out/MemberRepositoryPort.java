package com.ahmad.book.application.port.out;

import com.ahmad.book.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryPort {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByEmail(String email);
    List<Member> findAll();
    void update(Member member);
}
