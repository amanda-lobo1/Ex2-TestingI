package com.meli.obterdiploma2.controller;

import com.meli.obterdiploma2.model.StudentDTO;
import com.meli.obterdiploma2.service.IObterDiplomaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ObterDiplomaController {

    @Autowired
    IObterDiplomaService service;

    @GetMapping("/analyzeScores/{studentId}")
    public StudentDTO analyzeScores(@PathVariable Long studentId) {
        return service.analyzeScores(studentId);
    }
}