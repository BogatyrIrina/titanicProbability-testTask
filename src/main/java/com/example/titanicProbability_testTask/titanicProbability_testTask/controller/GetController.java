package com.example.titanicProbability_testTask.titanicProbability_testTask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GetController {
    @GetMapping("/pass")
    public String showPassengers() {
        return "passengers";
    }
}
