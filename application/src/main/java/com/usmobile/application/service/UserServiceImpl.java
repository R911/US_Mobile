package com.usmobile.application.service;

import com.usmobile.application.dto.UserCreateDto;
import com.usmobile.application.dto.UserUpdateDto;
import com.usmobile.application.model.User;
import com.usmobile.application.repository.UserRepository;
import com.usmobile.application.util.Constants;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final SequenceGeneratorService sequenceGeneratorService;

  private final MongoTemplate mongoTemplate;

  public UserServiceImpl(UserRepository userRepository,
      SequenceGeneratorService sequenceGeneratorService, MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
    this.sequenceGeneratorService = sequenceGeneratorService;
    this.userRepository = userRepository;
  }

  /**
   * Service to save User objects
   *
   * @param inputUser
   * @return
   */
  @Override
  public User create(UserCreateDto inputUser) {
    User user = User.builder()
        .id(Constants.USER_ID_PREFIX + sequenceGeneratorService.getSequenceNumber(
            Constants.USER_DB_SEQUENCE_NAME))
        .firstName(inputUser.getFirstName())
        .lastName(inputUser.getLastName())
        .email(inputUser.getEmail())
        .password(inputUser.getPassword())
        .build();
    return userRepository.save(user);
  }

  /**
   * Service to update existing user details.
   *
   * @param user
   * @param id
   * @return
   */
  @Override
  public User update(UserUpdateDto user, String id) {
    Query query = new Query().addCriteria(Criteria.where("id").is(id));
    Update updateDefinition = new Update();

    if (user.getFirstName() != null && !user.getFirstName().isEmpty()) {
      updateDefinition.set("firstName", user.getFirstName());
    }

    if (user.getLastName() != null && !user.getLastName().isEmpty()) {
      updateDefinition.set("lastName", user.getLastName());
    }

    if (user.getEmail() != null && !user.getEmail().isEmpty()) {
      updateDefinition.set("email", user.getEmail());
    }

    FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);

    return mongoTemplate.findAndModify(query, updateDefinition, options, User.class);
  }


}
