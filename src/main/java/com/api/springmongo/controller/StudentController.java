package com.api.springmongo.controller;

import com.api.springmongo.dto.StudentDTO;
import com.api.springmongo.model.Response;
import com.api.springmongo.model.Student;
import com.api.springmongo.service.StudentService;
import com.api.springmongo.service.StudentServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.core.convert.TypeDescriptor.map;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping(value = "/")
    public List<StudentDTO> getAllStudents() {

        List<Student> studentList = studentService.findAll();
        List<StudentDTO> res = studentList.stream()
                .map(e -> new StudentDTO(e.getId(), e.getName(), e.getStudentNumber(), e.getEmail(), e.getCourseList(), e.getGpa()))
                .collect(Collectors.toList());
        return res;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> saveOrUpdateStudent(@RequestBody StudentDTO studentDTO) {
        ObjectMapper mapper = new ObjectMapper();
        Student student = mapper.convertValue(studentDTO, Student.class);
        Student st = studentService.saveOrUpdateStudent(student);
        Response res = new Response();
        if(st!=null){
            res.setStatus(true);
            res.setMessage("Student added successfully");
        }else {
            res.setStatus(false);
            res.setMessage("Student added failed");
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{email}/{studentNumber}")
    public List<StudentDTO> getAllStudents(@PathVariable String email, @PathVariable long studentNumber) {

        List<Student> studentList = studentService.findByEmailAndStudentNumber(email,studentNumber);
        List<StudentDTO> res = studentList.stream()
                .map(e -> new StudentDTO(e.getId(), e.getName(), e.getStudentNumber(), e.getEmail(), e.getCourseList(), e.getGpa()))
                .collect(Collectors.toList());
        return res;
    }

    @GetMapping(value = "/get/{email}")
    public List<StudentDTO> getAllByQuery(@PathVariable String email){
        List<Student> studentList = studentService.findByQuery(email);
        List<StudentDTO> res = studentList.stream()
                .map(e -> new StudentDTO(e.getId(), e.getName(), e.getStudentNumber(), e.getEmail(), e.getCourseList(), e.getGpa()))
                .collect(Collectors.toList());
        return res;
    }
}
