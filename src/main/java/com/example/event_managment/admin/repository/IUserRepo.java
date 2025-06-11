package com.example.event_managment.admin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.event_managment.entity.User;

@Repository
public interface IUserRepo extends JpaRepository<User, Long> {

    List<User> findAll();

    Page<User> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM users WHERE id = :id", nativeQuery = true)
    User findUser(@Param("id") Long id);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean findByEmail(String email);

    boolean findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);

    boolean existsByUserRegId(String registrationId);

}
