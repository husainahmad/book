package com.ahmad.book.infrastructure.persistence.mybatis.repository;

import com.ahmad.book.application.port.out.MemberRepositoryPort;
import com.ahmad.book.domain.Member;
import com.ahmad.book.infrastructure.persistence.mybatis.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryAdapter implements MemberRepositoryPort {

    private final MemberMapper memberMapper;

    @Override
    public void save(Member member) {
        memberMapper.insert(member);
    }

    @Override
    public Member findById(Long id) {
        return memberMapper.selectById(id);
    }

    @Override
    public void update(Member member) {
        memberMapper.update(member);
    }
}
