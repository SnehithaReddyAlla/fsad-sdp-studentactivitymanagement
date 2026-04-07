package com.klef.fsad.sdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.fsad.sdp.entity.Activity;
import com.klef.fsad.sdp.entity.Mentor;

@Repository
public interface ActivityRepository 
       extends JpaRepository<Activity,Integer> 
{
   // Get activities created by mentor
   List<Activity> findByMentor(Mentor mentor);
}