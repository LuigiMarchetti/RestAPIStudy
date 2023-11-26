package com.study.luigi.student;

import com.study.luigi.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("select a from Student a where a.email = ?1") //Not mandatory
    Optional<Student> findStudentByEmail(String email);
}
