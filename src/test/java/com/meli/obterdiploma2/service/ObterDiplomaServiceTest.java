package com.meli.obterdiploma2.service;

import com.meli.obterdiploma2.exception.StudentNotFoundException;
import com.meli.obterdiploma2.model.StudentDTO;
import com.meli.obterdiploma2.model.SubjectDTO;
import com.meli.obterdiploma2.repository.IStudentDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ObterDiplomaServiceTest {

    @InjectMocks
    private ObterDiplomaService service;

    @Mock
    private IStudentDAO studentDAO;

    private StudentDTO studentDTO;

    @BeforeEach
    void setup(){
        this.studentDTO = new StudentDTO(1L, "Amanda Lobo", "Teste", 6.7D,
                new ArrayList<>(){{
                    add(new SubjectDTO("Amanda Lobo", 6.7D));
                }});
    }
    @Test
    @DisplayName("find student and return diploma")
    void analyzeScores_returnStudent_whenToFindStudent() {
        long studentId = 1;
        Mockito.when(studentDAO.findById(ArgumentMatchers.anyLong()))
                .thenReturn(studentDTO);

        StudentDTO res = service.analyzeScores(studentId);

        assertThat(res).isNotNull();
        assertThat(res.getId()).isEqualTo(studentId);
        assertThat(res.getAverageScore()).isPositive();
        assertThat(res.getStudentName()).isNotNull();
        assertThat(res.getSubjects()).isNotEmpty();
    }

    @Test
    @DisplayName("throw exception for student not existing")
    void analyzeScore_throwException_whenStudentNotFoundException() {
        long noExistingStudent = 0;

        BDDMockito.given(studentDAO.findById(ArgumentMatchers.anyLong()))
                .willThrow(new StudentNotFoundException(noExistingStudent));

        assertThrows(StudentNotFoundException.class, ()-> {
            service.analyzeScores(noExistingStudent);
        });
    }
}