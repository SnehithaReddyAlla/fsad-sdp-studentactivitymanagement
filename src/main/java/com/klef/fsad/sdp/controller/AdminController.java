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

import com.klef.fsad.sdp.dto.ParticipantDTO;
//import com.klef.fsad.sdp.entity.Admin;
import com.klef.fsad.sdp.entity.Participant;
import com.klef.fsad.sdp.entity.Mentor;
import com.klef.fsad.sdp.service.AdminService;

@RestController
@RequestMapping("adminapi")
@CrossOrigin("*")
public class AdminController 
{
	@Autowired
	private AdminService adminService;
	
	@GetMapping("/")
	public String index()
	{
		return "Full Stack SDP Project";
	}
	
	/*@PostMapping("/login")
	public ResponseEntity<?> checkadminlogin(@RequestBody Admin admin)
	{
		try
		{
			Admin a = adminService.verifyAdminLogin(admin.getUsername(), admin.getPassword());
		
		    if(a!=null)
		    {
		    	return ResponseEntity.status(200).body(admin);
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
	}*/
	
	@PostMapping("/addmentor")
	public ResponseEntity<String> addmentor(@RequestBody Mentor mentor)
	{
		   try
		   {
			   String output = adminService.addMentor(mentor);
			   return ResponseEntity.status(201).body(output);
		   }
		   catch(Exception e)
		   {
			   return ResponseEntity.status(500).body("Internal Server Error");
		   }
	}
	
	@GetMapping("/viewallmentors")
	public ResponseEntity<?> viewallmentors()
	{
	    try
	    {
	        List<Mentor> mentors = adminService.viewAllMentors();
	        return ResponseEntity.ok(mentors);
	    }
	    catch(Exception e)
	    {
	        return ResponseEntity.status(500).body("Error Fetching Mentors");
	    }
	}
	
	
	@DeleteMapping("/deletementor/{id}")
	public ResponseEntity<String> deleteMentor(@PathVariable int id)
	{
	    try
	    {
	        boolean deleted = adminService.deleteMentor(id);

	        if(deleted)
	        {
	            return ResponseEntity.ok("Mentor Deleted Successfully");
	        }
	        else
	        {
	            return ResponseEntity.status(404).body("Mentor Not Found");
	        }
	    }
	    catch(Exception e)
	    {
	        return ResponseEntity.status(500).body("Internal Server Error");
	    }
	}
	
	@GetMapping("/viewallparticipants")
	public ResponseEntity<?> viewAllParticipants()
	{
	    try
	    {
	        List<Participant> participants = adminService.viewAllParticipants();
	        //return ResponseEntity.ok(participants);
	        if(participants.size()>0)
		        return ResponseEntity.ok(participants);
		        else
		        return ResponseEntity.noContent().build();
	    }
	    catch(Exception e)
	    {
	        return ResponseEntity.status(500).body("Error Fetching Participants");
	    }
	}
	@DeleteMapping("/deleteparticipant/{id}")
	public String deleteParticipant(@PathVariable int id)
	{
	    return adminService.deleteParticipant(id);
	}
	
	@GetMapping("/displayallparticipantsdto")
	  public ResponseEntity<?> displayallparticipantsDTO()
	  {
	      try
	      {
	          List<ParticipantDTO> participants = adminService.displayallparticipantsDTO();
	          return ResponseEntity.ok(participants);
	      }
	      catch(Exception e)
	      {
	          return ResponseEntity.status(500).body("Error Fetching Participants");
	      }
	  }
}