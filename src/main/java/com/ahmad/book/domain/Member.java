package com.ahmad.book.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Member {
    private Long id;
    private String name;
    private String email;
    private Date createdAt;
    private Date updatedAt;
}
