package com.klef.fsad.sdp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.Enrollment;
import com.klef.fsad.sdp.entity.Participant;
import com.klef.fsad.sdp.repository.EnrollmentRepository;
import com.klef.fsad.sdp.repository.ParticipantRepository;

@Service
public class ParticipantServiceImpl implements ParticipantService
{
	@Autowired
    private ParticipantRepository participantRepository;
	 
	@Autowired
	private EnrollmentRepository enrollmentRepository;
	
	@Override
	public Participant verifyParticipantLogin(String email, String pwd) 
	{
		return participantRepository.findByEmailAndPassword(email, pwd);
	}

	@Override
	public String updateParticipantProfile(Participant participant) 
	{
		Optional<Participant> optional = participantRepository.findById(participant.getId());
		
		if(optional.isPresent())
		{
			Participant p = optional.get();
			
			p.setContact(participant.getContact());
			p.setDepartment(participant.getDepartment());
			p.setName(participant.getName());
			p.setPassword(participant.getPassword());
			
			participantRepository.save(p);
			
			return "Participant Profile Updated Successfully";
		}
		else
		{
			return "Participant ID Not Found to Update";
		}
	}

	@Override
	public String participantRegistration(Participant participant) 
	{
		participantRepository.save(participant);
		return "Participant Registered Successfully";
	}

	@Override
	public String enrollActivity(Enrollment enrollment) 
	{
		enrollmentRepository.save(enrollment);
		return "Activity Enrollment Successful";
	}

	@Override
	public List<Enrollment> viewEnrollmentsByParticipant(int participantid) 
	{
	    return enrollmentRepository.findByParticipantId(participantid);
	}
	
	@Override
	public String deleteEnrollment(int eid) 
	{
	    enrollmentRepository.deleteById(eid);
	    return "Enrollment Deleted Successfully";
	}

}