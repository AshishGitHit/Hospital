package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PIdNotFoundExc.class)
    private ResponseEntity<String> resourceNotFound(PIdNotFoundExc PIdNotFoundExc) {
        return new ResponseEntity<String>("Patient id doesnot exist.", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DIdNotFoundExc.class)
    private ResponseEntity<String> doctorResourceNotFound(DIdNotFoundExc
                                                                  doctorNotFound) {
        return new ResponseEntity<String>("Doctor id doesnot exist.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BIdNotFoundExc.class)
    private ResponseEntity<String> bedNotFound(BIdNotFoundExc BIdNotFoundExc) {
        return new ResponseEntity<String>("Bed id doesnot exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PNotAddedExc.class)
    private ResponseEntity<String> resourceNotFound2(PNotAddedExc patientNotFound) {
        return new ResponseEntity<String>("No record posted.", HttpStatus.OK);
    }

    @ExceptionHandler(DNameExc.class)
    private ResponseEntity<String> doctorNotFound2(DNameExc DNameExc) {
        return new ResponseEntity<String>("Check Doctor name", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PNameExc.class)
    private ResponseEntity<String> patientNotFound3(PNameExc PNameExc) {
        return new ResponseEntity<String>("Check Patient name.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DEmailExc.class)
    private ResponseEntity<String> dEmailExc(DEmailExc dEmailExc) {
        return new ResponseEntity<String>("Check Doctor email", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PAddressExc.class)
    private ResponseEntity<String> pAddressExc(PAddressExc pAddressExc) {
        return new ResponseEntity<String>("Check Patient address", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PAgeExc.class)
    private ResponseEntity<String> pAgeExc(PAgeExc pAgeExc) {
        return new ResponseEntity<String>("Check Patient age.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BAlrdyOccExc.class)
    private ResponseEntity<String> bAlrdyOccExc(BAlrdyOccExc bAlrdyOccExc) {
        return new ResponseEntity<String>("Bed Already occupied", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TTypeExc.class)
    private ResponseEntity<String> tTypeExc(TTypeExc tTypeExc) {
        return new ResponseEntity<String>("Type can't be blank.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TTypeIdExc.class)
    private ResponseEntity<String> tTypeIdExc(TTypeIdExc tTypeIdExc) {
        return new ResponseEntity<String>("Type id must be between 1 to 4.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TTypeNameExc.class)
    private ResponseEntity<String> tTypeNameExc(TTypeNameExc tTypeNameExc) {
        return new ResponseEntity<String>("Type can't be duplicate.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TIdNotFoundExc.class)
    private ResponseEntity<String> typeResourceNotFound(TIdNotFoundExc typeNotFound) {
        return new ResponseEntity<String>("Type id doesnot exist.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TNotFoundExc.class)
    private ResponseEntity<String> tNotFoundExc(TNotFoundExc TNotFoundExc) {
        return new ResponseEntity<String>("No Types added.", HttpStatus.OK);
    }

    @ExceptionHandler(TypeMismatchExc.class)
    private ResponseEntity<String> typeMismatchExc(TypeMismatchExc typeMismatchExc) {
        return new ResponseEntity<String>("Type Mismatch error.", HttpStatus.CREATED);
    }

}
