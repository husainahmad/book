package com.ahmad.book.infrastructure.persistence.mybatis.repository;

import com.ahmad.book.application.port.out.MemberRepositoryPort;
import com.ahmad.book.domain.Member;
import com.ahmad.book.infrastructure.persistence.mybatis.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryAdapter implements MemberRepositoryPort {

    private final MemberMapper memberMapper;

    @Override
    public Member save(Member member) {
        memberMapper.insert(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(memberMapper.selectById(id));
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return Optional.ofNullable(memberMapper.selectByEmail(email));
    }

    @Override
    public List<Member> findAll() {
        return memberMapper.selectAll();
    }

    @Override
    public void update(Member member) {
        memberMapper.update(member);
    }
}
