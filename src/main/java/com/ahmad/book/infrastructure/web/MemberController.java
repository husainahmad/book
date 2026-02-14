package com.ahmad.book.infrastructure.web;

import com.ahmad.book.application.service.MemberService;
import com.ahmad.book.domain.Member;
import com.ahmad.book.infrastructure.web.dto.CommonResponse;
import com.ahmad.book.infrastructure.web.dto.MemberRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<CommonResponse> createBook(@Valid @RequestBody MemberRequest memberRequest) {
        Member member = memberRequest.toMember();
        return new ResponseEntity<>(CommonResponse.builder()
                .data(memberService.create(member))
                .build(), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<CommonResponse> getAll() {
        return new ResponseEntity<>(CommonResponse.builder()
                .data(memberService.getAllMembers()).build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getBookById(@PathVariable Long id) {
        Member member = memberService.getMemberById(id);
        return new ResponseEntity<>(CommonResponse.builder()
                .data(member)
                .build(), HttpStatus.OK);
    }
}
