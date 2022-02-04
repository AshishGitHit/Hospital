//package com.example.demo.service;
//
//import com.example.demo.entity.BillGenerate;
//import com.example.demo.entity.Patient;
//import com.example.demo.repository.BillRepo;
//import com.example.demo.repository.PatientRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class BillServiceImpl implements BillService{
//
//    @Autowired
//    private BillRepo billRepo;
//    @Autowired
//    private PatientRepo patientRepo;
//
//
//    public String getBill(BillGenerate bills) {
//        Integer amount = 0;
//        Patient pat = patientRepo.getById();
//        for (Patient p:pat) {
//            for (BillGenerate bill: bills) {
//                if (bill.getDismissedOn().after(pat.getAdmittedOn())) {
//                    amount = amount+(bill.getDismissedOn().compareTo(pat.getAdmittedOn()));
//                }
//
//            }
//
//        }
//        return "Bill = " + amount;
//
//    }
//}
