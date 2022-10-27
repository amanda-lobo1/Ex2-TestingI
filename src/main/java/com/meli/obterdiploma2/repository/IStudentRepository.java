package com.meli.obterdiploma2.repository;

import com.meli.obterdiploma2.model.StudentDTO;

import java.util.Set;

public interface IStudentRepository {

    Set<StudentDTO> findAll();

}