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
        final var result = service.saveStudent(null, "name", 500, "email@email.com", "Costel");
        Assertions.assertNull(result);
    }

    @Test
    public void testSaveStudent_with_id_empty() {
        final var result = service.saveStudent("", "name", 500, "email@email.com", "Costel");
        Assertions.assertNull(result);
    }

    // "Nume" EC
    // nume == null, nume == ""
    @Test
    public void testSaveStudent_with_nume_null() {
        final var result = service.saveStudent("id", null, 500, "email@email.com", "Costel");
        Assertions.assertNull(result);
    }

    @Test
    public void testSaveStudent_with_nume_empty() {
        final var result = service.saveStudent("id", "", 500, "email@email.com", "Costel");
        Assertions.assertNull(result);
    }

    // "Grupa" EC
    @Test
    public void testSaveStudent_with_group_below_111() {
        final var result = service.saveStudent("id", "name", 1, "email@email.com", "Costel");
        Assertions.assertNull(result);
    }

    @Test
    public void testSaveStudent_with_group_above_937() {
        final var result = service.saveStudent("id", "name", 1000, "email@email.com", "Costel");
        Assertions.assertNull(result);
    }

    @Test
    public void testSaveStudent_valid() {
        final var result = service.saveStudent("id", "name", 500, "email@email.com", "Costel");

        Assertions.assertNotNull(result);
        Assertions.assertEquals("id", result.getID());
        Assertions.assertEquals("name", result.getNume());
        Assertions.assertEquals(500, result.getGrupa());
        Assertions.assertEquals("email@email.com", result.getEmail());
        Assertions.assertEquals("Costel", result.getProf());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // BVA
    // 109, 110  -> invalid
    // 111, 112  -> valid
    // 936, 937  -> valid
    // 938, 939  -> invalid
}