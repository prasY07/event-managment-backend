package com.example.event_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.event_management.entity.SocialMediaSource;

@Repository
public interface ISocialMediaSourceRepo extends JpaRepository<SocialMediaSource, Long> {

    List<SocialMediaSource> findAll();
}