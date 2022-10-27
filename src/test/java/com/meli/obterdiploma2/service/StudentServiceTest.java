package com.meli.obterdiploma2.service;

import com.meli.obterdiploma2.exception.StudentNotFoundException;
import com.meli.obterdiploma2.model.StudentDTO;
import com.meli.obterdiploma2.model.SubjectDTO;
import com.meli.obterdiploma2.repository.IStudentDAO;
import com.meli.obterdiploma2.repository.IStudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private IStudentDAO iStudentDAO;

    @Mock
    private IStudentRepository iStudentRepository;

    private StudentDTO studentDTO;
    private Set<StudentDTO> student;

    @BeforeEach
    void setup(){
        this.studentDTO = new StudentDTO(1L, "Amanda", "Testing Student", 7.5D,
                new ArrayList<>(){{
                    add(new SubjectDTO("Amanda", 7.5));
                }});
        this.student = new HashSet<>();
        this.student.add(studentDTO);
    }

    @Test
    @DisplayName("return student by id")
    void read_returnStudent_whenFindById() {
        long studentId = 1;
        Mockito.when(iStudentDAO.findById(ArgumentMatchers.anyLong()))
                .thenReturn(studentDTO);

        StudentDTO res = studentService.read(studentId);

        assertThat(res).isNotNull();
        assertThat(res.getId()).isEqualTo(studentId);
        assertThat(res.getAverageScore()).isPositive();
        assertThat(res.getStudentName()).isNotNull();
        assertThat(res.getSubjects()).isNotEmpty();
    }

    @Test
    @DisplayName("exception for student not found")
    void read_throwException_whenStudentNotFoundById(){
        long noExistingStudent = 0;

        BDDMockito.given(iStudentDAO.findById(ArgumentMatchers.anyLong()))
                .willThrow(new StudentNotFoundException(noExistingStudent));

        assertThrows(StudentNotFoundException.class, () -> {
            studentService.read(noExistingStudent);
        });
    }
    @Test
    @DisplayName("return all students")
    void getAll_returnStudentSet_whenFoundAll() {
        Mockito.when(iStudentRepository.findAll())
                .thenReturn(student);

        Set<StudentDTO> res = studentService.getAll();

        assertThat(res).isNotNull();
    }
}