package pl.davidduke.todolistapi.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.davidduke.todolistapi.api.services.UserService;


@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


}
