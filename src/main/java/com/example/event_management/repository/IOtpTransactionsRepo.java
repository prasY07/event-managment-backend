package com.example.event_management.repository;

import com.example.event_management.entity.OtpTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IOtpTransactionsRepo extends JpaRepository<OtpTransactions,Long> {
    Optional<OtpTransactions> findByUserOtpId(String userOtpId);

}
