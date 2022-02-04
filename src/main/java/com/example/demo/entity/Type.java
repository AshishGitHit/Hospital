package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "type")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Type {
    @Id
    private int type_id;
    @Column(name = "type", unique = true)
    private String types;
}
