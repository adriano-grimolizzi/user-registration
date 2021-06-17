package com.grimolizzi.users;

import com.grimolizzi.errors.NotAdultException;
import com.grimolizzi.errors.NotInFranceException;
import com.grimolizzi.user.User;
import com.grimolizzi.user.UserRepository;
import com.grimolizzi.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Test
    public void shouldFindAll() {

        when(this.repository.findAll()).thenReturn(this.getMockedList());
        Iterable<User> result = this.service.find(null);

        assertEquals(
                this.getMockedList().size(),
                StreamSupport.stream(result.spliterator(), false).count());
    }

    @Test
    public void shouldFindByLastName() {

        when(this.repository.findByLastName("Ocelot")).thenReturn(this.getOcelot());
        Iterable<User> result = this.service.find("Ocelot");

        assertEquals(1, StreamSupport.stream(result.spliterator(), false).count());
    }

    @Test
    public void shouldSaveUser() {

        User validUser = new User("01", "Solid", "Snake", LocalDate.of(1970, 1, 1), "France");

        this.service.save(validUser);

        verify(this.repository).save(validUser);
    }

    @Test(expected = NotAdultException.class)
    public void shouldThrowErrorIfNotAdult() {

        User underageUser = new User("01", "Solid", "Snake", LocalDate.now(), "France");

        this.service.save(underageUser);
    }

    @Test(expected = NotInFranceException.class)
    public void shouldThrowErrorIfNotInFrance() {

        User underageUser = new User("01", "Solid", "Snake", LocalDate.of(1970, 1, 1), "Japan");

        this.service.save(underageUser);
    }

    private List<User> getMockedList() {

        List<User> result = new ArrayList<>();
        result.add(new User("01", "Solid", "Snake", LocalDate.now(), "France"));
        result.add(new User("02", "Liquid", "Snake", LocalDate.now(), "Japan"));
        result.add(new User("03", "Revolver", "Ocelot", LocalDate.now(), "Japan"));
        return result;
    }

    private List<User> getOcelot() {

        List<User> result = new ArrayList<>();
        result.add(new User("03", "Revolver", "Ocelot", LocalDate.now(), "Japan"));
        return result;
    }
}
