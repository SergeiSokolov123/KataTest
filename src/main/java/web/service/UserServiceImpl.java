package web.service;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.Users;
import web.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Users getById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not find"));
    }

    @Transactional
    @Override
    public void addUser(Users users) {
        userRepository.save(users);
    }

    @Transactional
    @Override
    public void update(Users users) {
        userRepository.save(users);
    }

    @Transactional
    @Override
    public void delete(int id) {
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.deleteById(id);
    }
}
