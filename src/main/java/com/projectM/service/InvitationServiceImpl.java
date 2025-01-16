package com.projectM.service;

import com.projectM.model.Invitation;
import com.projectM.repository.InvitationRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService{

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public void sendInvitation(String userEmail, Long projectId) throws MessagingException {
        String token = UUID.randomUUID().toString();

        Invitation invitation = new Invitation();
        invitation.setEmail(userEmail);
        invitation.setProjectId(projectId);
        invitation.setToken(token);

        invitationRepository.save(invitation);
        String invitationLink = "http://localhost:5173/accept_invitation?token="+token;
        emailService.sendEmailWithToken(userEmail, invitationLink);
    }

    @Override
    public Invitation acceptInvitation(String token) throws Exception {   //Unused func-parameter userId
        Invitation invitation = invitationRepository.findByToken(token);
        if (invitation == null) {
            throw new Exception("invalid invitation");
        }
        return invitation;
    }

    @Override
    public String getTokenByUserMail(String userEmail) throws Exception {
        Invitation invitation = invitationRepository.findByEmail(userEmail);
        String token = invitation.getToken();
        if (token == null) {
            throw new Exception("token doesn't exist");
        }
        return token;
    }

    @Override
    public void deleteToken(String token) throws Exception {
        Invitation invitation = invitationRepository.findByToken(token);
        if (invitation == null || invitation.getToken() == null) {
            throw new Exception("token doesn't exist");
        }
        Long id = invitation.getId();
        invitationRepository.deleteById(id);
    }
}
