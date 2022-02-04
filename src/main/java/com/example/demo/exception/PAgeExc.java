package com.example.demo.exception;

public class PAgeExc extends RuntimeException {
    public PAgeExc(String patient_not_added) {
        super(patient_not_added);
    }
}
