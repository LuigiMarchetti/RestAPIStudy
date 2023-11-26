package com.study.luigi.student;

import com.study.luigi.student.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping
    public ResponseEntity insertStudent(@RequestBody Student student) {
        return this.studentService.addNewStudent(student);
    }

    @PutMapping(path = "/{studentId}")
    public ResponseEntity updateStudent(@PathVariable("studentId") Long id,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String email) {
        return this.studentService.updateStudent(id, name, email);
    }

    @DeleteMapping(path = "/{studentId}")
    public ResponseEntity deleteStudent(@PathVariable("studentId") Long id) {
        return studentService.deleteStudent(id);
    }
}
