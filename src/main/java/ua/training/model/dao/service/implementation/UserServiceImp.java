package ua.training.model.dao.service.implementation;

import ua.training.model.dao.UserDao;
import ua.training.model.dao.service.UserService;
import ua.training.model.entity.User;

import java.util.List;
import java.util.Optional;

public class UserServiceImp implements UserService {
    private UserDao userDao = DAO_FACTORY.createUserDao();

    @Override
    public Optional<User> getOrCheckUserByEmail(String email) {
        return userDao.getOrCheckUserByEmail(email);
    }

    @Override
    public void registerNewUser(User user) {
        userDao.create(user);
    }

    @Override
    public void updateUserParameters(User user) {
        userDao.update(user);
    }

    @Override
    public List<User> getLimitUsersWithoutAdmin(Integer userId,
                                                Integer limit,
                                                Integer skip) {
        return userDao.getLimitUsersWithoutAdmin(userId, limit, skip);
    }

    @Override
    public Integer countUsers(Integer userId) {
        return userDao.countUsers(userId);
    }

    @Override
    public void deleteArrayUsersByEmail(List<String> emails) {
        userDao.deleteArrayUsersByEmail(emails);
    }
}
