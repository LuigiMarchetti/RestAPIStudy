package com.study.luigi;

import com.study.luigi.student.entity.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	//Demo
	@GetMapping
	public List<Student> hello(){
		return List.of(new Student(1L, "Luigi", "luigi@gmail.com", LocalDate.of(2004,01,06)));
	}
}
