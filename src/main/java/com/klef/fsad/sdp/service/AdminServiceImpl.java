package com.klef.fsad.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.Admin;
import com.klef.fsad.sdp.entity.Participant;
import com.klef.fsad.sdp.entity.Mentor;
import com.klef.fsad.sdp.repository.AdminRepository;
import com.klef.fsad.sdp.repository.ParticipantRepository;
import com.klef.fsad.sdp.repository.MentorRepository;

@Service
public class AdminServiceImpl implements AdminService
{
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private MentorRepository mentorRepository;
	
	@Autowired
	private ParticipantRepository participantRepository;
	

	@Override
	public Admin verifyAdminLogin(String username, String password) 
	{
		return adminRepository.findByUsernameAndPassword(username, password);
	}

	@Override
	public String addMentor(Mentor mentor) 
	{
		mentorRepository.save(mentor);
		return "Mentor Added Successfully";
	}

	@Override
	public List<Mentor> viewAllMentors() 
	{
		return mentorRepository.findAll();
	}

	@Override
	public List<Participant> viewAllParticipants() 
	{
		return participantRepository.findAll();
	}

	@Override
	public boolean deleteMentor(int id) 
	{
		if(mentorRepository.existsById(id))
		{
			mentorRepository.deleteById(id);
			return true;
		}
		return false;
	}
	
	@Override
	public String deleteParticipant(int id)
	{
		participantRepository.deleteById(id);
		return "Participant Deleted Successfully";
	}

}