package com.springbootdemo.springbootdemo.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbootdemo.springbootdemo.entities.Profile;

public interface ProfileRepo extends JpaRepository<Profile,String>{
    Optional<Profile> findBy_id(String _id);
}
