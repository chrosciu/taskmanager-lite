package eu.chrost.taskmanager.api.rest;

import eu.chrost.taskmanager.model.embedded.UserName;
import eu.chrost.taskmanager.model.entities.User;
import eu.chrost.taskmanager.model.enums.TeamRole;
import eu.chrost.taskmanager.repository.UserRepository;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;
  
    @MockBean
    private UserRepository userRepository;

    @Test
    public void testGetAllUsers() throws Exception {
        User user1 = new User();
        user1.setTeamRole(TeamRole.DEVELOPER);
        user1.setLogin("johndoe");
        user1.setPassword("jd_password");

        UserName userName1 = new UserName();
        userName1.setFirstName("John");
        userName1.setLastName("Doe");
        user1.setUserName(userName1);

        given(userRepository.findAll()).willReturn(Collections.singletonList(user1));

        mvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].login").value("johndoe"))
                .andExpect(jsonPath("$[0].password").value("jd_password"))
                .andExpect(jsonPath("$[0].teamRole").value("DEVELOPER"));
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setTeamRole(TeamRole.DEVELOPER);
        user.setLogin("johndoe");
        user.setPassword("jd_password");

        UserName userName = new UserName();
        userName.setFirstName("John");
        userName.setLastName("Doe");
        user.setUserName(userName);

        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        mvc.perform(get("/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.login").value("johndoe"))
                .andExpect(jsonPath("$.password").value("jd_password"))
                .andExpect(jsonPath("$.teamRole").value("DEVELOPER"));
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        user.setTeamRole(TeamRole.DEVELOPER);
        user.setLogin("johndoe");
        user.setPassword("jd_password");

        UserName userName = new UserName();
        userName.setFirstName("John");
        userName.setLastName("Doe");
        user.setUserName(userName);

        User savedUser = new User();
        savedUser.setId(1L);

        given(userRepository.save(user)).willReturn(savedUser);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"login\": \"johndoe\", \"password\": \"jd_password\", \"teamRole\": \"DEVELOPER\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", new StringContains("/users/1")));
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setTeamRole(TeamRole.DEVELOPER);
        user.setLogin("johndoe");
        user.setPassword("jd_password");

        UserName userName = new UserName();
        userName.setFirstName("John");
        userName.setLastName("Doe");
        user.setUserName(userName);

        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        given(userRepository.save(user)).willReturn(user);

        mvc.perform(put("/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"login\": \"johndoe\", \"password\": \"jd_password\", \"teamRole\": \"DEVELOPER\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.login").value("johndoe"))
                .andExpect(jsonPath("$.password").value("jd_password"))
                .andExpect(jsonPath("$.teamRole").value("DEVELOPER"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        User user = new User();
        user.setId(1L);

        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        mvc.perform(delete("/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userRepository).delete(user);
    }
}