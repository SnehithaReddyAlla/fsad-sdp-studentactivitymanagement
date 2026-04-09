package com.klef.fsad.sdp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.dto.ParticipantDTO;
import com.klef.fsad.sdp.entity.Admin;
import com.klef.fsad.sdp.entity.Participant;
import com.klef.fsad.sdp.entity.Mentor;
import com.klef.fsad.sdp.repository.AdminRepository;
import com.klef.fsad.sdp.repository.ParticipantRepository;
import com.klef.fsad.sdp.repository.MentorRepository;

@Service
public class AdminServiceImpl implements AdminService
{
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Admin verifyAdminLogin(String username, String password)
    {
        return adminRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public String addMentor(Mentor mentor)
    {
        if (mentor.getPassword() != null && !mentor.getPassword().isEmpty())
        {
            mentor.setPassword(passwordEncoder.encode(mentor.getPassword()));
        }

        mentorRepository.save(mentor);
        return "Mentor Added Successfully";
    }

    @Override
    public List<Mentor> viewAllMentors()
    {
        return mentorRepository.findAll();
    }

    @Override
    public List<Participant> viewAllParticipants()
    {
        return participantRepository.findAll();
    }

    @Override
    public boolean deleteMentor(int id)
    {
        if (mentorRepository.existsById(id))
        {
            mentorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public String deleteParticipant(int id)
    {
        participantRepository.deleteById(id);
        return "Participant Deleted Successfully";
    }

    @Override
    public List<ParticipantDTO> displayallparticipantsDTO()
    {
        List<Participant> customers = viewAllParticipants();

        return customers.stream()
                .map(this::ParticipantToParticipantDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipantDTO ParticipantToParticipantDTO(Participant p) 
    {
        ParticipantDTO dto = new ParticipantDTO();

        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setGender(p.getGender());
        dto.setDepartment(p.getDepartment());
        dto.setEmail(p.getEmail());

        return dto;
    }
}