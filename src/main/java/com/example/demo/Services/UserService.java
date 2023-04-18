package com.example.demo.Services;

import com.example.demo.Controllers.GenreController;
import com.example.demo.Entity.Client;
import com.example.demo.Entity.Role;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService  implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserRepository.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = userRepository.findByName(username);
        logger.info(String.valueOf(client));
        logger.info(userRepository.findAll().toString());
        if (client==null){
            throw new UsernameNotFoundException("User not found");
        }

        return client;
    }

    public boolean saveUser(Client client) {
        Client userFromDB = userRepository.findByName(client.getUsername());

        if (userFromDB != null) {
            return false;
        }

        client.setRoles(Arrays.asList(roleRepository.findById(1L).get()));
        logger.info(String.valueOf(client));
        client.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));
        client.setMoney(0L);
        userRepository.save(client);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            for (Role role: userRepository.findById(userId).get().getRoles()){
                role.getUsers().remove(userRepository.findById(userId));
            }
            userRepository.findById(userId).get().setRoles(null);
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<Client> allUsers() {
        return userRepository.findAll();
    }

    public List<Client> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", Client.class)
                .setParameter("paramId", idMin).getResultList();
    }
}
