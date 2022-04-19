package shoppertise.notes.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import shoppertise.notes.model.UserDetail;
import shoppertise.notes.repository.UserRepository;

@Component
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetail user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }

        List<SimpleGrantedAuthority> auth = Arrays.asList(new SimpleGrantedAuthority("user"));

        return new User(user.getUsername(), user.getPassword(), auth);
    }
}
