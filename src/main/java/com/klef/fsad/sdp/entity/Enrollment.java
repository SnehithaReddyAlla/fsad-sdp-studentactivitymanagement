package com.klef.fsad.sdp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="enrollment_table")
public class Enrollment 
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)   // ✅ AUTO ID
   private int id;

   @ManyToOne
   @JoinColumn(name = "participant_id")
   private Participant participant;

   @ManyToOne
   @JoinColumn(name = "activity_id")
   private Activity activity;

   @Column(length = 100,nullable = false)
   private String enrollmentDate;

   @Column(length = 100,nullable = false)
   private String status;

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public Participant getParticipant() {
      return participant;
   }

   public void setParticipant(Participant participant) {
      this.participant = participant;
   }

   public Activity getActivity() {
      return activity;
   }

   public void setActivity(Activity activity) {
      this.activity = activity;
   }

   public String getEnrollmentDate() {
      return enrollmentDate;
   }

   public void setEnrollmentDate(String enrollmentDate) {
      this.enrollmentDate = enrollmentDate;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   @Override
   public String toString() {
      return "Enrollment [id=" + id +
             ", participant=" + participant +
             ", activity=" + activity +
             ", enrollmentDate=" + enrollmentDate +
             ", status=" + status + "]";
   }
}