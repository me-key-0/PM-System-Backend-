package com.projectM.service;

import com.projectM.model.Invitation;
import jakarta.mail.MessagingException;

public interface InvitationService {

    void sendInvitation(String userEmail, Long projectId) throws MessagingException;

    Invitation acceptInvitation(String token) throws Exception;

    String getTokenByUserMail(String userEmail) throws Exception;

    void deleteToken(String token) throws Exception;

}
