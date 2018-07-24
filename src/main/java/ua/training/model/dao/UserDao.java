package ua.training.model.dao;

import ua.training.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    Optional<User> getOrCheckUserByEmail(String email);

    List<User> getLimitUsersWithoutAdmin(Integer adminId,
                                         Integer limit,
                                         Integer skip);

    Integer countUsers(Integer userId);

    void deleteArrayUsersByEmail(List<String> emails);
}
