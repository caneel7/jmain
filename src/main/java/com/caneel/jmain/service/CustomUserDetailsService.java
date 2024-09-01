package com.caneel.jmain.service;


import com.caneel.jmain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Autowired
    public CustomUserDetailsService(UserRepository repo){
        this.userRepository = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.caneel.jmain.model.User user = userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Cannot Find User"));
        return new User(user.getEmail(),user.getPassword(),null);
    }

    public UserDetails loadUserById(String id) throws UsernameNotFoundException{
        com.caneel.jmain.model.User  user = userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("Cannot Find User"));
        return new User(user.getId(), user.getId(), null);
    }
}
