package com.example.hotel.repository;

import com.example.hotel.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
    // Custom query methods can be added here if needed
}