package com.apirest.controllers;

import com.apirest.models.User;
import com.apirest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> find() throws ResponseStatusException {
        try {
            List<User> users = this.userService.find();
            return users;
        }catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Users were not found!");
        }
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable("id") Long id) throws ResponseStatusException {
        try {
            User user = this.userService.findById(id);
            return user;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }
    }

    @PostMapping()
    public User create(@RequestBody User user) throws ResponseStatusException {
        try {
            User newUser = this.userService.create(user);
            return newUser;
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "There was an error creating user!");
        }
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody User user) throws ResponseStatusException {
        try {
            User upUser = this.userService.update(id, user);
            return upUser;
        }catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "There was an error updating user!");
        }
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) throws ResponseStatusException{
       try {
           Boolean delete = this.userService.delete(id);
           if(!delete) {
               throw new ResponseStatusException(HttpStatus.CONFLICT, "There was an error deleting user!");
           }
           return "User delete successfully!";
       }catch (EntityNotFoundException e) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
       }catch (Exception e) {
           throw new ResponseStatusException(HttpStatus.CONFLICT, "There was an error deleting user!");
       }
    }

    @GetMapping("/more-than/{id}")
    public List<User> listMoreThan(@PathVariable("id") Long id) throws ResponseStatusException {
        try {
            List<User> users = this.userService.listMoreThan(id);
            return users;
        }catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Users were not found!");
        }
    }

    @GetMapping("/greater-than/{id}")
    public List<User> findAllMoreThan(@PathVariable("id") Long id) throws ResponseStatusException {
        try {
            List<User> users = this.userService.findAllMoreThan(id);
            return users;
        }catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Users were not found!");
        }
    }

    @GetMapping("/name/{username}")
    public List<User> findByName(@PathVariable("username") String username) throws ResponseStatusException {
        try {
            List<User> users = this.userService.findByName(username);
            return users;
        }catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Users were not found!");
        }
    }
}
