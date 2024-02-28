package de.neuefische.team2.backend.security;

import de.neuefische.team2.backend.models.User;
import de.neuefische.team2.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public String getMe() {

        if ("anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            return "anonymousUser";
        }
        return userService.getUserById(SecurityContextHolder.getContext().getAuthentication().getName());
    }


    @PostMapping("/me")
    public User updateFavorite(@RequestBody String bookId) {
        return userService.updateStatus(SecurityContextHolder.getContext().getAuthentication().getName(), bookId);
    }
}