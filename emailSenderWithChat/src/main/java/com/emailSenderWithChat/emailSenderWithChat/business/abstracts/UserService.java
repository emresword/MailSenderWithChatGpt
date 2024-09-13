package com.emailSenderWithChat.emailSenderWithChat.business.abstracts;

import com.emailSenderWithChat.emailSenderWithChat.business.request.UserRequest;
import com.emailSenderWithChat.emailSenderWithChat.business.response.UserResponse;
import java.util.List;

public interface UserService {
    UserResponse getById(int id);
    List<UserResponse> getAll();
    UserResponse addUser(UserRequest userRequest);
    UserResponse updateUser(int id, UserRequest userRequest);
    void deleteUser(int id);
}
