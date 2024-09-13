package com.emailSenderWithChat.emailSenderWithChat.business.concretes;

import com.emailSenderWithChat.emailSenderWithChat.business.abstracts.MessageService;
import com.emailSenderWithChat.emailSenderWithChat.business.abstracts.UserService;
import com.emailSenderWithChat.emailSenderWithChat.business.request.UserRequest;
import com.emailSenderWithChat.emailSenderWithChat.business.response.UserResponse;
import com.emailSenderWithChat.emailSenderWithChat.core.mail.MailService;
import com.emailSenderWithChat.emailSenderWithChat.dataAccess.abstracts.UserDao;
import com.emailSenderWithChat.emailSenderWithChat.entities.concretes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserManager implements UserService {

    private final UserDao userDao;
    private final MailService mailService;
    private final MessageService messageService;

    @Autowired
    public UserManager(UserDao userDao, MailService mailService, MessageService messageService) {
        this.userDao = userDao;
        this.mailService = mailService;
        this.messageService = messageService;
    }

    @Override
    public UserResponse addUser(UserRequest userRequest) {
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
       
        
        User savedUser = userDao.save(user);

       
        mailService.sendMail(user.getEmail(), user.getEmail(), "Hoş geldiniz, " + user.getName() + "!");

        return new UserResponse(savedUser.getName(), savedUser.getEmail());
    }

    @Override
    public UserResponse updateUser(int id, UserRequest userRequest) {
        User user = userDao.findById(id).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
       
        
        User updatedUser = userDao.save(user);

       
        mailService.sendMail(updatedUser.getEmail(), updatedUser.getEmail(), "Bilgileriniz başarıyla güncellenmiştir.");

        return new UserResponse(updatedUser.getName(), updatedUser.getEmail());
    }

    @Override
    public void deleteUser(int userId) {
        User user = userDao.findById(userId).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));
        userDao.delete(user);

       
        mailService.sendMail(user.getEmail(), user.getEmail(), "Hesabınız silinmiştir.");
    }

    @Override
    public UserResponse getById(int id) {
        User user = userDao.findById(id).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));
        return new UserResponse(user.getName(), user.getEmail());
    }

    @Override
    public List<UserResponse> getAll() {
        return userDao.findAll().stream()
                .map(user -> new UserResponse(user.getName(), user.getEmail()))
                .collect(Collectors.toList());
    }
}
