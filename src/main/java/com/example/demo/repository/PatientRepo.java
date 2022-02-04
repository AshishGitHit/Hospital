package com.example.demo.repository;

import com.example.demo.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PatientRepo extends JpaRepository<Patient, UUID> {

    @Query(value = "select * from patient p where p.d_id =:d_id",nativeQuery = true)
    List<Patient> getPatientByDoctorId(UUID d_id);

    @Query(value = "select * from patient pa where pa.b_id =:b_id",nativeQuery = true)
    Patient findByB_Id(UUID b_id);

    List<Patient> findByBedId(UUID id);

}
