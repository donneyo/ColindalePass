package com.colindalepass.repository;

import com.colindalepass.entity.GeneratedCodeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CodeDetailsRepository extends JpaRepository<GeneratedCodeDetails, Integer> {

    @Query("SELECT g FROM GeneratedCodeDetails g WHERE g.verificationCode = ?1")
    public GeneratedCodeDetails findByCode(String verificationCode);

    @Modifying
    @Query("DELETE FROM GeneratedCodeDetails c WHERE c.expiryTime < :currentTime")
    public void deleteExpiredCodes(@Param("currentTime") LocalDateTime currentTime);

}
