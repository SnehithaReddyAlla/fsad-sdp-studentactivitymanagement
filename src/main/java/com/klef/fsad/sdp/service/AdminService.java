package com.klef.fsad.sdp.service;

import java.util.List;

import com.klef.fsad.sdp.entity.Admin;
import com.klef.fsad.sdp.entity.Participant;
import com.klef.fsad.sdp.entity.Mentor;

public interface AdminService 
{
  public Admin verifyAdminLogin(String username,String password);
  
  public String addMentor(Mentor mentor);
  public List<Mentor> viewAllMentors();
  public boolean deleteMentor(int id);
  
  public List<Participant> viewAllParticipants();
  public String deleteParticipant(int id);
  
}