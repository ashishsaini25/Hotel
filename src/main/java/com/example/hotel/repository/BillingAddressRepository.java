package com.example.hotel.repository;

import com.example.hotel.entity.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingAddressRepository extends JpaRepository<BillingAddress, Long> {
    // Custom query methods can be added here if needed
}