package com.javalabs.userserviceelkstack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<com.javalabs.userserviceelkstack.model.User, Long> {
}
