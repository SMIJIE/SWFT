package ua.training.model.dao.service;

import ua.training.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends Service {
    Optional<User> getOrCheckUserByEmail(String email);

    void registerNewUser(User user);

    void updateUserParameters(User user);

    List<User> getLimitUsersWithoutAdmin(Integer userId,
                                         Integer limit,
                                         Integer skip);

    Integer countUsers(Integer userId);

    void deleteArrayUsersByEmail(List<String> emails);
}
