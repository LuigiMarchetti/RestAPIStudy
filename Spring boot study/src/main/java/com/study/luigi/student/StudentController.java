package com.study.luigi.student;

import com.study.luigi.student.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "v1/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getStudents(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "pageLimit", defaultValue = "10", required = false) int pageLimit
    ) {
        return studentService.getStudents(page, pageLimit);
    }


    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable("studentId") Long id) {
        return studentService.getStudent(id);
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
