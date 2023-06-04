package com.att.training.springboot.examples.user;

import com.att.training.springboot.examples.db.DbContext;
import com.att.training.springboot.examples.db.DbRegion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void create(@Valid @RequestBody User user) {
        var dbRegion = DbRegion.fromId(user.id());
        log.info("#create - user = {}, dbRegion = {}", user, dbRegion);
        DbContext.setRegion(dbRegion);
        userService.create(user);
    }
}
