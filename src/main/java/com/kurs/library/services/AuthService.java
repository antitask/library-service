package com.kurs.library.services;

import com.kurs.library.entity.Role;
import com.kurs.library.entity.User;
import com.kurs.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;

    /**
     this method checks the authorization header and, if
     the header is valid, extract and return the User
     */
    public User authorisedUser(String authorisationHeader) {
        String[] parseHeader = authorisationHeader.substring(7).split("\\.");
        log.info(String.format("Parsed header have %d parts", parseHeader.length));
        if (parseHeader.length == 3) {
            Optional<User> byName = userRepository.findByName(parseHeader[0]);
            if (byName.isPresent() && byName.get().getPassword().equals(parseHeader[1]) &&
                    (byName.get().getRole().equals(Role.valueOf(parseHeader[2])) ||
                            byName.get().getRole().equals(Role.valueOf(parseHeader[2])))
            ) {
                return byName.get();
            }
        }
        log.info("Authorisation header is not parsed successfully");
        return null;
    }
}
