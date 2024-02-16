package eu.chrost.taskmanager;

import eu.chrost.taskmanager.model.entities.Team;
import eu.chrost.taskmanager.model.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerFullTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void testAddTeamMembers() throws Exception {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Team team = new Team();
        team.setName("Wajchowi");
        User user = new User();
        user.setLogin("chrosciu");
        entityManager.persist(team);
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();

        long teamId = team.getId();
        long userId = user.getId();

        String json = String.format("{\"userIds\": [%d]}", userId);

        this.mockMvc.perform(put("/teams/{id}/members", teamId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        team = entityManager.find(Team.class, teamId);
        List<Long> userIds = team.getMembers().stream().map(User::getId).toList();
        user = entityManager.find(User.class, userId);
        List<Long> teamIds = user.getTeams().stream().map(Team::getId).toList();
        entityManager.getTransaction().commit();
        entityManager.close();
        assertThat(teamIds).containsExactly(teamId);
        assertThat(userIds).containsExactly(userId);
    }
}
