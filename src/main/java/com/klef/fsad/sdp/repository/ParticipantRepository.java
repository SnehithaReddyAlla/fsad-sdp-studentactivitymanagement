package com.klef.fsad.sdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.klef.fsad.sdp.entity.Participant;

import jakarta.transaction.Transactional;

@Repository
public interface ParticipantRepository 
       extends JpaRepository<Participant,Integer>
{
    Participant findByEmailAndPassword(String email,String password);
    
    @Query("SELECT p FROM Participant p WHERE p.email=?1 AND p.password=?2")
    Participant checkParticipantLogin(String email,String password);

    Participant findByEmail(String email);

    @Query("SELECT p FROM Participant p WHERE p.email=?1")
    Participant getParticipantByEmail(String email);

    @Modifying
    @Transactional
    @Query("DELETE FROM Participant p WHERE p.email=?1")
    int deleteParticipantByEmail(String email);

    Participant findByUsername(String username);

    @Query("SELECT p FROM Participant p WHERE p.username=?1")
    Participant getParticipantByUsername(String username);

    @Modifying
    @Transactional
    @Query("DELETE FROM Participant p WHERE p.username=?1")
    int deleteParticipantByUsername(String username);

    List<Participant> findByDepartment(String department);

    @Query("SELECT p FROM Participant p WHERE p.department=?1")
    List<Participant> getParticipantsByDepartment(String department);

    List<Participant> findByGender(String gender);

    @Query("SELECT p FROM Participant p WHERE p.gender=?1")
    List<Participant> getParticipantsByGender(String gender);

    List<Participant> findByNameContaining(String keyword);

    @Query("SELECT p FROM Participant p WHERE p.name LIKE %?1%")
    List<Participant> searchParticipantByName(String keyword);

    long count();

    @Query("SELECT COUNT(p) FROM Participant p")
    long totalParticipants();

    long countByDepartment(String department);

    @Query("SELECT COUNT(p) FROM Participant p WHERE p.department=?1")
    long totalParticipantsByDepartment(String department);

    long countByGender(String gender);

    @Query("SELECT COUNT(p) FROM Participant p WHERE p.gender=?1")
    long totalParticipantsByGender(String gender);

    boolean existsByEmail(String email);

    @Query("SELECT COUNT(p)>0 FROM Participant p WHERE p.email=?1")
    boolean isEmailAvailable(String email);

    @Modifying
    @Transactional
    @Query("DELETE FROM Participant p WHERE p.department=?1")
    int deleteParticipantsByDepartment(String department);

    @Modifying
    @Transactional
    @Query("UPDATE Participant p SET p.password=?2 WHERE p.email=?1")
    int updatePasswordByEmail(String email,String password);

    @Modifying
    @Transactional
    @Query("UPDATE Participant p SET p.department=?2 WHERE p.username=?1")
    int updateDepartmentByUsername(String username,String department);

    long countByName(String name);
}