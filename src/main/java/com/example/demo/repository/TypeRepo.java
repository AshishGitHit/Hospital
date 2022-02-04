package com.example.demo.repository;

import com.example.demo.entity.Patient;
import com.example.demo.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepo extends JpaRepository<Type, Integer> {
//    Patient findByT_Id(Integer id);
}
