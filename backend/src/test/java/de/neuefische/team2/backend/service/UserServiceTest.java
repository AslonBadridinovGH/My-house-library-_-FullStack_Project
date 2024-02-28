package de.neuefische.team2.backend.service;

import de.neuefische.team2.backend.models.User;
import de.neuefische.team2.backend.repos.UserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Service
class UserServiceTest {
    private final UserRepo userRepo = Mockito.mock(UserRepo.class);
    private final IdService idService = Mockito.mock(IdService.class);

    @Test
    void updateStatusTest_whenFavoriteNew_returnUpdatedList() {
        //GIVEN
        String bookId = "3";
        Mockito.when(userRepo.findById("1")).thenReturn(Optional.of(new User("1", "Name", new ArrayList<>(List.of("1", "2")))));
        User userToUpdate = (new User("1", "Name", new ArrayList<>(List.of("1", "2", bookId))));
        Mockito.when((userRepo.save(Mockito.any()))).thenReturn(userToUpdate);

        UserService userService = new UserService(userRepo, idService);
        //WHEN
        User actual = userService.updateStatus(userToUpdate.id(), bookId);

        //THEN
        assertEquals(userToUpdate, actual);
        Mockito.verify(userRepo, Mockito.times(1)).save(userToUpdate);
    }

    @Test
    void updateStatusTest_whenFavoriteAlreadyExists_RemoveFromList() {
        //GIVEN
        String existingBookId = "2";
        Mockito.when(userRepo.findById("1")).thenReturn(Optional.of(new User("1", "Name", new ArrayList<>(List.of("1", "2")))));
        User userToUpdate = (new User("1", "Name", new ArrayList<>(List.of("1", "2", existingBookId))));

        UserService userService = new UserService(userRepo, idService);
        //WHEN
        User actual = userService.updateStatus(userToUpdate.id(), existingBookId);

        //THEN
        assertFalse(actual.favorites().contains(existingBookId));
        Mockito.verify(userRepo, Mockito.times(1)).save(actual);
    }
}