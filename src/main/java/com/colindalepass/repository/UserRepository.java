package com.colindalepass.repository;

import com.colindalepass.entity.AuthenticationType;
import com.colindalepass.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.firstName = ?1")
    public User findByUsername(String firstname);


    @Query("SELECT u FROM User u WHERE u.email = :email")
    public User getUserByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.id = ?1")
    public Optional<User> findById(Integer id);

    @Query("SELECT new com.colindalepass.entity.User(u.firstName, u.lastName, u.email) FROM User u WHERE u.id = ?1")
    public Optional<User> findUserDetailsById(Integer id);


    public Long countById(Integer id);

    @Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email, ' ', u.firstName, ' ',"
            + " u.lastName) LIKE %?1%")
    public Page<User> findAll(String keyword, Pageable pageable);

    @Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
    @Modifying
    public void updateEnabledStatus(Integer id, boolean enabled);

    @Query("UPDATE User u SET u.authenticationType = ?2 WHERE u.id = ?1")
    @Modifying
    public void updateAuthenticationType(Integer customerId, AuthenticationType type);

    public User findByResetPasswordToken(String token);

}
