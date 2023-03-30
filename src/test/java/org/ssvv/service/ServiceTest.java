package org.ssvv.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.ssvv.repository.NotaXMLRepository;
import org.ssvv.repository.StudentXMLRepository;
import org.ssvv.repository.TemaXMLRepository;
import org.ssvv.validation.NotaValidator;
import org.ssvv.validation.StudentValidator;
import org.ssvv.validation.TemaValidator;

public class ServiceTest {

    private final StudentXMLRepository studentXMLRepository = new StudentXMLRepository(new StudentValidator(), "test-student.xml");
    private final TemaXMLRepository temaXMLRepository = new TemaXMLRepository(new TemaValidator(), "test-tema.xml");
    private final NotaXMLRepository notaXMLRepository = new NotaXMLRepository(new NotaValidator(), "test-nota.xml");
    private final Service service = new Service(studentXMLRepository, temaXMLRepository, notaXMLRepository);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // EC

    // "ID" EC
    // id == null, id == ""                         -> invalid
    @Test
    public void testSaveStudent_with_id_null() {
        final var student = service.saveStudent(null, "name", 500, "email@email.com", "Costel");
        Assertions.assertNull(student);
    }

    @Test
    public void testSaveStudent_with_id_empty() {
        final var student = service.saveStudent("", "name", 500, "email@email.com", "Costel");
        Assertions.assertNull(student);
    }

    // "Nume" EC
    // nume == null, nume == ""
    @Test
    public void testSaveStudent_with_nume_null() {
        final var student = service.saveStudent("id", null, 500, "email@email.com", "Costel");
        Assertions.assertNull(student);
    }

    @Test
    public void testSaveStudent_with_nume_empty() {
        final var student = service.saveStudent("id", "", 500, "email@email.com", "Costel");
        Assertions.assertNull(student);
    }

    // "Grupa" EC
    @Test
    public void testSaveStudent_with_group_below_111() {
        final var student = service.saveStudent("id", "name", 1, "email@email.com", "Costel");
        Assertions.assertNull(student);
    }

    @Test
    public void testSaveStudent_with_group_above_937() {
        final var student = service.saveStudent("id", "name", 1000, "email@email.com", "Costel");
        Assertions.assertNull(student);
    }

    @Test
    public void testSaveStudent_valid() {
        final var student = service.saveStudent("id", "name", 500, "email@email.com", "Costel");

        Assertions.assertNotNull(student);
        Assertions.assertEquals("id", student.getID());
        Assertions.assertEquals("name", student.getNume());
        Assertions.assertEquals(500, student.getGrupa());
        Assertions.assertEquals("email@email.com", student.getEmail());
        Assertions.assertEquals("Costel", student.getProf());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testSaveStudent_group_110() {
        final var student = service.saveStudent("id", "name", 110, "email@email.com", "Michael");
        Assertions.assertNull(student);
    }

    @Test
    public void testSaveStudent_group_111() {
        final var student = service.saveStudent("id", "name", 111, "email@email.com", "Michael");
        Assertions.assertEquals(111, student.getGrupa());
    }

    @Test
    public void testSaveStudent_group_112() {
        final var student = service.saveStudent("id", "name", 112, "email@email.com", "Michael");
        Assertions.assertEquals(112, student.getGrupa());
    }

    @Test
    public void testSaveStudent_group_936() {
        final var student = service.saveStudent("id", "name", 936, "emai@email.com", "Michael");
        Assertions.assertEquals(936, student.getGrupa());
    }

    @Test
    public void testSaveStudent_group_937() {
        final var student = service.saveStudent("id", "name", 937, "email@email.com", "Michael");
        Assertions.assertEquals(937, student.getGrupa());
    }

    @Test
    public void testSaveStudent_group_938() {
        final var student = service.saveStudent("id", "name", 938, "email@email.com", "Michael");
        Assertions.assertNull(student);
    }

    @Test
    public void testSaveStudent_id_length_1() {
        final var student = service.saveStudent("a", "name", 500, "email@email.com", "Michael");
        Assertions.assertEquals("a", student.getID());
    }

    @Test
    public void testSaveStudent_name_length_1() {
        final var student = service.saveStudent("id", " ", 500, "email@email.com", "Michael");
        Assertions.assertEquals(" ", student.getNume());
    }
}