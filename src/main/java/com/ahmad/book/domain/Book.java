package com.ahmad.book.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private int totalCopies;
    private int availableCopies;
    private Date createdAt;
    private Date updatedAt;
}
