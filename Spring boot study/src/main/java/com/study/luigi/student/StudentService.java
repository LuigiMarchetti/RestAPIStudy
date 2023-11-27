package com.study.luigi.student;

import com.study.luigi.student.entity.Student;
import com.study.luigi.student.utils.MessageDTO;
import com.study.luigi.student.utils.StudentConstants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    public ResponseEntity<List<Student>> getStudents(int page, int pageLimit) {
        Pageable pageable = PageRequest.of(page -1, pageLimit);
        Page<Student> studentList = studentRepository.findAll(pageable);
        if (studentList.getContent().isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(studentList.getContent());
    }

    public ResponseEntity<Student> getStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (!student.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build() ;
        }

        return ResponseEntity.ok().body(student.get());
    }

    public ResponseEntity addNewStudent(Student student){
        if (student.getEmail() == null || student.getEmail().isEmpty()){
            return ResponseEntity.badRequest().body(new MessageDTO(StudentConstants.MANDOTORYEMAIL));
        }
        Optional<Student> emailIsAlreadyRegistered = studentRepository.findStudentByEmail(student.getEmail());

        if (emailIsAlreadyRegistered.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageDTO(StudentConstants.EMAILALREADYREGISTERED));
        }

        studentRepository.save(student);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(student.getId())
                .toUri();

        return ResponseEntity.created(uri).body(student);
    }

    public ResponseEntity deleteStudent(Long id) {
        Boolean studentExist = studentRepository.existsById(id);

        if (!studentExist) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO(StudentConstants.STUDENTNOTFOUND));
        }
        studentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    public ResponseEntity updateStudent(Long id, String name, String email) {
        Optional<Student> studentExists = studentRepository.findById(id);
        if (!studentExists.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO(StudentConstants.STUDENTNOTFOUND));
        }

        Student student = studentExists.get();

        if (name != null && !name.isEmpty() && !Objects.equals(name, student.getName())) {
            student.setName(name);
        }

        if (email != null && !email.isEmpty() && !Objects.equals(email, student.getEmail())) {
            //Optional<Student> emailExists = studentRepository.findByEmail(email);
            student.setEmail(email);
        }

        return ResponseEntity.noContent().build();
    }
}
