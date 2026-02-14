package com.ahmad.book.application.service;

import com.ahmad.book.application.port.in.MemberServicePort;
import com.ahmad.book.application.port.out.MemberRepositoryPort;
import com.ahmad.book.domain.Member;
import com.ahmad.book.domain.exception.AlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberServicePort {

    private final MemberRepositoryPort memberRepositoryPort;

    @Override
    public Member create(Member member) {
        if (memberRepositoryPort.findByEmail(member.getEmail()) != null) {
            throw new AlreadyExistException("exception.member.email.alreadyExists", null);
        }
        return memberRepositoryPort.save(member);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepositoryPort.findAll();
    }

    @Override
    public Member getMemberById(Long id) {
        return memberRepositoryPort.findById(id);
    }
}
