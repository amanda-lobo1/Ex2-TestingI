package com.meli.obterdiploma2.service;

import com.meli.obterdiploma2.model.StudentDTO;

public interface IObterDiplomaService {

    StudentDTO analyzeScores(Long studentId);
}