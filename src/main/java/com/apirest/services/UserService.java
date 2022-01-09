package com.apirest.services;

import com.apirest.models.User;
import com.apirest.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> find() throws EntityNotFoundException {
        List<User> users = this.userRepository.findAll();

        if(!users.isEmpty()){
            return users;
        }

        throw new EntityNotFoundException();
    }

    public User findById(Long id) throws EntityNotFoundException {
        Optional<User> user = this.userRepository.findById(id);

        if(user.isPresent()){
            return user.get();
        }

        throw new EntityNotFoundException();
    }

    public User create(User user) throws Exception {
        Optional<User> userFind = this.userRepository.findByUsernameIgnoreCase(user.getUsername()).stream().findFirst();

        if (userFind.isPresent()) {
            throw new Exception("User is already exists!");
        }
        this.userRepository.save(user);
        return user;
    }

    public User update(Long id, User user) throws EntityNotFoundException, Exception {
        Optional<User> userFind = this.userRepository.findById(id);

        if (!userFind.isPresent()) {
            throw new EntityNotFoundException();
        }

        User userGet = userFind.get();

        userGet.setUsername(user.getUsername());
        userGet.setName(user.getName());

        User userUpdate = this.userRepository.save(userGet);

        if (!(userUpdate instanceof User)) {
            throw new Exception("Error update user!");
        }
        return userUpdate;
    }

    public Boolean delete(Long id) throws EntityNotFoundException {
        Optional<User> userFind = this.userRepository.findById(id);

        if (!userFind.isPresent()) {
            throw new EntityNotFoundException();
        }

        User userGet = userFind.get();

        this.userRepository.delete(userGet);

        return true;
    }

    public List<User> listMoreThan(Long id) {
        List<User> users = this.userRepository.findByIdGreaterThan(id);

        if(!users.isEmpty()){
            return users;
        }
        throw new EntityNotFoundException();
    }

    public List<User> findAllMoreThan(Long id) {
        List<User> users = this.userRepository.findAllMoreThan(id);

        if(!users.isEmpty()){
            return users;
        }
        throw new EntityNotFoundException();
    }

    public List<User> findByName(String username) {
        List<User> users = this.userRepository.findByUsernameIgnoreCase(username);

        if(!users.isEmpty()){
            return users;
        }
        throw new EntityNotFoundException();
    }
}
