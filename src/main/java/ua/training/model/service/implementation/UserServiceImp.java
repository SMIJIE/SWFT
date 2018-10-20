package ua.training.model.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.training.model.dao.implemation.HUserDao;
import ua.training.model.entity.User;
import ua.training.model.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private HUserDao hUserDao;

    @Override
    public Optional<User> getOrCheckUserByEmail(String email) {
        return hUserDao.getOrCheckUserByEmail(email);
    }

    @Override
    public void registerNewUser(User user) {
        hUserDao.create(user);
    }

    @Override
    public void updateUserParameters(User user) {
        hUserDao.update(user);
    }

    @Override
    public List<User> getLimitUsersWithoutAdmin(Integer userId,
                                                Integer limit,
                                                Integer skip) {

        return hUserDao.getLimitUsersWithoutAdmin(userId, limit, skip);
    }

    @Override
    public Integer countUsers(Integer userId) {
        return hUserDao.countUsers(userId);
    }

    @Override
    public void deleteArrayUsersByEmail(List<String> emails) {
        hUserDao.deleteArrayUsersByEmail(emails);
    }
}
