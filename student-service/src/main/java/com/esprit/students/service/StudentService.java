package com.esprit.students.service;

import com.esprit.students.exception.StudentNotFoundException;
import com.esprit.students.model.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface StudentService {
     List<Student> getAllStudents();
     Optional<Student> getStudentById(String id);
     Student createStudent(Student student);
     Student updateStudent(Student student, String id) throws StudentNotFoundException;
     void deleteStudent(String id);
}
