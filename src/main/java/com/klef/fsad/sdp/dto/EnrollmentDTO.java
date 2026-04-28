package com.klef.fsad.sdp.dto;

public class EnrollmentDTO
{
    private int id;

    private String participantName;
    private String participantEmail;
    private String participantContact;

    private int activityId;
    private String activityName;
    private String activityCategory;
    private String activityVenue;
    private String activityDate;
    private double activityDuration;
    private String mentorName;

    private String enrollmentDate;
    private String status;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getParticipantName()
    {
        return participantName;
    }

    public void setParticipantName(String participantName)
    {
        this.participantName = participantName;
    }

    public String getParticipantEmail()
    {
        return participantEmail;
    }

    public void setParticipantEmail(String participantEmail)
    {
        this.participantEmail = participantEmail;
    }

    public String getParticipantContact()
    {
        return participantContact;
    }

    public void setParticipantContact(String participantContact)
    {
        this.participantContact = participantContact;
    }

    public int getActivityId()
    {
        return activityId;
    }

    public void setActivityId(int activityId)
    {
        this.activityId = activityId;
    }

    public String getActivityName()
    {
        return activityName;
    }

    public void setActivityName(String activityName)
    {
        this.activityName = activityName;
    }

    public String getActivityCategory()
    {
        return activityCategory;
    }

    public void setActivityCategory(String activityCategory)
    {
        this.activityCategory = activityCategory;
    }

    public String getActivityVenue()
    {
        return activityVenue;
    }

    public void setActivityVenue(String activityVenue)
    {
        this.activityVenue = activityVenue;
    }

    public String getActivityDate()
    {
        return activityDate;
    }

    public void setActivityDate(String activityDate)
    {
        this.activityDate = activityDate;
    }

    public double getActivityDuration()
    {
        return activityDuration;
    }

    public void setActivityDuration(double activityDuration)
    {
        this.activityDuration = activityDuration;
    }

    public String getMentorName()
    {
        return mentorName;
    }

    public void setMentorName(String mentorName)
    {
        this.mentorName = mentorName;
    }

    public String getEnrollmentDate()
    {
        return enrollmentDate;
    }

    public void setEnrollmentDate(String enrollmentDate)
    {
        this.enrollmentDate = enrollmentDate;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "EnrollmentDTO [id=" + id
                + ", participantName=" + participantName
                + ", participantEmail=" + participantEmail
                + ", participantContact=" + participantContact
                + ", activityId=" + activityId
                + ", activityName=" + activityName
                + ", activityCategory=" + activityCategory
                + ", activityVenue=" + activityVenue
                + ", activityDate=" + activityDate
                + ", activityDuration=" + activityDuration
                + ", mentorName=" + mentorName
                + ", enrollmentDate=" + enrollmentDate
                + ", status=" + status + "]";
    }
}