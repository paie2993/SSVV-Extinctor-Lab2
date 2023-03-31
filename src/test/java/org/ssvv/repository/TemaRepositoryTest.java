package org.ssvv.repository;

import org.junit.jupiter.api.*;
import org.ssvv.domain.Tema;
import org.ssvv.validation.TemaValidator;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class TemaRepositoryTest {

    private static final TemaValidator validatorTeme = new TemaValidator();

    private CRUDRepository<String, Tema> repository;

    private static final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    private static final PrintStream ps = new PrintStream(baos);

    @BeforeAll
    public static void setUpAll() {
        System.setOut(ps);
    }

    @BeforeEach
    public void setUp() {
        repository = new TemaRepository(validatorTeme);
    }

    @AfterEach
    public void tearDown() {
        baos.reset();
    }

    @Test
    public void test_whenAllInputIsValid_thenSaveAndReturnTema() {
        final var entity = validTema();

        final var savedEntity = repository.save(entity);

        Assertions.assertSame(entity, savedEntity);
    }

    @Test
    public void test_whenStartlineIsNotValid_thenPrintErrorMessage() {
        final var entity = validTema();
        entity.setDeadline(12);
        entity.setStartline(13);

        final var savedEntity = repository.save(entity);

        Assertions.assertNull(savedEntity);
//        Assertions.assertEquals("Entitatea nu este valida! \n\n", baos.toString());
    }

    @Test
    public void test_whenDeadlineIsNotValid_thenPrintErrorMessage() {
        final var entity = validTema();
        entity.setDeadline(15);

        final var savedEntity = repository.save(entity);

        Assertions.assertNull(savedEntity);
//        Assertions.assertEquals("Entitatea nu este valida! \n\n", baos.toString());
    }

    @Test
    public void test_whenDescriereIsNotValid_thenPrintErrorMessage() {
        final var entity = validTema();
        entity.setDescriere(null);

        final var savedEntity = repository.save(entity);

        Assertions.assertNull(savedEntity);
//        Assertions.assertEquals("Entitatea nu este valida! \n\n", baos.toString());
    }

    @Test
    public void test_whenIdIsNotValid_thenPrintErrorMessage() {
        final var entity = validTema();
        entity.setID(null);

        final var savedEntity = repository.save(entity);

        Assertions.assertNull(savedEntity);
//        Assertions.assertEquals("Entitatea nu este valida! \n\n", baos.toString());
    }

    private static Tema validTema() {
        return new Tema("id", "descriere", 14, 11);
    }

}