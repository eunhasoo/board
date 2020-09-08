package com.eunhasoo.board.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class BasicErrorController {

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
