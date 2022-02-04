package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.entity.Bed;
import com.example.demo.entity.Doctor;
import com.example.demo.entity.Patient;
import com.example.demo.entity.Type;
import com.example.demo.exception.BAlrdyOccExc;
import com.example.demo.exception.DEmailExc;
import com.example.demo.exception.DNameExc;
import com.example.demo.exception.PAddressExc;
import com.example.demo.exception.PAgeExc;
import com.example.demo.exception.PIdNotFoundExc;
import com.example.demo.exception.PNameExc;
import com.example.demo.exception.PNotAddedExc;
import com.example.demo.exception.TypeMismatchExc;
import com.example.demo.repository.BedRepo;
import com.example.demo.repository.DoctorRepo;
import com.example.demo.repository.PatientRepo;
import com.example.demo.repository.TypeRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {HospitalServiceImpl.class})
@ExtendWith(SpringExtension.class)
class HospitalServiceImplTest {
    @MockBean
    private TypeRepo typeRepo;

    @MockBean
    private BedRepo bedRepo;

    @MockBean
    private DoctorRepo doctorRepo;

    @MockBean
    private EntityManager entityManager;

    @Autowired
    private HospitalServiceImpl hospitalServiceImpl;

    @MockBean
    private PatientRepo patientRepo;


    @Test
    void shouldGetAllBeds() {
        Bed bed = new Bed();
        bed.setId(UUID.randomUUID());

        ArrayList<Bed> bedList = new ArrayList<>();
        bedList.add(bed);
        when(this.bedRepo.findAll()).thenReturn(bedList);
        List<Bed> actualAllBeds = this.hospitalServiceImpl.getAllBeds();
        assertSame(bedList, actualAllBeds);
        assertEquals(1, actualAllBeds.size());
        verify(this.bedRepo, atLeast(1)).findAll();
    }

    @Test
    void shouldAddBed() {
        Bed bed = new Bed();
        bed.setId(UUID.randomUUID());
        when(this.bedRepo.save((Bed) any())).thenReturn(bed);

        Bed bed1 = new Bed();
        bed1.setId(UUID.randomUUID());
        assertSame(bed, this.hospitalServiceImpl.addBed(bed1));
        verify(this.bedRepo).save((Bed) any());
    }

    @Test
    void shouldGetAllPatients() {
        Bed bed = new Bed();
        bed.setId(UUID.randomUUID());

        Doctor doctor = new Doctor();
        doctor.setEmail("jane.doe@example.org");
        doctor.setDoctor_id(UUID.randomUUID());
        doctor.setName("No patient found.");

        Patient patient = new Patient();
        patient.setAddress("42 Main St");
        patient.setAge(0);
        patient.setBed(bed);
        patient.setDoctor(doctor);
        patient.setPatient_id(UUID.randomUUID());
        patient.setName("No patient found.");

        ArrayList<Patient> patientList = new ArrayList<>();
        patientList.add(patient);
        when(this.patientRepo.findAll()).thenReturn(patientList);
        List<Patient> actualAllPatients = this.hospitalServiceImpl.getAllPatients();
        assertSame(patientList, actualAllPatients);
        assertEquals(1, actualAllPatients.size());
        verify(this.patientRepo, atLeast(1)).findAll();
    }

    @Test
    void shouldAddPatient() {
        when(this.patientRepo.save((Patient) any())).thenThrow(new PNotAddedExc("foo"));
        when(this.patientRepo.findByBedId((UUID) any())).thenReturn(new ArrayList<>());

        Bed bed = new Bed();
        bed.setId(UUID.randomUUID());

        Type type = new Type();
        type.setType_id(1);
        type.setTypes("Types");

        Doctor doctor = new Doctor();
        doctor.setDoctor_id(UUID.randomUUID());
        doctor.setEmail("jane.doe@example.org");
        doctor.setGender("Gender");
        doctor.setName("Name");
        doctor.setType(type);

        Type type1 = new Type();
        type1.setType_id(1);
        type1.setTypes("Types");

        Patient patient = new Patient();
        patient.setAddress("42 Main St");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        patient.setAdmittedOn(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        patient.setAge(1);
        patient.setBed(bed);
        patient.setDoctor(doctor);
        patient.setGender("Gender");
        patient.setName("Name");
        patient.setPatient_id(UUID.randomUUID());
        patient.setType(type1);
        assertThrows(PNotAddedExc.class, () -> this.hospitalServiceImpl.addPatient(patient));
        verify(this.patientRepo).findByBedId((UUID) any());
        verify(this.patientRepo).save((Patient) any());
    }

    @Test
    void shouldGetAllDoctors() {
        when(this.doctorRepo.findAll()).thenReturn(new ArrayList<>());
        assertThrows(PNotAddedExc.class, () -> this.hospitalServiceImpl.getAllDoctors());
        verify(this.doctorRepo).findAll();
    }


    @Test
    void shouldAddDoctor() {
        Type type = new Type();
        type.setType_id(1);
        type.setTypes("Types");

        Doctor doctor = new Doctor();
        doctor.setDoctor_id(UUID.randomUUID());
        doctor.setEmail("jane.doe@example.org");
        doctor.setGender("Gender");
        doctor.setName("Name");
        doctor.setType(type);
        when(this.doctorRepo.save((Doctor) any())).thenReturn(doctor);

        Type type1 = new Type();
        type1.setType_id(1);
        type1.setTypes("Types");

        Doctor doctor1 = new Doctor();
        doctor1.setDoctor_id(UUID.randomUUID());
        doctor1.setEmail("jane.doe@example.org");
        doctor1.setGender("Gender");
        doctor1.setName("Name");
        doctor1.setType(type1);
        assertSame(doctor, this.hospitalServiceImpl.addDoctor(doctor1));
        verify(this.doctorRepo).save((Doctor) any());
    }

    @Test
    void shouldDeleteBedById() {
        doNothing().when(this.bedRepo).deleteById((UUID) any());
        when(this.bedRepo.existsById((UUID) any())).thenReturn(true);
        assertEquals("deleted", this.hospitalServiceImpl.deleteBedById(UUID.randomUUID()));
        verify(this.bedRepo).deleteById((UUID) any());
        verify(this.bedRepo).existsById((UUID) any());
    }

    @Test
    void shouldDeletePatientById() {
        doNothing().when(this.patientRepo).deleteById((UUID) any());
        when(this.patientRepo.existsById((UUID) any())).thenReturn(true);
        assertEquals("deleted", this.hospitalServiceImpl.deletePatientById(UUID.randomUUID()));
        verify(this.patientRepo).deleteById((UUID) any());
        verify(this.patientRepo).existsById((UUID) any());
    }



//    @Test
//    void testGetAllBeds() {
//        when(this.bedRepo.findAll()).thenReturn(new ArrayList<>());
//        assertThrows(PNotAddedExc.class, () -> this.hospitalServiceImpl.getAllBeds());
//        verify(this.bedRepo).findAll();
//    }
//
//    @Test
//    void testGetAllBeds2() {
//        when(this.bedRepo.findAll()).thenThrow(new PNotAddedExc("foo"));
//        assertThrows(PNotAddedExc.class, () -> this.hospitalServiceImpl.getAllBeds());
//        verify(this.bedRepo).findAll();
//    }

//    @Test
//    void testGetAllPatients() {
//        when(this.patientRepo.findAll()).thenReturn(new ArrayList<>());
//        assertThrows(PNotAddedExc.class, () -> this.hospitalServiceImpl.getAllPatients());
//        verify(this.patientRepo).findAll();
//    }
//
//    @Test
//    void testGetAllPatients2() {
//        when(this.patientRepo.findAll()).thenThrow(new PNotAddedExc("foo"));
//        assertThrows(PNotAddedExc.class, () -> this.hospitalServiceImpl.getAllPatients());
//        verify(this.patientRepo).findAll();
//    }


//    @Test
//    void testAddPatient() {
//        Bed bed = new Bed();
//        bed.setId(UUID.randomUUID());
//
//        Type type = new Type();
//        type.setType_id(1);
//        type.setTypes("Types");
//
//        Doctor doctor = new Doctor();
//        doctor.setDoctor_id(UUID.randomUUID());
//        doctor.setEmail("jane.doe@example.org");
//        doctor.setGender("Gender");
//        doctor.setName("Name");
//        doctor.setType(type);
//
//        Type type1 = new Type();
//        type1.setType_id(1);
//        type1.setTypes("Types");
//
//        Patient patient = new Patient();
//        patient.setAddress("42 Main St");
//        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
//        patient.setAdmittedOn(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
//        patient.setAge(1);
//        patient.setBed(bed);
//        patient.setDoctor(doctor);
//        patient.setGender("Gender");
//        patient.setName("Name");
//        patient.setPatient_id(UUID.randomUUID());
//        patient.setType(type1);
//        when(this.patientRepo.save((Patient) any())).thenReturn(patient);
//        when(this.patientRepo.findByBedId((UUID) any())).thenReturn(new ArrayList<>());
//
//        Bed bed1 = new Bed();
//        bed1.setId(UUID.randomUUID());
//
//        Type type2 = new Type();
//        type2.setType_id(1);
//        type2.setTypes("Types");
//
//        Doctor doctor1 = new Doctor();
//        doctor1.setDoctor_id(UUID.randomUUID());
//        doctor1.setEmail("jane.doe@example.org");
//        doctor1.setGender("Gender");
//        doctor1.setName("Name");
//        doctor1.setType(type2);
//
//        Type type3 = new Type();
//        type3.setType_id(1);
//        type3.setTypes("Types");
//
//        Patient patient1 = new Patient();
//        patient1.setAddress("42 Main St");
//        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
//        patient1.setAdmittedOn(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
//        patient1.setAge(1);
//        patient1.setBed(bed1);
//        patient1.setDoctor(doctor1);
//        patient1.setGender("Gender");
//        patient1.setName("Name");
//        patient1.setPatient_id(UUID.randomUUID());
//        patient1.setType(type3);
//        assertSame(patient, this.hospitalServiceImpl.addPatient(patient1));
//        verify(this.patientRepo).findByBedId((UUID) any());
//        verify(this.patientRepo).save((Patient) any());
//    }

//
//    @Test
//    void testAddPatient3() {
//        Bed bed = new Bed();
//        bed.setId(UUID.randomUUID());
//
//        Type type = new Type();
//        type.setType_id(1);
//        type.setTypes("Types");
//
//        Doctor doctor = new Doctor();
//        doctor.setDoctor_id(UUID.randomUUID());
//        doctor.setEmail("jane.doe@example.org");
//        doctor.setGender("Gender");
//        doctor.setName("Name");
//        doctor.setType(type);
//
//        Type type1 = new Type();
//        type1.setType_id(1);
//        type1.setTypes("Types");
//
//        Patient patient = new Patient();
//        patient.setAddress("42 Main St");
//        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
//        patient.setAdmittedOn(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
//        patient.setAge(0);
//        patient.setBed(bed);
//        patient.setDoctor(doctor);
//        patient.setGender("Gender");
//        patient.setName("Name");
//        patient.setPatient_id(UUID.randomUUID());
//        patient.setType(type1);
//
//        ArrayList<Patient> patientList = new ArrayList<>();
//        patientList.add(patient);
//
//        Bed bed1 = new Bed();
//        bed1.setId(UUID.randomUUID());
//
//        Type type2 = new Type();
//        type2.setType_id(1);
//        type2.setTypes("Types");
//
//        Doctor doctor1 = new Doctor();
//        doctor1.setDoctor_id(UUID.randomUUID());
//        doctor1.setEmail("jane.doe@example.org");
//        doctor1.setGender("Gender");
//        doctor1.setName("Name");
//        doctor1.setType(type2);
//
//        Type type3 = new Type();
//        type3.setType_id(1);
//        type3.setTypes("Types");
//
//        Patient patient1 = new Patient();
//        patient1.setAddress("42 Main St");
//        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
//        patient1.setAdmittedOn(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
//        patient1.setAge(1);
//        patient1.setBed(bed1);
//        patient1.setDoctor(doctor1);
//        patient1.setGender("Gender");
//        patient1.setName("Name");
//        patient1.setPatient_id(UUID.randomUUID());
//        patient1.setType(type3);
//        when(this.patientRepo.save((Patient) any())).thenReturn(patient1);
//        when(this.patientRepo.findByBedId((UUID) any())).thenReturn(patientList);
//
//        Bed bed2 = new Bed();
//        bed2.setId(UUID.randomUUID());
//
//        Type type4 = new Type();
//        type4.setType_id(1);
//        type4.setTypes("Types");
//
//        Doctor doctor2 = new Doctor();
//        doctor2.setDoctor_id(UUID.randomUUID());
//        doctor2.setEmail("jane.doe@example.org");
//        doctor2.setGender("Gender");
//        doctor2.setName("Name");
//        doctor2.setType(type4);
//
//        Type type5 = new Type();
//        type5.setType_id(1);
//        type5.setTypes("Types");
//
//        Patient patient2 = new Patient();
//        patient2.setAddress("42 Main St");
//        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
//        patient2.setAdmittedOn(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
//        patient2.setAge(1);
//        patient2.setBed(bed2);
//        patient2.setDoctor(doctor2);
//        patient2.setGender("Gender");
//        patient2.setName("Name");
//        patient2.setPatient_id(UUID.randomUUID());
//        patient2.setType(type5);
//        assertThrows(BAlrdyOccExc.class, () -> this.hospitalServiceImpl.addPatient(patient2));
//        verify(this.patientRepo).findByBedId((UUID) any());
//    }
//
//    @Test
//    void testAddPatient4() {
//        Bed bed = new Bed();
//        bed.setId(UUID.randomUUID());
//
//        Type type = new Type();
//        type.setType_id(1);
//        type.setTypes("Types");
//
//        Doctor doctor = new Doctor();
//        doctor.setDoctor_id(UUID.randomUUID());
//        doctor.setEmail("jane.doe@example.org");
//        doctor.setGender("Gender");
//        doctor.setName("Name");
//        doctor.setType(type);
//
//        Type type1 = new Type();
//        type1.setType_id(1);
//        type1.setTypes("Types");
//
//        Patient patient = new Patient();
//        patient.setAddress("42 Main St");
//        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
//        patient.setAdmittedOn(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
//        patient.setAge(1);
//        patient.setBed(bed);
//        patient.setDoctor(doctor);
//        patient.setGender("Gender");
//        patient.setName("Name");
//        patient.setPatient_id(UUID.randomUUID());
//        patient.setType(type1);
//        when(this.patientRepo.save((Patient) any())).thenReturn(patient);
//        when(this.patientRepo.findByBedId((UUID) any())).thenReturn(new ArrayList<>());
//
//        Bed bed1 = new Bed();
//        bed1.setId(UUID.randomUUID());
//
//        Type type2 = new Type();
//        type2.setType_id(1);
//        type2.setTypes("Types");
//
//        Doctor doctor1 = new Doctor();
//        doctor1.setDoctor_id(UUID.randomUUID());
//        doctor1.setEmail("jane.doe@example.org");
//        doctor1.setGender("Gender");
//        doctor1.setName("Name");
//        doctor1.setType(type2);
//
//        Type type3 = new Type();
//        type3.setType_id(1);
//        type3.setTypes("Types");
//
//        Patient patient1 = new Patient();
//        patient1.setAddress("");
//        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
//        patient1.setAdmittedOn(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
//        patient1.setAge(1);
//        patient1.setBed(bed1);
//        patient1.setDoctor(doctor1);
//        patient1.setGender("Gender");
//        patient1.setName("Name");
//        patient1.setPatient_id(UUID.randomUUID());
//        patient1.setType(type3);
//        assertThrows(PAddressExc.class, () -> this.hospitalServiceImpl.addPatient(patient1));
//        verify(this.patientRepo).findByBedId((UUID) any());
//    }
//
//    @Test
//    void testAddPatient5() {
//        Bed bed = new Bed();
//        bed.setId(UUID.randomUUID());
//
//        Type type = new Type();
//        type.setType_id(1);
//        type.setTypes("Types");
//
//        Doctor doctor = new Doctor();
//        doctor.setDoctor_id(UUID.randomUUID());
//        doctor.setEmail("jane.doe@example.org");
//        doctor.setGender("Gender");
//        doctor.setName("Name");
//        doctor.setType(type);
//
//        Type type1 = new Type();
//        type1.setType_id(1);
//        type1.setTypes("Types");
//
//        Patient patient = new Patient();
//        patient.setAddress("42 Main St");
//        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
//        patient.setAdmittedOn(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
//        patient.setAge(1);
//        patient.setBed(bed);
//        patient.setDoctor(doctor);
//        patient.setGender("Gender");
//        patient.setName("Name");
//        patient.setPatient_id(UUID.randomUUID());
//        patient.setType(type1);
//        when(this.patientRepo.save((Patient) any())).thenReturn(patient);
//        when(this.patientRepo.findByBedId((UUID) any())).thenReturn(new ArrayList<>());
//
//        Bed bed1 = new Bed();
//        bed1.setId(UUID.randomUUID());
//
//        Type type2 = new Type();
//        type2.setType_id(1);
//        type2.setTypes("Types");
//
//        Doctor doctor1 = new Doctor();
//        doctor1.setDoctor_id(UUID.randomUUID());
//        doctor1.setEmail("jane.doe@example.org");
//        doctor1.setGender("Gender");
//        doctor1.setName("Name");
//        doctor1.setType(type2);
//
//        Type type3 = new Type();
//        type3.setType_id(1);
//        type3.setTypes("Types");
//
//        Patient patient1 = new Patient();
//        patient1.setAddress("42 Main St");
//        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
//        patient1.setAdmittedOn(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
//        patient1.setAge(0);
//        patient1.setBed(bed1);
//        patient1.setDoctor(doctor1);
//        patient1.setGender("Gender");
//        patient1.setName("Name");
//        patient1.setPatient_id(UUID.randomUUID());
//        patient1.setType(type3);
//        assertThrows(PAgeExc.class, () -> this.hospitalServiceImpl.addPatient(patient1));
//        verify(this.patientRepo).findByBedId((UUID) any());
//    }
//
//    @Test
//    void testAddPatient6() {
//        Bed bed = new Bed();
//        bed.setId(UUID.randomUUID());
//
//        Type type = new Type();
//        type.setType_id(1);
//        type.setTypes("Types");
//
//        Doctor doctor = new Doctor();
//        doctor.setDoctor_id(UUID.randomUUID());
//        doctor.setEmail("jane.doe@example.org");
//        doctor.setGender("Gender");
//        doctor.setName("Name");
//        doctor.setType(type);
//
//        Type type1 = new Type();
//        type1.setType_id(1);
//        type1.setTypes("Types");
//
//        Patient patient = new Patient();
//        patient.setAddress("42 Main St");
//        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
//        patient.setAdmittedOn(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
//        patient.setAge(1);
//        patient.setBed(bed);
//        patient.setDoctor(doctor);
//        patient.setGender("Gender");
//        patient.setName("Name");
//        patient.setPatient_id(UUID.randomUUID());
//        patient.setType(type1);
//        when(this.patientRepo.save((Patient) any())).thenReturn(patient);
//        when(this.patientRepo.findByBedId((UUID) any())).thenReturn(new ArrayList<>());
//
//        Bed bed1 = new Bed();
//        bed1.setId(UUID.randomUUID());
//
//        Type type2 = new Type();
//        type2.setType_id(123);
//        type2.setTypes("Types");
//
//        Doctor doctor1 = new Doctor();
//        doctor1.setDoctor_id(UUID.randomUUID());
//        doctor1.setEmail("jane.doe@example.org");
//        doctor1.setGender("Gender");
//        doctor1.setName("Name");
//        doctor1.setType(type2);
//
//        Type type3 = new Type();
//        type3.setType_id(1);
//        type3.setTypes("Types");
//
//        Patient patient1 = new Patient();
//        patient1.setAddress("42 Main St");
//        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
//        patient1.setAdmittedOn(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
//        patient1.setAge(1);
//        patient1.setBed(bed1);
//        patient1.setDoctor(doctor1);
//        patient1.setGender("Gender");
//        patient1.setName("Name");
//        patient1.setPatient_id(UUID.randomUUID());
//        patient1.setType(type3);
//        assertThrows(TypeMismatchExc.class, () -> this.hospitalServiceImpl.addPatient(patient1));
//        verify(this.patientRepo).findByBedId((UUID) any());
//    }
//
//    @Test
//    void testAddPatient7() {
//        Bed bed = new Bed();
//        bed.setId(UUID.randomUUID());
//
//        Type type = new Type();
//        type.setType_id(1);
//        type.setTypes("Types");
//
//        Doctor doctor = new Doctor();
//        doctor.setDoctor_id(UUID.randomUUID());
//        doctor.setEmail("jane.doe@example.org");
//        doctor.setGender("Gender");
//        doctor.setName("Name");
//        doctor.setType(type);
//
//        Type type1 = new Type();
//        type1.setType_id(1);
//        type1.setTypes("Types");
//
//        Patient patient = new Patient();
//        patient.setAddress("42 Main St");
//        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
//        patient.setAdmittedOn(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
//        patient.setAge(1);
//        patient.setBed(bed);
//        patient.setDoctor(doctor);
//        patient.setGender("Gender");
//        patient.setName("Name");
//        patient.setPatient_id(UUID.randomUUID());
//        patient.setType(type1);
//        when(this.patientRepo.save((Patient) any())).thenReturn(patient);
//        when(this.patientRepo.findByBedId((UUID) any())).thenReturn(new ArrayList<>());
//
//        Bed bed1 = new Bed();
//        bed1.setId(UUID.randomUUID());
//
//        Type type2 = new Type();
//        type2.setType_id(1);
//        type2.setTypes("Types");
//
//        Doctor doctor1 = new Doctor();
//        doctor1.setDoctor_id(UUID.randomUUID());
//        doctor1.setEmail("jane.doe@example.org");
//        doctor1.setGender("Gender");
//        doctor1.setName("Name");
//        doctor1.setType(type2);
//
//        Type type3 = new Type();
//        type3.setType_id(1);
//        type3.setTypes("Types");
//
//        Patient patient1 = new Patient();
//        patient1.setAddress("42 Main St");
//        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
//        patient1.setAdmittedOn(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
//        patient1.setAge(1);
//        patient1.setBed(bed1);
//        patient1.setDoctor(doctor1);
//        patient1.setGender("Gender");
//        patient1.setName("");
//        patient1.setPatient_id(UUID.randomUUID());
//        patient1.setType(type3);
//        assertThrows(PNameExc.class, () -> this.hospitalServiceImpl.addPatient(patient1));
//        verify(this.patientRepo).findByBedId((UUID) any());
//    }


//    @Test
//    void testGetAllDoctors2() {
//        when(this.doctorRepo.findAll()).thenThrow(new PNotAddedExc("foo"));
//        assertThrows(PNotAddedExc.class, () -> this.hospitalServiceImpl.getAllDoctors());
//        verify(this.doctorRepo).findAll();
//    }

//    @Test
//    void testAddDoctor2() {
//        when(this.doctorRepo.save((Doctor) any())).thenThrow(new PNotAddedExc("foo"));
//
//        Type type = new Type();
//        type.setType_id(1);
//        type.setTypes("Types");
//
//        Doctor doctor = new Doctor();
//        doctor.setDoctor_id(UUID.randomUUID());
//        doctor.setEmail("jane.doe@example.org");
//        doctor.setGender("Gender");
//        doctor.setName("Name");
//        doctor.setType(type);
//        assertThrows(PNotAddedExc.class, () -> this.hospitalServiceImpl.addDoctor(doctor));
//        verify(this.doctorRepo).save((Doctor) any());
//    }

//    @Test
//    void testAddDoctor3() {
//        Type type = new Type();
//        type.setType_id(1);
//        type.setTypes("Types");
//
//        Doctor doctor = new Doctor();
//        doctor.setDoctor_id(UUID.randomUUID());
//        doctor.setEmail("jane.doe@example.org");
//        doctor.setGender("Gender");
//        doctor.setName("Name");
//        doctor.setType(type);
//        when(this.doctorRepo.save((Doctor) any())).thenReturn(doctor);
//
//        Type type1 = new Type();
//        type1.setType_id(1);
//        type1.setTypes("Types");
//
//        Doctor doctor1 = new Doctor();
//        doctor1.setDoctor_id(UUID.randomUUID());
//        doctor1.setEmail("");
//        doctor1.setGender("Gender");
//        doctor1.setName("Name");
//        doctor1.setType(type1);
//        assertThrows(DEmailExc.class, () -> this.hospitalServiceImpl.addDoctor(doctor1));
//    }

//    @Test
//    void testAddDoctor4() {
//        Type type = new Type();
//        type.setType_id(1);
//        type.setTypes("Types");
//
//        Doctor doctor = new Doctor();
//        doctor.setDoctor_id(UUID.randomUUID());
//        doctor.setEmail("jane.doe@example.org");
//        doctor.setGender("Gender");
//        doctor.setName("Name");
//        doctor.setType(type);
//        when(this.doctorRepo.save((Doctor) any())).thenReturn(doctor);
//
//        Type type1 = new Type();
//        type1.setType_id(1);
//        type1.setTypes("Types");
//
//        Doctor doctor1 = new Doctor();
//        doctor1.setDoctor_id(UUID.randomUUID());
//        doctor1.setEmail("jane.doe@example.org");
//        doctor1.setGender("Gender");
//        doctor1.setName("");
//        doctor1.setType(type1);
//        assertThrows(DNameExc.class, () -> this.hospitalServiceImpl.addDoctor(doctor1));
//    }

//    @Test
//    void testAddBed2() {
//        when(this.bedRepo.save((Bed) any())).thenThrow(new PNotAddedExc("foo"));
//
//        Bed bed = new Bed();
//        bed.setId(UUID.randomUUID());
//        assertThrows(PNotAddedExc.class, () -> this.hospitalServiceImpl.addBed(bed));
//        verify(this.bedRepo).save((Bed) any());
//    }

//    @Test
//    void testDeleteBedById2() {
//        doThrow(new PNotAddedExc("foo")).when(this.bedRepo).deleteById((UUID) any());
//        when(this.bedRepo.existsById((UUID) any())).thenReturn(true);
//        assertThrows(PNotAddedExc.class, () -> this.hospitalServiceImpl.deleteBedById(UUID.randomUUID()));
//        verify(this.bedRepo).deleteById((UUID) any());
//        verify(this.bedRepo).existsById((UUID) any());
//    }
//
//    @Test
//    void testDeleteBedById3() {
//        doNothing().when(this.bedRepo).deleteById((UUID) any());
//        when(this.bedRepo.existsById((UUID) any())).thenReturn(false);
//        assertThrows(BIdNotFoundExc.class, () -> this.hospitalServiceImpl.deleteBedById(UUID.randomUUID()));
//        verify(this.bedRepo).existsById((UUID) any());
//    }

//    @Test
//    void testDeletePatientById2() {
//        doThrow(new PNotAddedExc("foo")).when(this.patientRepo).deleteById((UUID) any());
//        when(this.patientRepo.existsById((UUID) any())).thenReturn(true);
//        assertThrows(PNotAddedExc.class, () -> this.hospitalServiceImpl.deletePatientById(UUID.randomUUID()));
//        verify(this.patientRepo).deleteById((UUID) any());
//        verify(this.patientRepo).existsById((UUID) any());
//    }
//
//    @Test
//    void testDeletePatientById3() {
//        doNothing().when(this.patientRepo).deleteById((UUID) any());
//        when(this.patientRepo.existsById((UUID) any())).thenReturn(false);
//        assertThrows(PIdNotFoundExc.class, () -> this.hospitalServiceImpl.deletePatientById(UUID.randomUUID()));
//        verify(this.patientRepo).existsById((UUID) any());
//    }


}

