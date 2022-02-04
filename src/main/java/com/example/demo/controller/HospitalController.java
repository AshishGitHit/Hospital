package com.example.demo.controller;

import com.example.demo.entity.Bed;
import com.example.demo.entity.Doctor;
import com.example.demo.entity.Patient;
import com.example.demo.entity.Type;
import com.example.demo.service.HospitalServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "hospital")
public class HospitalController {

    @Autowired
    public HospitalServiceImpl hospitalService;


    @Operation(summary = "Get Beds.", description = "This end-point will return all the beds.")
    @GetMapping(value = "/get/AllBeds")
    public ResponseEntity<List<Bed>> getAllBeds() {
        return ResponseEntity.ok(hospitalService.getAllBeds());
    }

    @Operation(summary = "Add Bed.", description = "This end-point will add the bed.")
    @PostMapping(value = "/add/Bed")
    public ResponseEntity<Bed> addBed(@RequestBody @Valid Bed bed) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hospitalService.addBed(bed));
    }

    @Operation(summary = "Get Types.", description = "This end-point will return all the types.")
    @GetMapping(value = "/get/AllTypes")
    public ResponseEntity<List<Type>> getAllTypes() {
        return ResponseEntity.ok(hospitalService.getAllTypes());
    }


    @Operation(summary = "Add Type.", description = "This end-point will add the type.")
    @PostMapping(value = "/add/Type")
    public ResponseEntity<Type> addType(@RequestBody @Valid Type type) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hospitalService.addType(type));
    }

    @Operation(summary = " Delete Bed By Id.", description = "This end-point will delete the bed.")
    @DeleteMapping(value = "/bed/{id}")
    public ResponseEntity<String> deleteBedById(@RequestParam UUID b_id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(hospitalService.deleteBedById(b_id));
    }

    @Operation(summary = " Delete Type By Id.", description = "This end-point will delete the type.")
    @DeleteMapping(value = "/type/{id}")
    public ResponseEntity<String> deleteTypeById(@RequestParam Integer type_id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(hospitalService.deleteTypeById(type_id));
    }

    @Operation(summary = "Get Patients.", description = "This end-point will return all the patients.")
    @GetMapping(value = "/get/AllPatients")
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(hospitalService.getAllPatients());
    }

    @Operation(summary = "Get Patients By Doctor Id.", description = "This end-point will return " +
            "all the patients by doctor id.")
    @GetMapping(value = "/patient/doctor/{id}")
    public ResponseEntity<List<Patient>> getPatientByDoctorId(@RequestParam @Valid UUID d_id) {
        return ResponseEntity.ok(hospitalService.getPatientByDoctorId(d_id));
    }

    @Operation(summary = "Get Patient By Bed Id.", description = "This end-point will return the patient by bed id.")
    @GetMapping(value = "/patient/bed/{id}")
    public ResponseEntity<Patient> getPatientByBedId(@RequestParam @Valid UUID b_id) {
        return ResponseEntity.ok(hospitalService.getPatientByBedId(b_id));
    }

    @Operation(summary = "Get Patients By Type Id.", description = "This end-point will return " +
            "all the patients by type id.")
    @GetMapping(value = "/patient/type/{id}")
    public ResponseEntity<List<Patient>> getPatientByTypeId(@RequestParam @Valid Integer type_id) {
        return ResponseEntity.ok(hospitalService.getPatientByTypeId(type_id));
    }

    @Operation(summary = "Get Doctors By Type Id.", description = "This end-point will return " +
            "all the doctors by type id.")
    @GetMapping(value = "/doctor/type/{id}")
    public ResponseEntity<List<Doctor>> getDoctorByTypeId(@RequestParam @Valid Integer type_id) {
        return ResponseEntity.ok(hospitalService.getDoctorByTypeId(type_id));
    }

    @Operation(summary = "Add Patient.", description = "This end-point will add the patient.")
    @PostMapping(value = "/add/Patient")
    public ResponseEntity<Patient> addPatient(@RequestBody @Valid Patient patient) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hospitalService.addPatient(patient));
    }

    @Operation(summary = " Delete Patient By Id.", description = "This end-point will delete the patient.")
    @DeleteMapping(value = "/patient/{id}")
    public ResponseEntity<String> deletePatientById(@RequestParam UUID patient_id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(hospitalService.deletePatientById(patient_id));
    }

    @Operation(summary = "Get Doctors.", description = "This end-point will return all the doctors.")
    @GetMapping(value = "/get/AllDoctors")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(hospitalService.getAllDoctors());
    }

    @Operation(summary = "Add Doctor.", description = "This end-point will add the doctor.")
    @PostMapping(value = "/add/Doctor")
    public ResponseEntity<Doctor> addDoctor(@RequestBody @Valid Doctor doctor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hospitalService.addDoctor(doctor));
    }

    @Operation(summary = " Delete Doctor By Id.", description = "This end-point will delete the doctor.")
    @DeleteMapping(value = "/doctor/{id}")
    public ResponseEntity<String> deleteDoctorById(@RequestParam UUID d_id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(hospitalService.deleteDoctorById(d_id));
    }

    @GetMapping(value = "/patient/page")
    public ResponseEntity<Page<Patient>> getAllPatientByPageable(@RequestParam(defaultValue = "1") Integer pageNo,
                                                        @RequestParam(defaultValue = "10") Integer size)
    {

        return ResponseEntity.ok(hospitalService.getAllPatientByPageable(pageNo, size));
    }

    @PutMapping(value = "/type/update/{id}")
    public ResponseEntity<Type> updateType(@PathVariable Integer id, @RequestBody @Valid Type type) {
        return ResponseEntity.ok(hospitalService.updateType(id, type));
    }

//    @PostMapping(value = "/getbill")
//    public String getBill(@RequestBody BillGenerate billGenerate){
//        return billService.g(bills);
//    }

}
