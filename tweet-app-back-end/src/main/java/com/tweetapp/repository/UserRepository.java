package com.tweetapp.repository;

import com.tweetapp.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUserId(String username);

    @Query(value = "SELECT u FROM UserEntity u WHERE u.userId LIKE %?1%")
    List<UserEntity> searchByUsername(String username);
}
