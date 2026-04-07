package com.klef.fsad.sdp.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="participant_table")
public class Participant 
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   @Column(nullable = false,length = 50)
   private String name;

   @Column(nullable = false,length = 50)
   private String gender;

   @Column(nullable = false,length = 50,unique = true)
   private String email;

   @Column(nullable = false,length = 50,unique = true)
   private String username;

   @Column(nullable = false,length = 50)
   private String password;

   @Column(nullable = false,length = 20,unique = true)
   private String contact;

   @Column(nullable = false,length = 100)
   private String department;

   @CreationTimestamp
   @Column(updatable = false)
   private LocalDateTime registeredAt;

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getGender() {
      return gender;
   }

   public void setGender(String gender) {
      this.gender = gender;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getContact() {
      return contact;
   }

   public void setContact(String contact) {
      this.contact = contact;
   }

   public String getDepartment() {
      return department;
   }

   public void setDepartment(String department) {
      this.department = department;
   }

   public LocalDateTime getRegisteredAt() {
      return registeredAt;
   }

   public void setRegisteredAt(LocalDateTime registeredAt) {
      this.registeredAt = registeredAt;
   }

   @Override
   public String toString() {
      return "Participant [id=" + id + ", name=" + name + ", gender=" + gender + ", email=" + email
            + ", username=" + username + ", password=" + password + ", contact=" + contact
            + ", department=" + department + ", registeredAt=" + registeredAt + "]";
   }
}