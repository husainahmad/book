package com.ahmad.book.infrastructure.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String method = request.getMethod();
        String uri = request.getRequestURI();

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            int status = response.getStatus();
            String requestBody = getRequestBody(requestWrapper);
            String responseBody = getResponseBody(responseWrapper);

            if (!uri.startsWith("/actuator") && !uri.startsWith("/swagger")) {
                log.info("Incoming Request: {} {} with Body {}", method, uri, requestBody);
                log.info("Outgoing Response: {} {} with Status {} Body {}", method, uri, status, responseBody);
            }
            responseWrapper.copyBodyToResponse();
        }
    }

    private String getResponseBody(ContentCachingResponseWrapper responseWrapper) {
        byte[] buf = responseWrapper.getContentAsByteArray();
        if (buf.length==0) {
            return "";
        }
        return new String(buf, StandardCharsets.UTF_8);
    }

    private String getRequestBody(ContentCachingRequestWrapper requestWrapper) {
        byte[] buf = requestWrapper.getContentAsByteArray();
        if (buf.length==0) {
            return "";
        }
        return new String(buf, StandardCharsets.UTF_8);
    }
}
