package com.example.demo.service;

import com.example.demo.entity.Bed;
import com.example.demo.entity.Doctor;
import com.example.demo.entity.Patient;
import com.example.demo.entity.Type;
import com.example.demo.exception.*;
import com.example.demo.repository.BedRepo;
import com.example.demo.repository.DoctorRepo;
import com.example.demo.repository.PatientRepo;
import com.example.demo.repository.TypeRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HospitalServiceImpl implements HospitalService{

    @Autowired
    private final BedRepo bedRepo;
    private final PatientRepo patientRepo;
    private final DoctorRepo doctorRepo;
    private final TypeRepo typeRepo;
    private final EntityManager entityManager;

    @Autowired
    public HospitalServiceImpl (BedRepo bedRepo, PatientRepo patientRepo,
                                DoctorRepo doctorRepo, TypeRepo typeRepo, EntityManager entityManager)
    {
        this.bedRepo = bedRepo;
        this.patientRepo = patientRepo;
        this.doctorRepo = doctorRepo;
        this.typeRepo = typeRepo;
        this.entityManager = entityManager;
    }

    public List<Bed> getAllBeds() {
        if(bedRepo.findAll().isEmpty()) {
            throw new PNotAddedExc("No bed found.");
        }
        return bedRepo.findAll();
    }

    public Bed addBed(Bed bed) {

        return bedRepo.save(bed);
    }

    public List<Patient> getAllPatients() {
        if(patientRepo.findAll().isEmpty()) {
            throw new PNotAddedExc("No patient found.");
        }
        return patientRepo.findAll();
    }

    public Patient addPatient(@NotNull Patient patient) {
        List<Patient> p = patientRepo.findByBedId(patient.getBed().getId());

        if(patient.getName().isBlank()) {
            throw new PNameExc("No pat");
        }
        else if(patient.getAddress().isBlank()) {
            throw new PAddressExc("Patient not added.");
        }
        else if(patient.getAge()<=0) {
            throw new PAgeExc("Patient not added.");
        }
        else if(!p.isEmpty()) {
            throw new BAlrdyOccExc("Patient not added.");
        }
        else if(patient.getDoctor().getType().getType_id()!=(patient.getType().getType_id())) {
            throw new TypeMismatchExc("Patient not added");
        }
        return patientRepo.save(patient);
    }

    public List<Doctor> getAllDoctors() {
        if(doctorRepo.findAll().isEmpty()) {
            throw new PNotAddedExc("No doctor found.");
        }
        return doctorRepo.findAll();
    }


    public Doctor addDoctor(@NotNull Doctor doctor) {
        if(doctor.getName().isBlank()) {
            throw new DNameExc("Doctor not added.");
        }
        else if(doctor.getEmail().isBlank()) {
            throw  new DEmailExc("No doctor added");
        }
        return doctorRepo.save(doctor);
    }

    public String deleteBedById(UUID b_id) {
        if(!bedRepo.existsById(b_id)) {
            throw new BIdNotFoundExc("Bed id not found.");
        }
        bedRepo.deleteById(b_id);
        return "deleted";
    }

    public String deletePatientById(UUID patient_id) {
        if(!patientRepo.existsById(patient_id)) {
            throw new PIdNotFoundExc("Patient id not found.");
        }
        patientRepo.deleteById(patient_id);
        return "deleted";
    }

    public String deleteDoctorById(UUID d_id) {
        if(!doctorRepo.existsById(d_id)) {
            throw new DIdNotFoundExc("Doctor id not found");
        }
        doctorRepo.deleteById(d_id);
        return "deleted";
    }

    public List<Patient> getPatientByDoctorId(UUID d_id) {
        if(!doctorRepo.existsById(d_id)) {
            throw new DIdNotFoundExc("No patient found.");
        }
        return patientRepo.getPatientByDoctorId(d_id);
    }

    public Patient getPatientByBedId(UUID b_id) {
        if(!bedRepo.existsById(b_id)) {
            throw new BIdNotFoundExc("Bed not found.");
        }
        return patientRepo.findByB_Id(b_id);
    }


    public Type addType(@NotNull Type type) {
        if (type.getType_id()<=0 || type.getType_id()>4) {
            throw new TTypeIdExc("No type added");
        } else if (type.getTypes().isBlank()) {
            throw new TTypeExc("Type not added.");
        }
        List<Type> ty = typeRepo.findAll();
        for (Type t:ty) {
            if (t.getTypes().equals(type.getTypes())) {
                throw new TTypeNameExc("Type can't be duplicate");
            }
        }
        return typeRepo.save(type);
    }

    public List<Patient> getPatientByTypeId(Integer type_id) {
        if(!typeRepo.existsById(type_id)) {
            throw new TIdNotFoundExc("Type not found.");
        }
        return patientRepo.findAll();
    }

    public List<Doctor> getDoctorByTypeId(Integer type_id) {
        if(!typeRepo.existsById(type_id)) {
            throw new TIdNotFoundExc("Type not found.");
        }
        return doctorRepo.findAll();
    }

    public List<Type> getAllTypes() {
        if(typeRepo.findAll().isEmpty()) {
            throw new TNotFoundExc("No types found.");
        }
        return typeRepo.findAll();
    }

    public String deleteTypeById(Integer type_id) {
        if(!typeRepo.existsById(type_id)) {
            throw new TIdNotFoundExc("Type id not found");
        }
        typeRepo.deleteById(type_id);
        return "deleted";
    }

    public Page getAllPatientByPageable(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        Page<Patient> pages = patientRepo.findAll(pageable);
        return pages;
    }

    public Type updateType(Integer id, Type type) {
        Optional<Type> t=typeRepo.findById(id);
        if (t.isEmpty()){
            return null;
        }
        else {
            return typeRepo.save(type);
        }
    }
}
