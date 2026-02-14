package com.ahmad.book.infrastructure.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class CommonResponse {
    @Builder.Default
    private long timeStamp = System.currentTimeMillis();
    @Builder.Default
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data = HttpStatus.CREATED;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object error;
}
