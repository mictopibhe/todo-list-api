package pl.davidduke.todolistapi.api.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.davidduke.todolistapi.api.services.UserService;


@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {

    final UserService userService;


}
