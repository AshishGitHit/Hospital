package com.example.demo.exception;

public class PIdNotFoundExc extends RuntimeException {
    public PIdNotFoundExc(String patient_id_not_found) {
        super(patient_id_not_found);
    }
}
