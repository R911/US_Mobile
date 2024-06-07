package com.usmobile.application.controller;

import com.usmobile.application.dto.UserCreateDto;
import com.usmobile.application.dto.UserUpdateDto;
import com.usmobile.application.model.User;
import com.usmobile.application.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * User Controller
 */
@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Endpoint for creating new users
   *
   * @param user
   * @return User created user
   */
  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public @ResponseBody ResponseEntity<User> createUser(@RequestBody UserCreateDto user) {
    return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
  }

  /**
   * Endpoint for updating existing user
   *
   * @param user
   * @param id
   * @return User updated user
   */
  @PutMapping("{id}/update")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<User> updateUser(@RequestBody UserUpdateDto user,
      @PathVariable("id") String id) {
    return new ResponseEntity<>(userService.update(user, id), HttpStatus.OK);
  }

}
