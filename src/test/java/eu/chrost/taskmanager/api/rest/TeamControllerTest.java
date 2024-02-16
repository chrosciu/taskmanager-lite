package eu.chrost.taskmanager.api.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.chrost.taskmanager.dto.TeamDto;
import eu.chrost.taskmanager.dto.TeamMembersDto;
import eu.chrost.taskmanager.model.embedded.Codename;
import eu.chrost.taskmanager.model.entities.Team;
import eu.chrost.taskmanager.model.entities.User;
import eu.chrost.taskmanager.repository.TeamRepository;
import eu.chrost.taskmanager.repository.UserRepository;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeamController.class)
public class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TeamRepository teamRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testFindAllTeam() throws Exception {
        // given
        Team team = new Team();
        team.setId(1L);
        team.setName("Team A");
        Codename codename = new Codename();
        codename.setShortName("A");
        codename.setFullName("Team A Fullname");
        team.setCodename(codename);
        team.setDescription("Test Team");

        TeamDto teamDto = new TeamDto();
        teamDto.setId(1L);
        teamDto.setName("Team A");
        teamDto.setCodenameShort("A");
        teamDto.setCodenameFull("Team A Fullname");
        teamDto.setDescription("Test Team");
        
        given(teamRepository.findAll()).willReturn(Arrays.asList(team));

        // when+then
        this.mockMvc.perform(get("/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(teamDto.getName()))
                .andExpect(jsonPath("$[0].codenameShort").value(teamDto.getCodenameShort()))
                .andExpect(jsonPath("$[0].codenameFull").value(teamDto.getCodenameFull()))
                .andExpect(jsonPath("$[0].description").value(teamDto.getDescription()));
    }

    @Test
    public void testFindTeamById() throws Exception {
        // given
        Team team = new Team();
        team.setId(1L);
        team.setName("Team A");

        Codename codename = new Codename();
        codename.setShortName("A");
        codename.setFullName("Team A Fullname");
        team.setCodename(codename);
        team.setDescription("Test Team");

        given(teamRepository.findById(1L)).willReturn(Optional.of(team));

        // when+then
        this.mockMvc.perform(get("/teams/{id}", 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(team.getName()))
            .andExpect(jsonPath("$.codenameShort").value(team.getCodename().getShortName()))
            .andExpect(jsonPath("$.codenameFull").value(team.getCodename().getFullName()))
            .andExpect(jsonPath("$.description").value(team.getDescription()));
    }

    @Test
    public void testCreateTeam() throws Exception {
        // given
        Team team = new Team();
        team.setName("Team A");

        TeamDto newTeamDto = new TeamDto();
        newTeamDto.setName("Team A");
        String teamDtoJson = objectMapper.writeValueAsString(newTeamDto);

        Team savedTeam = new Team();
        savedTeam.setId(1L);

        when(teamRepository.save(team)).thenReturn(savedTeam);

        //when+then
        this.mockMvc.perform(post("/teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(teamDtoJson))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", new StringContains("/teams/1")));
    }

    @Test
    public void testUpdateTeam() throws Exception {
        // given
        Team team = new Team();
        team.setId(1L);

        TeamDto updatedTeamDto = new TeamDto();
        updatedTeamDto.setName("Team A Updated");
        updatedTeamDto.setCodenameShort("A");
        String teamDtoJson = objectMapper.writeValueAsString(updatedTeamDto);

        given(teamRepository.findById(1L)).willReturn(Optional.of(team));

        given(teamRepository.save(team)).willReturn(team);

        //when+then
        this.mockMvc.perform(put("/teams/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(teamDtoJson))
            .andExpect(status().isOk());
    }

    @Test
    public void testDeleteTeam() throws Exception {
        // given
        Team team = new Team();
        team.setId(1L);

        given(teamRepository.findById(1L)).willReturn(Optional.of(team));

        //when+then
        this.mockMvc.perform(delete("/teams/{id}", 1L))
            .andExpect(status().isOk());

        verify(teamRepository).delete(team);
    }

    @Test
    public void testAddTeamMembers() throws Exception {
        // given
        Long teamId = 1L;
        TeamMembersDto teamMembersDto = new TeamMembersDto();
        teamMembersDto.setUserIds(Arrays.asList(1L));
        String teamMembersJson = objectMapper.writeValueAsString(teamMembersDto);

        Team team = new Team();
        team.setId(teamId);

        User user = new User();
        user.setId(1L);

        given(teamRepository.findById(teamId)).willReturn(Optional.of(team));

        given(userRepository.findAllById(Arrays.asList(1L))).willReturn(List.of(user));

        // when+then
        this.mockMvc.perform(put("/teams/{id}/members", teamId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(teamMembersJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testRemoveTeamMembers() throws Exception {
        // given
        Long teamId = 1L;
        TeamMembersDto teamMembersDto = new TeamMembersDto();
        teamMembersDto.setUserIds(Arrays.asList(1L));
        String membersToRemoveJson = objectMapper.writeValueAsString(teamMembersDto);

        Team team = new Team();
        team.setId(teamId);

        given(teamRepository.findById(teamId)).willReturn(Optional.of(team));

        // when+then
        this.mockMvc.perform(delete("/teams/{id}/members", teamId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(membersToRemoveJson))
                .andExpect(status().isOk());
    }


}