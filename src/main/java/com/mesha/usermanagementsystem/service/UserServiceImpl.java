package com.mesha.usermanagementsystem.service;

import com.mesha.usermanagementsystem.entity.UserEntity;
import com.mesha.usermanagementsystem.model.User;
import com.mesha.usermanagementsystem.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository repository;
    @Override
    public User saveUser(User user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        repository.save(userEntity);
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<UserEntity> userEntities = repository.findAll();
        List<User> users = userEntities.stream().map(userEntity -> new User(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmailId()
        )).collect(Collectors.toList());
        return users;
    }

    @Override
    public User getUserById(Long id) {
        UserEntity userEntity = repository.findById(id).get();
        User user = new User();
        BeanUtils.copyProperties(userEntity, user);
        return user;
    }

    @Override
    public boolean deleteUser(Long id) {
        UserEntity userEntity = repository.findById(id).get();
        repository.delete(userEntity);
        return true;
    }

    @Override
    public User updateUser(Long id, User user) {
        UserEntity userEntity = repository.findById(id).get();
        userEntity.setEmailId(user.getEmailId());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        repository.save(userEntity);
        return user;
    }
}
