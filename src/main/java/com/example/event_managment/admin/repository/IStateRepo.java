package com.example.event_managment.admin.repository;

import com.example.event_managment.common.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStateRepo extends JpaRepository<State, Long> {

    List<State> findAll();
}
