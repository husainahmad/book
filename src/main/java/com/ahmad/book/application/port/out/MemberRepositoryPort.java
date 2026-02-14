package com.ahmad.book.application.port.out;

import com.ahmad.book.domain.Member;

public interface MemberRepositoryPort {
    void save(Member member);
    Member findById(Long id);
    void update(Member member);
}
