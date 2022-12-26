package cz.domin.chatappv2.Service;

import cz.domin.chatappv2.Model.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService implements UserDetailsService {
    private final PersonService personService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String email = username; // user log in to app with email, not with username as UserDetailService has implemented
        Person person = personService.getPersonByEmail(email);
        return new User(person.getEmail(), person.getPassword(), new ArrayList<>());
    }
}
