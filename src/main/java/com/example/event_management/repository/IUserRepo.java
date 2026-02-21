package com.example.event_management.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.event_management.common.AppStatus;
import com.example.event_management.entity.User;

@Repository
public interface IUserRepo extends JpaRepository<User, Long> {

    List<User> findAll();

    List<User> findByStatus(AppStatus.UserStatus status);

    Page<User> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM users WHERE id = :id", nativeQuery = true)
    User findUser(@Param("id") Long id);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);

    @Query(value="Select * from users where email = :email and role = :role", nativeQuery=true)
    User getUserByEmailAndRole(@Param("email") String email , @Param("role") String role);

    @Query("SELECT COUNT(u) FROM User u")
    Long countTotalUsers();
    

    // boolean existsByUserRegId(String registrationId);

}
