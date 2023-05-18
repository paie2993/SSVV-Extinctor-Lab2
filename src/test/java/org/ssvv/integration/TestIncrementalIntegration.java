package org.ssvv.integration;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ssvv.domain.Nota;
import org.ssvv.domain.Pair;
import org.ssvv.domain.Student;
import org.ssvv.domain.Tema;
import org.ssvv.repository.NotaXMLRepository;
import org.ssvv.repository.StudentXMLRepository;
import org.ssvv.repository.TemaXMLRepository;
import org.ssvv.service.Service;
import org.ssvv.validation.NotaValidator;
import org.ssvv.validation.StudentValidator;
import org.ssvv.validation.TemaValidator;

import javax.swing.text.Style;

import static org.mockito.Mockito.when;

public class TestIncrementalIntegration {

    @Mock
    private StudentXMLRepository studentXMLRepository;

    @Mock
    private TemaXMLRepository temaXMLRepository;

    @Mock
    private NotaXMLRepository notaXMLRepository;

    private Service service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        this.service = new Service(studentXMLRepository, temaXMLRepository, notaXMLRepository);
    }

    @Test
    public void testAddGrade() {
        final var expected = new Nota(new Pair<>("5", "5"), 10, 12, "Excelent");
        when(studentXMLRepository.findOne("5")).thenReturn(new Student("5", "a", 936, "@gmail", "radu"));
        when(temaXMLRepository.findOne("5")).thenReturn(new Tema("5", "a", 12, 11));
        when(notaXMLRepository.save(expected)).thenReturn(expected);

        final var addedGrade = service.saveNota("5", "5", 10, 12, "Excelent");

        Assertions.assertEquals(expected, addedGrade);
    }


    @Test
    public void testAddStudent() {
        final var expected = new Student("5", "xd", 200, "@outlook", "Carmen");
        when(studentXMLRepository.save(expected)).thenReturn(expected);

        final var addedStudent = service.saveStudent("5", "xd", 200, "@outlook", "Carmen");

        Assertions.assertEquals(expected, addedStudent);
    }

    @Test
    public void testAddAssignment() {
        final var expected = new Tema("5", "cruduri", 12, 11);
        when(temaXMLRepository.save(expected)).thenReturn(expected);

        final var addedAssignment = service.saveTema("5", "cruduri", 12, 11);

        Assertions.assertEquals(expected, addedAssignment);
    }

    @Test
    public void addStudentAndAssignment() {
        testAddStudent();
        testAddAssignment();
    }

    @Test
    public void testAll() {
        testAddGrade();
        testAddStudent();
        testAddAssignment();
    }
}
