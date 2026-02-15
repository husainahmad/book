package com.ahmad.book.infrastructure.web;

import com.ahmad.book.application.service.MemberService;
import com.ahmad.book.infrastructure.web.dto.CommonResponse;
import com.ahmad.book.infrastructure.web.dto.MemberRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Tag(name = "Members", description = "API for managing members in the library system.")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @Operation(summary = "Create a new member", description = "Creates a new member in the library system with the provided details.")
    public ResponseEntity<CommonResponse> createBook(@Valid @RequestBody MemberRequest memberRequest) {
        return new ResponseEntity<>(CommonResponse.builder()
                .data(memberService.create(memberRequest.toMember()))
                .build(), HttpStatus.CREATED);
    }

    @GetMapping("")
    @Operation(summary = "Get all members", description = "Retrieves a list of all members registered in the library system.")
    public ResponseEntity<CommonResponse> getAll() {
        return new ResponseEntity<>(CommonResponse.builder()
                .data(memberService.getAllMembers()).build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get member by ID", description = "Retrieves the details of a specific member by their ID.")
    public ResponseEntity<CommonResponse> getBookById(@PathVariable Long id) {
        return new ResponseEntity<>(CommonResponse.builder()
                .data(memberService.getMemberById(id))
                .build(), HttpStatus.OK);
    }
}
