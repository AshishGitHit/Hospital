//package com.example.demo.entity;
//
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Entity
//@Table(name = "dismissedOn")
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@ToString
//public class BillGenerate {
//    @Id
//    private Date dismissedOn;
//    private int amount;
//
//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "d_day")
//    private Patient patient;
//}
