package com.usmobile.application.service;

import com.usmobile.application.dto.UserCreateDto;
import com.usmobile.application.dto.UserUpdateDto;
import com.usmobile.application.model.User;

public interface UserService {

  User create(UserCreateDto user);

  User update(UserUpdateDto user, String id);
}
