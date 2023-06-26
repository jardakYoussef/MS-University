package com.esprit.students.exception;


public class StudentNotFoundException extends Throwable {
    public StudentNotFoundException(String id) {
        super("Cannot found student number [" + id + "]");
    }
}
