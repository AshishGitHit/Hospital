package com.example.demo.exception;

public class TypeMismatchExc extends RuntimeException {
    public TypeMismatchExc(String patient_not_added) {
        super(patient_not_added);
    }
}
