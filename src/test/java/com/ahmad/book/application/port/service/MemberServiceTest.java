package com.ahmad.book.application.port.service;

import com.ahmad.book.application.port.out.MemberRepositoryPort;
import com.ahmad.book.application.service.MemberService;
import com.ahmad.book.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepositoryPort memberRepositoryPort;

    @InjectMocks
    private MemberService memberService;

    private Member member;

    @BeforeEach
    void setUp() {

        member = new Member()
                .setId(1L)
                .setName("Jon Doe")
                .setEmail("jon.doe@book.lib");
    }

    @Test
    void createMember_ShouldReturnCreatedMember() {
        // Given
        when(memberRepositoryPort.findByEmail(member.getEmail())).thenReturn(null);
        when(memberRepositoryPort.save(member)).thenReturn(member);

        // When
        Member createdMember = memberService.create(member);

        // Then
        assertNotNull(createdMember);
        assertEquals(member.getId(), createdMember.getId());
        assertEquals(member.getName(), createdMember.getName());
        assertEquals(member.getEmail(), createdMember.getEmail());

        verify(memberRepositoryPort).findByEmail(member.getEmail());
        verify(memberRepositoryPort).save(member);
    }

    @Test
    void getMemberById_ShouldReturnMember() {
        // Given
        when(memberRepositoryPort.findById(member.getId())).thenReturn(member);

        // When
        Member foundMember = memberService.getMemberById(member.getId());

        // Then
        assertNotNull(foundMember);
        assertEquals(member.getId(), foundMember.getId());
        assertEquals(member.getName(), foundMember.getName());
        assertEquals(member.getEmail(), foundMember.getEmail());

        verify(memberRepositoryPort).findById(member.getId());
    }

    @Test
    void getAllMembers_ShouldReturnListOfMembers() {
        // Given
        when(memberRepositoryPort.findAll()).thenReturn(List.of(member));

        // When
        List<Member> members = memberService.getAllMembers();

        // Then
        assertNotNull(members);
        assertEquals(1, members.size());
        assertEquals(member.getId(), members.get(0).getId());
        assertEquals(member.getName(), members.get(0).getName());
        assertEquals(member.getEmail(), members.get(0).getEmail());

        verify(memberRepositoryPort).findAll();
    }
}
