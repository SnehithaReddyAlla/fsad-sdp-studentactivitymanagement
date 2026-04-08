package com.klef.fsad.sdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.fsad.sdp.entity.Mentor;

@Repository
public interface MentorRepository extends JpaRepository<Mentor,Integer>
{
   Mentor findByEmailAndPassword(String email, String password);

   Mentor findByEmail(String email);
}