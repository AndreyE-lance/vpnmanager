package com.elantsev.vpnmanager.service;

import com.elantsev.vpnmanager.model.Role;
import com.elantsev.vpnmanager.model.User;
import com.elantsev.vpnmanager.repository.RoleRepository;
import com.elantsev.vpnmanager.repository.UserRepository;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.Buffer;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final EntityManager entityManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BasicPasswordEncryptor basicPasswordEncryptor;

    public UserService(EntityManager entityManager,
                       UserRepository userRepository,
                       RoleRepository roleRepository,
                       BasicPasswordEncryptor basicPasswordEncryptor) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.basicPasswordEncryptor = basicPasswordEncryptor;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if (user == null) throw new UsernameNotFoundException("User Not Found");
        return user;
    }

    public User findUserById(Long userId){
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(new User());
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Transactional
    public boolean saveUser(User user){
        if(userRepository.findByUsername(user.getUsername())!=null){
            return false;
        }
        user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        user.setPassword(basicPasswordEncryptor.encryptPassword(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId){
        if(userRepository.existsById(userId)){
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    /*public List<User> usergtList(Long minId){
        return entityManager.createQuery("SELECT u FROM User u WHERE u.id>:paramId", User.class)
                .setParameter("paramId", minId).getResultList();
    }*/

    public boolean changeFileOwner(Path path, String toUser, String password){
        Process prc = null;
        try {
            prc = Runtime.getRuntime().exec("sudo -S chown "+toUser+" "+path.toString());
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(prc.getOutputStream()));
            bw.write(password+"\n");
            bw.flush();
            if(getCurrentOwner(path).equals(toUser)) return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getCurrentOwner(Path path){
        Process prc = null;
        try {
            prc = Runtime.getRuntime().exec("ls -l "+path.toString());
            BufferedReader bReader = new BufferedReader(new InputStreamReader(prc.getInputStream()));
            return bReader.readLine().split(" ")[2];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}


























