package com.study.luigi.student;

import com.study.luigi.student.entity.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student Matheus = new Student(1L, "Matheus Rocha", "matheusRocha@gmail.com", LocalDate.of(2004, Month.AUGUST,02));
            Student Joao = new Student(2L, "João Carlos", "joao@gmail.com", LocalDate.of(2000, Month.JANUARY,01));

            studentRepository.saveAll(List.of(Matheus, Joao));
        };
    }
}
