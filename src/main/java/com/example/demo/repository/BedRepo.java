package com.example.demo.repository;

import com.example.demo.entity.Bed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BedRepo extends JpaRepository<Bed, UUID> {

}
