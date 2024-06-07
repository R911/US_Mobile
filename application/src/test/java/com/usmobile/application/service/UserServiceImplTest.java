package com.usmobile.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.usmobile.application.dto.UserCreateDto;
import com.usmobile.application.dto.UserUpdateDto;
import com.usmobile.application.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
class UserServiceImplTest {

  @Autowired
  private UserService userService;

  @Autowired
  private MongoTemplate mongoTemplate;


  @AfterEach
  void tearDown() {
    mongoTemplate.getDb().drop();
  }

  @Test
  void userService_createUser_returnCreatedUser() {

    User user = userService.create(
        new UserCreateDto("Rahul", "Singh", "rahul91105@gmail.com", "Rahul@1234"));

    assertEquals(user.getEmail(), "rahul91105@gmail.com");
    assertEquals(user.getFirstName(), "Rahul");
    assertEquals(user.getLastName(), "Singh");
    assertEquals(user.getPassword(), "Rahul@1234");
  }

  @Test
  void userService_updateExistingUser_returnUpdatedUser() {

    User user = userService.create(
        new UserCreateDto("Rahul", "Singh", "rahul91105@gmail.com", "Rahul@1234"));

    assertEquals(user.getEmail(), "rahul91105@gmail.com");
    assertEquals(user.getFirstName(), "Rahul");
    assertEquals(user.getLastName(), "Singh");
    assertEquals(user.getPassword(), "Rahul@1234");

    User updatedUser = userService.update(new UserUpdateDto("Adam", "Hunt", "AdamHunt@gmail.com"),
        user.getId());

    assertEquals(updatedUser.getId(), user.getId());
    assertEquals(updatedUser.getEmail(), "AdamHunt@gmail.com");
    assertEquals(updatedUser.getFirstName(), "Adam");
    assertEquals(updatedUser.getLastName(), "Hunt");
    assertEquals(updatedUser.getPassword(), "Rahul@1234");

  }

}