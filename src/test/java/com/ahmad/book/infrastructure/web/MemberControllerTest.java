package com.ahmad.book.infrastructure.web;

import com.ahmad.book.application.port.out.JwtTokenPort;
import com.ahmad.book.application.service.MemberService;
import com.ahmad.book.domain.Member;
import com.ahmad.book.domain.exception.AlreadyExistException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@AutoConfigureMockMvc(addFilters = false)
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtTokenPort jwtTokenPort;

    @Test
    void createMember_ShouldReturnCreatedMember() throws Exception {
        Member member = new Member()
                .setId(1L)
                .setName("Test Member")
                .setEmail("test@member.com");

        Mockito.when(memberService.create(Mockito.any(Member.class))).thenReturn(member);

        mockMvc.perform(post("/api/members")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(member)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(member.getId()))
                .andExpect(jsonPath("$.data.name").value(member.getName()))
                .andExpect(jsonPath("$.data.email").value(member.getEmail()));
    }

    @Test
    void createMember_ShouldReturnBadRequest_WhenEmailIsInvalid() throws Exception {
        Member member = new Member()
                .setId(1L)
                .setName("Test Member")
                .setEmail("invalid-email");

        mockMvc.perform(post("/api/members")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(member)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createMember_ShouldReturnBadRequest_WhenEmailIsExist() throws Exception {
        Member member = new Member()
                .setId(1L)
                .setName("Test Member")
                .setEmail("exist@email.com");

        Mockito.when(memberService.create(Mockito.any(Member.class))).thenThrow(new AlreadyExistException("exception.member.email.alreadyExists", null));

        mockMvc.perform(post("/api/members")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(member)))
                .andExpect(status().isConflict());

    }
}
