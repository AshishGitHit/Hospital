package com.example.demo.exception;

public class DEmailExc extends RuntimeException {
    public DEmailExc(String no_doctor_added) {
        super(no_doctor_added);
    }
}
