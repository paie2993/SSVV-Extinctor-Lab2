package org.ssvv.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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

public class TestIntegration {

    private final StudentXMLRepository studentXMLRepository = new StudentXMLRepository(new StudentValidator(), "test-student.xml");
    private final TemaXMLRepository temaXMLRepository = new TemaXMLRepository(new TemaValidator(), "test-tema.xml");
    private final NotaXMLRepository notaXMLRepository = new NotaXMLRepository(new NotaValidator(), "test-nota.xml");

    private final Service service = new Service(studentXMLRepository, temaXMLRepository, notaXMLRepository);

    @Test
    public void testAddGrade() {
        final var addedGrade = service.saveNota("5", "5", 10, 12, "Excelent");
        final var expected = new Nota(new Pair<>("5", "5"), 10, 12, "Excelent");

        Assertions.assertEquals(expected, addedGrade);
    }


    @Test
    public void testAddStudent() {
        final var addedStudent = service.saveStudent("5", "xd", 200, "@outlook", "Carmen");
        final var expected = new Student("5", "xd", 200, "@outlook", "Carmen");

        Assertions.assertEquals(expected, addedStudent);
    }

    @Test
    public void testAddAssignment() {
        final var addedAssignment = service.saveTema("5", "cruduri", 12, 11);
        final var expected = new Tema("5", "cruduri", 12, 11);

        Assertions.assertEquals(expected, addedAssignment);
    }

    // Big-Bang
    @Test
    public void testAll() {
        testAddGrade();
        testAddStudent();
        testAddAssignment();
    }
}
