package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klef.fsad.sdp.entity.Activity;
import com.klef.fsad.sdp.entity.Mentor;
import com.klef.fsad.sdp.service.MentorService;

@RestController
@RequestMapping("mentorapi")
@CrossOrigin("*")
public class MentorController 
{
  @Autowired
  private MentorService mentorService;
  
  @GetMapping("/")
  public String mentorhome()
  {
	   return "Mentor Controller Demo";
  }
  
  @PostMapping("login")
  public ResponseEntity<?> verifymentorlogin(@RequestBody Mentor mentor)
  {
	   try
		{
			Mentor m = mentorService.verifyMentorLogin(mentor.getEmail(), mentor.getPassword());
		
		    if(m!=null)
		    {
		    	return ResponseEntity.status(200).body(m);
		    }
		    else
		    {
		    	return ResponseEntity.status(401).body("Login Invalid");
		    }
		}
		catch (Exception e) 
		{
			return ResponseEntity.status(500).body("Internal Server Error");
		}
  }
  
  
@PostMapping("/addactivity")
public ResponseEntity<String> addActivity(@RequestBody Activity activity)
{
   try
   {
       String output = mentorService.addActivity(activity);
       return ResponseEntity.status(201).body(output);
   }
   catch(Exception e)
   {
       return ResponseEntity.status(500).body("Error Adding Activity");
   }
}
  
@GetMapping("/viewmyactivities/{mentorid}")
public ResponseEntity<?> viewMyActivities(@PathVariable int mentorid)
{
    try
    {
        List<Activity> myactivities = mentorService.viewActivitiesByMentor(mentorid);

        if(myactivities == null || myactivities.isEmpty())
        {
            return ResponseEntity.status(204).body("No Activities Found");
        }

        return ResponseEntity.ok(myactivities);
    }
    catch(Exception e)
    {
        return ResponseEntity.status(500).body("Error Fetching Activities");
    }
}


@DeleteMapping("/deleteactivity/{id}")
public ResponseEntity<String> deleteActivity(@PathVariable int id)
{
 try
 {
     String output = mentorService.deleteActivity(id);
     return ResponseEntity.ok(output);
 }
 catch(Exception e)
 {
     return ResponseEntity.status(500).body("Error Deleting Activity");
 }
}
@GetMapping("/viewallactivities")
public ResponseEntity<?> viewAllActivities()
{
    try
    {
        List<Activity> activities = mentorService.viewAllActivities();

        if(activities == null || activities.isEmpty())
        {
            return ResponseEntity.status(204).body("No Activities Found");
        }

        return ResponseEntity.ok(activities);
    }
    catch(Exception e)
    {
        return ResponseEntity.status(500).body("Error Fetching Activities");
    }
}

@GetMapping("/viewenrollments/{mentorid}")
public ResponseEntity<?> viewEnrollmentsByMentor(@PathVariable int mentorid)
{
    try
    {
        List<?> enrollments = mentorService.viewEnrollmentsByMentor(mentorid);

        if(enrollments == null || enrollments.isEmpty())
        {
            return ResponseEntity.status(204).body("No Enrollments Found");
        }

        return ResponseEntity.ok(enrollments);
    }
    catch(Exception e)
    {
        return ResponseEntity.status(500).body("Error Fetching Enrollments");
    }
}
@PostMapping("/updateprofile")
public ResponseEntity<String> updateProfile(@RequestBody Mentor mentor)
{
    try
    {
        String output = mentorService.updateMentorProfile(mentor);

        return ResponseEntity.status(201).body(output);
    }
    catch(Exception e)
    {
        return ResponseEntity.status(500).body("Profile Updated Failed");
    }
}
}