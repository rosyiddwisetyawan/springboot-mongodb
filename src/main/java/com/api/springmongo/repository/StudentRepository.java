package com.api.springmongo.repository;

import com.api.springmongo.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface StudentRepository extends MongoRepository<Student, String> {

    Student findByStudentNumber(long studentNumber);

    Student findByEmail(String email);

    List<Student> findAllByOrderByGpaDesc();

    List<Student> findByEmailAndStudentNumber(String email, long studentNumber);

    @Query("{'email': {$regex: ?0 }}")
    List<Student> findByQuery(String email);

}
