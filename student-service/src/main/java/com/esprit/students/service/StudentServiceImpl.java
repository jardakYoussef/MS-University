package com.esprit.students.service;

import com.esprit.students.exception.StudentNotFoundException;
import com.esprit.students.repository.StudentRepository;
import com.esprit.students.model.Student;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getStudentById(String id) {
        return studentRepository.findById(id);
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Student studentDto, String id) throws StudentNotFoundException {
        Optional<Student> student = this.studentRepository.findById(id);
        if (student.isPresent()) {
            return this.studentRepository.save(studentDto);
        } else {
            throw new StudentNotFoundException(id);
        }
    }

    @Override
    public void deleteStudent(String id) {
        studentRepository.deleteById(id);
    }
}
