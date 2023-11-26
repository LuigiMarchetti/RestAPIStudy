package com.study.luigi.student;

import com.study.luigi.student.entity.Student;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public ResponseEntity<List<Student>> getStudents() {
        List<Student> studentList = studentRepository.findAll();
        if (studentList.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(studentList);
    }

    public ResponseEntity addNewStudent(Student student){
        if (student.getEmail() == null || student.getEmail().isEmpty()){
            return ResponseEntity.badRequest().body("Email is mandatory");
        }
        Optional<Student> emailIsAlreadyRegistered = studentRepository.findStudentByEmail(student.getEmail());

        if (emailIsAlreadyRegistered.isPresent()) {
            return ResponseEntity.badRequest().body("This email is already registered");
        }

        studentRepository.save(student);
        return ResponseEntity.ok(null);
    }

    public ResponseEntity deleteStudent(Long id) {
        Boolean studentExist = studentRepository.existsById(id);

        if (!studentExist) {
            return ResponseEntity.notFound().build();
        }
        studentRepository.deleteById(id);
        return ResponseEntity.ok(null);
    }

    @Transactional
    public ResponseEntity updateStudent(Long id, String name, String email) {
        Optional<Student> studentExists = studentRepository.findById(id);
        if (!studentExists.isPresent()) {
            return ResponseEntity.badRequest().body("Student don't exist");
        }

        Student student = studentExists.get();

        if (name != null && !name.isEmpty() && !Objects.equals(name, student.getName())) {
            student.setName(name);
        }

        if (email != null && !email.isEmpty() && !Objects.equals(email, student.getEmail())) {
            //Optional<Student> emailExists = studentRepository.findByEmail(email);
            student.setEmail(email);
        }

        return ResponseEntity.ok(null);
    }
}
