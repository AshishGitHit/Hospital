package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "patient")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Patient {
    @Id
    @GeneratedValue(generator = "uuid")
    @org.hibernate.annotations.Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID patient_id;
    private String name;
    private String address;
    private int age;
    private String gender;
    private Date admittedOn;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "b_id", unique = true)
    private Bed bed;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "p_type")
    private Type type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "d_id")
    private Doctor doctor;



}