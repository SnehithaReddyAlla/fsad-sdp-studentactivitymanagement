package com.klef.fsad.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.Activity;
import com.klef.fsad.sdp.entity.Mentor;
import com.klef.fsad.sdp.repository.ActivityRepository;
import com.klef.fsad.sdp.repository.MentorRepository;
import com.klef.fsad.sdp.entity.Enrollment;
import com.klef.fsad.sdp.repository.EnrollmentRepository;

@Service
public class MentorServiceImpl implements MentorService
{
	@Autowired
	private MentorRepository mentorRepository;
	
	@Autowired
	private ActivityRepository activityRepository;
	
	@Autowired
	private EnrollmentRepository enrollmentRepository;
	
	
	@Override
	public Mentor verifyMentorLogin(String email, String pwd) 
	{
		return mentorRepository.findByEmailAndPassword(email, pwd);
	}

	@Override
	public String addActivity(Activity activity) 
	{
		activityRepository.save(activity);
		return "Activity Added Successfully";
	}

	@Override
	public String deleteActivity(int activityid) 
	{
	    enrollmentRepository.deleteByActivityId(activityid);
	    activityRepository.deleteById(activityid);

	    return "Activity Deleted Successfully";
	}

	@Override
	public List<Activity> viewActivitiesByMentor(int mentorid) 
	{
		Mentor mentor = mentorRepository.findById(mentorid).orElse(null);

	    if(mentor!= null)
	    {
	        return activityRepository.findByMentor(mentor);
	    }

	    return null;
	}
	@Override
	public List<Activity> viewAllActivities()
	{
	    return activityRepository.findAll();
	}
	
	@Override
	public List<Enrollment> viewEnrollmentsByMentor(int mentorid)
	{
	    return enrollmentRepository.findEnrollmentsByMentorId(mentorid);
	}
	
	@Override
	public String updateMentorProfile(Mentor mentor)
	{
	    Mentor m = mentorRepository.findById(mentor.getId()).orElse(null);

	    if(m != null)
	    {
	        m.setMentorname(mentor.getMentorname());

	        m.setPassword(mentor.getPassword());

	        m.setContact(mentor.getContact());

	        mentorRepository.save(m);

	        return "Mentor Profile Updated Successfully";
	    }

	    return "Mentor Not Found";
	}

}