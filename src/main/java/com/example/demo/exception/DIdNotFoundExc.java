package com.example.demo.exception;

public class DIdNotFoundExc extends RuntimeException {
    public DIdNotFoundExc(String doctor_id_not_found) {
        super(doctor_id_not_found);
    }
}
