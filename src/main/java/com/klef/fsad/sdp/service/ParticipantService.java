package com.klef.fsad.sdp.service;

import java.util.List;

import com.klef.fsad.sdp.entity.Enrollment;
import com.klef.fsad.sdp.entity.Participant;

public interface ParticipantService 
{
  public String participantRegistration(Participant participant);

  public Participant verifyParticipantLogin(String email,String pwd);

  public String updateParticipantProfile(Participant participant);

  public String enrollActivity(Enrollment enrollment);

  public List<Enrollment> viewEnrollmentsByParticipant(int participantid);
  public String deleteEnrollment(int enrollmentid);

}