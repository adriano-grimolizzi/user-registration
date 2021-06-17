package com.grimolizzi.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grimolizzi.errors.UserNotFoundException;
import com.grimolizzi.user.User;
import com.grimolizzi.user.UserController;
import com.grimolizzi.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private static final String URL_TEMPLATE = "/users";

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService service;

    private MockMvc mvc;

    @Before
    public void init() {
        this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldFindAll() throws Exception {

        when(service.find(null)).thenReturn(getMockedList());

        this.mvc.perform(get(URL_TEMPLATE)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", equalTo("Solid")))
                .andExpect(jsonPath("$[1].firstName", equalTo("Liquid")));
    }

    @Test
    public void shouldFindByLastName() throws Exception {

        when(service.find(any())).thenReturn(getOcelot());

        this.mvc.perform(get(URL_TEMPLATE + "?lastName=Ocelot")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", equalTo("Revolver")))
                .andExpect(jsonPath("$[0].lastName", equalTo("Ocelot")));
    }

    @Test
    public void shouldSave() throws Exception {

        User user1 = new User("01", "Solid", "Snake", LocalDate.now(), "France");

        String jsonBody = new ObjectMapper().writeValueAsString(user1);

        this.mvc.perform(post(URL_TEMPLATE)
                .content(jsonBody)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(this.service).save(user1);
    }

    @Test
    public void shouldNotSaveWithoutMandatoryFields() throws Exception {

        User user1 = new User("01", null, null, null, null);

        String jsonBody = new ObjectMapper().writeValueAsString(user1);

        this.mvc.perform(post(URL_TEMPLATE)
                .content(jsonBody)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(this.service, never()).save(user1);
    }

    private List<User> getMockedList() {

        User user1 = new User("01", "Solid", "Snake", LocalDate.now(), "France");
        User user2 = new User("02", "Liquid", "Snake", LocalDate.now(), "Japan");

        List<User> result = new ArrayList<>();
        result.add(user1);
        result.add(user2);
        return result;
    }

    private List<User> getOcelot() {

        List<User> result = new ArrayList<>();
        result.add(new User("03", "Revolver", "Ocelot", LocalDate.now(), "Japan"));
        return result;
    }
}