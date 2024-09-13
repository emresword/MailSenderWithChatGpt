package com.emailSenderWithChat.emailSenderWithChat.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emailSenderWithChat.emailSenderWithChat.entities.concretes.User;

public interface UserDao extends JpaRepository<User, Integer>{

}
