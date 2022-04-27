package com.example.abeec.repository;

import com.example.abeec.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission,String> {

    Optional<Mission> findById(String id);
}
