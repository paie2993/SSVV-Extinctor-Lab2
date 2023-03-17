package org.ssvv.service;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

import org.ssvv.domain.Nota;
import org.ssvv.domain.Pair;
import org.ssvv.domain.Student;
import org.ssvv.domain.Tema;
import org.ssvv.repository.NotaXMLRepository;
import org.ssvv.repository.StudentXMLRepository;
import org.ssvv.repository.TemaXMLRepository;

public class Service {
    private StudentXMLRepository studentXmlRepo;
    private TemaXMLRepository temaXmlRepo;
    private NotaXMLRepository notaXmlRepo;

    public Service(StudentXMLRepository studentXmlRepo, TemaXMLRepository temaXmlRepo, NotaXMLRepository notaXmlRepo) {
        this.studentXmlRepo = studentXmlRepo;
        this.temaXmlRepo = temaXmlRepo;
        this.notaXmlRepo = notaXmlRepo;
    }

    public Iterable<Student> findAllStudents() {
        return studentXmlRepo.findAll();
    }

    public Iterable<Tema> findAllTeme() {
        return temaXmlRepo.findAll();
    }

    public Iterable<Nota> findAllNote() {
        return notaXmlRepo.findAll();
    }

    public Student saveStudent(String id, String nume, int grupa, final String email, final String prof) {
        final Student student = new Student(id, nume, grupa, email, prof);
        return studentXmlRepo.save(student);
    }

    public Tema saveTema(String id, String descriere, int deadline, int startline) {
        Tema tema = new Tema(id, descriere, deadline, startline);
        return temaXmlRepo.save(tema);
    }

    public Nota saveNota(String idStudent, String idTema, double valNota, int predata, String feedback) {
        if (studentXmlRepo.findOne(idStudent) == null || temaXmlRepo.findOne(idTema) == null) {
            return null;
        }

        int deadline = temaXmlRepo.findOne(idTema).getDeadline();

        if (predata - deadline > 2) {
            valNota = 1;
        } else {
            valNota = valNota - 2.5 * (predata - deadline);
        }
        Nota nota = new Nota(new Pair(idStudent, idTema), valNota, predata, feedback);
        return notaXmlRepo.save(nota);
    }

    public int deleteStudent(String id) {
        Student result = studentXmlRepo.delete(id);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int deleteTema(String id) {
        Tema result = temaXmlRepo.delete(id);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public Student updateStudent(String id, String numeNou, int grupaNoua, final String email, final String prof) {
        Student studentNou = new Student(id, numeNou, grupaNoua, email, prof);
        return studentXmlRepo.update(studentNou);
    }

    public int updateTema(String id, String descriereNoua, int deadlineNou, int startlineNou) {
        Tema temaNoua = new Tema(id, descriereNoua, deadlineNou, startlineNou);
        Tema result = temaXmlRepo.update(temaNoua);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int extendDeadline(String id, int noWeeks) {
        Tema tema = temaXmlRepo.findOne(id);

        if (tema != null) {
            LocalDate date = LocalDate.now();
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            int currentWeek = date.get(weekFields.weekOfWeekBasedYear());

            if (currentWeek >= 39) {
                currentWeek = currentWeek - 39;
            } else {
                currentWeek = currentWeek + 12;
            }

            if (currentWeek <= tema.getDeadline()) {
                int deadlineNou = tema.getDeadline() + noWeeks;
                return updateTema(tema.getID(), tema.getDescriere(), deadlineNou, tema.getStartline());
            }
        }
        return 0;
    }

    public void createStudentFile(String idStudent, String idTema) {
        Nota nota = notaXmlRepo.findOne(new Pair(idStudent, idTema));

        notaXmlRepo.createFile(nota);
    }
}
