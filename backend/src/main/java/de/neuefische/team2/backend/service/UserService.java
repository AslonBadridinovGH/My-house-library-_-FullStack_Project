package de.neuefische.team2.backend.service;


import de.neuefische.team2.backend.models.User;
import de.neuefische.team2.backend.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final IdService idService;


    public String getUserById(String id) {
        Optional<User> byId = userRepo.findById(id);
        if (byId.isPresent()) {
            return byId.get().name();
        }
        throw (new ResponseStatusException(HttpStatus.NOT_FOUND, "No user with such id!"));
    }


    public User updateStatus(String userId, String bookId) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<String> updatedFavorites = new ArrayList<>(user.favorites());
            if (updatedFavorites.contains(bookId)) {
                updatedFavorites.remove(bookId);
            } else {
                updatedFavorites.add(bookId);
            }
            User updatedUser = user.withFavorites(updatedFavorites);
            userRepo.save(updatedUser);
            return updatedUser;
        }
        throw (new ResponseStatusException(HttpStatus.NOT_FOUND, "No user with such id!"));
    }

}
