package pl.davidduke.todolistapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.davidduke.todolistapi.api.exceptions.UserNotFoundException;
import pl.davidduke.todolistapi.storage.entities.UserEntity;
import pl.davidduke.todolistapi.storage.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        UserEntity user = userRepository
                .findByEmail(username)
                .orElseThrow(
                        () -> new UserNotFoundException(
                                messageSource.getMessage(
                                        "error.user.notfoundByEmail",
                                        new Object[]{username},
                                        LocaleContextHolder.getLocale()
                                )
                        )
                );
        return new CustomUserDetails(user);
    }
}
