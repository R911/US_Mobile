package com.usmobile.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  @Id
  private String id;

  private String firstName;

  private String lastName;

  @Email
  private String email;

  @Hidden
  @JsonIgnore
  private String password;

}
