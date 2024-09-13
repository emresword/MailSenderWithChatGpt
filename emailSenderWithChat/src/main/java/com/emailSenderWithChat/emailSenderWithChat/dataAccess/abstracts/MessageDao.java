package com.emailSenderWithChat.emailSenderWithChat.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emailSenderWithChat.emailSenderWithChat.entities.concretes.Message;

public interface MessageDao extends JpaRepository<Message, Integer>{

}
