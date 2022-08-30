package com.meta.controller;

import com.meta.advice.exception.CAuthenticationEntryPointException;
import com.meta.response.ResponseDto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/exception")
public class ExceptionController {

    @GetMapping(value = "/entrypoint")
    public ResponseDto entrypointException() {
        throw new CAuthenticationEntryPointException();
    }

    @GetMapping(value = "/accessdenied")
    public ResponseDto accessdeniedException() {
        throw new AccessDeniedException("");
    }
}
