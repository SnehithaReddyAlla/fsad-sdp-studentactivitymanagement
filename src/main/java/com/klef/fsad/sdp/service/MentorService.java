package com.klef.fsad.sdp.service;

import java.util.List;

import com.klef.fsad.sdp.entity.Activity;
import com.klef.fsad.sdp.entity.Mentor;
import com.klef.fsad.sdp.entity.Enrollment;

public interface MentorService 
{
	public Mentor verifyMentorLogin(String email,String pwd);
    
	public String addActivity(Activity activity);
	public List<Activity> viewActivitiesByMentor(int mentorid);
	public String deleteActivity(int activityid);
	public List<Activity> viewAllActivities();
	public List<Enrollment> viewEnrollmentsByMentor(int mentorid);
	public String updateMentorProfile(Mentor mentor);

}