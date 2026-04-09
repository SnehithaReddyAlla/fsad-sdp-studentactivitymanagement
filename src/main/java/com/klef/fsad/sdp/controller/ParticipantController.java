package com.klef.fsad.sdp.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.klef.fsad.sdp.entity.Participant;
import com.klef.fsad.sdp.entity.Enrollment;   // ✅ ADDED
import com.klef.fsad.sdp.service.ParticipantService;

@RestController
@RequestMapping("participantapi")
@CrossOrigin("*")
public class ParticipantController 
{
   @Autowired
   private ParticipantService participantService;
  
   @GetMapping("/")
   public String participanthome()
   {
       return "Participant Controller Demo";
   }
   
   @PostMapping("/registration")
   public ResponseEntity<String> participantregistration(@RequestBody Participant p)
   {
       try
       {
           String output = participantService.participantRegistration(p);

           return ResponseEntity.status(201).body(output);
       }
       catch(Exception e)
       {
           return ResponseEntity.status(500).body("Internal Server Error");
       }
   }
   
   /*@PostMapping("login")
   public ResponseEntity<?> verifyparticipantlogin(@RequestBody Participant participant)
   {
       try
       {
           Participant p = participantService.verifyParticipantLogin(participant.getEmail(),participant.getPassword());
       
           if(p!=null)
           {
               return ResponseEntity.status(200).body(p);
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
   
   @PostMapping("/updateprofile")
   public ResponseEntity<String> participantupdateprofile(@RequestBody Participant p)
   {
       try
       {
           String output = participantService.updateParticipantProfile(p);

           return ResponseEntity.status(201).body(output);
       }
       catch(Exception e)
       {
           return ResponseEntity.status(500).body("Internal Server Error");
       }
   }

   @PostMapping("/enroll")
   public ResponseEntity<String> enrollActivity(
           @RequestBody Enrollment enrollment)
   {
       try
       {
           String output = participantService.enrollActivity(enrollment);

           return ResponseEntity.status(201).body(output);
       }
       catch(Exception e)
       {
           return ResponseEntity.status(500).body("Enrollment Failed");
       }
   }

   @GetMapping("/viewenrollments/{pid}")
   public ResponseEntity<?> viewEnrollments(@PathVariable int pid)
   {
      try
      {
          return ResponseEntity.ok(participantService.viewEnrollmentsByParticipant(pid));
      }
      catch(Exception e)
      {
          return ResponseEntity.status(500).body("Failed to Fetch Enrollments");
      }
   }
   
   @DeleteMapping("/deleteenrollment/{eid}")
   public ResponseEntity<String> deleteEnrollment(@PathVariable int eid)
   {
      try
      {
          String output = participantService.deleteEnrollment(eid);

          return ResponseEntity.ok(output);
      }
      catch(Exception e)
      {
          return ResponseEntity.status(500).body("Deletion Failed");
      }
   }

}