package com.example.demo.repository;

import com.example.demo.domain.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NurseRepository extends JpaRepository<Nurse,String> {

    boolean existsByNurseId(String nurseId);
}
