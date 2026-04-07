package com.klef.fsad.sdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.stereotype.Repository;

import com.klef.fsad.sdp.entity.Enrollment;

@Repository
public interface EnrollmentRepository 
       extends JpaRepository<Enrollment,Integer>
{

   @Query("SELECT e FROM Enrollment e WHERE e.activity.mentor.id = :mentorid")
   List<Enrollment> findEnrollmentsByMentorId(int mentorid);

   @Query("SELECT e FROM Enrollment e WHERE e.participant.id = :pid")
   List<Enrollment> findByParticipantId(int pid);

   /* ✅ ADD THIS */

   @Modifying
   @Transactional
   @Query("DELETE FROM Enrollment e WHERE e.activity.id = :activityid")
   void deleteByActivityId(int activityid);

}