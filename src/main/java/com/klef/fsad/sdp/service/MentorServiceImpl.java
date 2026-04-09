package com.klef.fsad.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.dto.EnrollmentDTO;
import com.klef.fsad.sdp.entity.Activity;
import com.klef.fsad.sdp.entity.Enrollment;
import com.klef.fsad.sdp.entity.Mentor;
import com.klef.fsad.sdp.repository.ActivityRepository;
import com.klef.fsad.sdp.repository.EnrollmentRepository;
import com.klef.fsad.sdp.repository.MentorRepository;

@Service
public class MentorServiceImpl implements MentorService
{
    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Mentor verifyMentorLogin(String email, String pwd)
    {
        return mentorRepository.findByEmailAndPassword(email, pwd);
    }

    @Override
    public String addActivity(Activity activity)
    {
        org.springframework.security.core.Authentication authentication =
                org.springframework.security.core.context.SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String login = authentication.getName();

        Mentor mentor = mentorRepository.findByEmail(login);

        if (mentor == null)
        {
            return "Mentor Not Found";
        }

        activity.setMentor(mentor);

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

        if (mentor != null)
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
    public String updateMentorProfile(Mentor mentor)
    {
        Mentor m = mentorRepository.findById(mentor.getId()).orElse(null);

        if (m != null)
        {
            m.setMentorname(mentor.getMentorname());
            m.setContact(mentor.getContact());

            if (mentor.getPassword() != null && !mentor.getPassword().isEmpty())
            {
                m.setPassword(passwordEncoder.encode(mentor.getPassword()));
            }

            mentorRepository.save(m);

            return "Mentor Profile Updated Successfully";
        }

        return "Mentor Not Found";
    }

    @Override
    public EnrollmentDTO convertEnrollmentDTO(Enrollment enrollment)
    {
        EnrollmentDTO dto = new EnrollmentDTO();

        dto.setId(enrollment.getId());

        dto.setParticipantName(enrollment.getParticipant().getName());
        dto.setParticipantEmail(enrollment.getParticipant().getEmail());
        dto.setParticipantContact(enrollment.getParticipant().getContact());

        dto.setActivityId(enrollment.getActivity().getId());
        dto.setActivityName(enrollment.getActivity().getActivityname());
        dto.setActivityCategory(enrollment.getActivity().getCategory());
        dto.setActivityVenue(enrollment.getActivity().getVenue());
        dto.setActivityDate(enrollment.getActivity().getDate());
        dto.setActivityDuration(enrollment.getActivity().getDuration());

        if (enrollment.getActivity().getMentor() != null)
        {
            dto.setMentorName(enrollment.getActivity().getMentor().getMentorname());
        }

        dto.setEnrollmentDate(enrollment.getEnrollmentDate());
        dto.setStatus(enrollment.getStatus());

        return dto;
    }

    @Override
    public List<EnrollmentDTO> viewEnrollmentsByMentor(int mentorid)
    {
        List<Enrollment> enrollments = enrollmentRepository.findEnrollmentsByMentorId(mentorid);

        if (enrollments.isEmpty())
        {
            throw new RuntimeException("No enrollments found for this mentor");
        }

        return enrollments.stream().map(this::convertEnrollmentDTO).toList();
    }
}