package com.klef.fsad.sdp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Activity 
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   @Column(length=100,nullable=false)
   private String category;

   @Column(length=100,nullable=false)
   private String activityname;

   @Column(length=500,nullable=false)
   private String description;

   @Column(nullable=false)
   private double duration;

   // ✅ ADD THIS FIELD
   @Column(length=200, nullable=false)
   private String venue;
  

// ✅ ADD DATE FIELD
@Column(nullable=false)
private String date;

   @ManyToOne
   @JoinColumn(name = "mentor_id")
   private Mentor mentor;

   public int getId() {
	return id;
   }

   public void setId(int id) {
	this.id = id;
   }

   public String getCategory() {
	return category;
   }

   public void setCategory(String category) {
	this.category = category;
   }

   public String getActivityname() {
	return activityname;
   }

   public void setActivityname(String activityname) {
	this.activityname = activityname;
   }

   public String getDescription() {
	return description;
   }

   public void setDescription(String description) {
	this.description = description;
   }

   public double getDuration() {
	return duration;
   }

   public void setDuration(double duration) {
	this.duration = duration;
   }

   public String getVenue() {
	return venue;
   }

   public void setVenue(String venue) {
	this.venue = venue;
   }

   public String getDate() {
	return date;
   }

   public void setDate(String date) {
	this.date = date;
   }

   public Mentor getMentor() {
	return mentor;
   }

   public void setMentor(Mentor mentor) {
	this.mentor = mentor;
   }

   @Override
   public String toString() {
	return "Activity [id=" + id + ", category=" + category + ", activityname=" + activityname + ", description="
			+ description + ", duration=" + duration + ", venue=" + venue + ", date=" + date + ", mentor=" + mentor
			+ "]";
   }

   
}