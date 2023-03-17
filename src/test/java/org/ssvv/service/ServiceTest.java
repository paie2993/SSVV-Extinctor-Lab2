package org.ssvv.service;

import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.ssvv.domain.Student;
import org.ssvv.repository.NotaXMLRepository;
import org.ssvv.repository.StudentXMLRepository;
import org.ssvv.repository.TemaXMLRepository;
import org.ssvv.validation.NotaValidator;
import org.ssvv.validation.StudentValidator;
import org.ssvv.validation.TemaValidator;

public class ServiceTest extends TestCase {

    private final StudentXMLRepository studentXMLRepository = new StudentXMLRepository(new StudentValidator(), "test-student.xml");
    private final TemaXMLRepository temaXMLRepository = new TemaXMLRepository(new TemaValidator(), "test-tema.xml");
    private final NotaXMLRepository notaXMLRepository = new NotaXMLRepository(new NotaValidator(), "test-nota.xml");
    private final Service service = new Service(studentXMLRepository, temaXMLRepository, notaXMLRepository);

    @Test
    public void testSaveStudent_with_group_below_111() {
        final var result = new Student("id", "name", 1, "email@email.com", "Costel");
        Assertions.assertNull(result);
    }

    @Test
    public void testSaveStudent_with_group_between_111_937() {
        final var result = service.saveStudent("id", "name", 200, "email@email.com", "Costel");
        Assertions.assertEquals("id", result.getID());
        Assertions.assertEquals("name", result.getNume());
        Assertions.assertEquals(200, result.getGrupa());
        Assertions.assertEquals("email@email.com", result.getEmail());
        Assertions.assertEquals("Costel", result.getProf());
    }

    @Test
    public void testSaveStudent_with_group_above_937() {
        final var result = new Student("id", "name", 1000, "email@email.com", "Costel");
        Assertions.assertNull(result);
    }
}