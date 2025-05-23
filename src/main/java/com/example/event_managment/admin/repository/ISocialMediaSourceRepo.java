package com.example.event_managment.admin.repository;

import com.example.event_managment.common.entity.SocialMediaSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISocialMediaSourceRepo extends JpaRepository<SocialMediaSource, Long> {

    List<SocialMediaSource> findAll();
}
