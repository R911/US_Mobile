package com.usmobile.application.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usmobile.application.dto.UserCreateDto;
import com.usmobile.application.dto.UserUpdateDto;
import com.usmobile.application.model.User;
import com.usmobile.application.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Autowired
  private ObjectMapper objectMapper;

  private UserCreateDto userCreateDto;

  private UserUpdateDto userUpdateDto;

  private User user;

  @BeforeEach
  void init() {
    user = new User("u_1", "Rahul", "Singh", "rahul@abc.com", "Password@1234");
    userCreateDto = new UserCreateDto("Rahul", "Singh", "rahul@abc.com", "Password@1234");
    userUpdateDto = new UserUpdateDto("Rahul", "Singh", "rahul@abc.com");
  }


  @Test
  void userController_createUser_returnCreatedUser() throws Exception {
    when(userService.create(userCreateDto)).thenReturn(user);

    mockMvc.perform(post("/user/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(userCreateDto)))
        .andDo(print()).andExpect(status().isCreated())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(user.getFirstName())))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(user.getLastName())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(user.getEmail())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(user.getId())));
  }

  @Test
  void update() throws Exception {
    when(userService.update(userUpdateDto, "u_1")).thenReturn(user);

    mockMvc.perform(put("/user/u_1/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(userUpdateDto)))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(user.getFirstName())))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(user.getLastName())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(user.getEmail())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(user.getId())));
  }
}