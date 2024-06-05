package eu.chrost.taskmanager.team;

import eu.chrost.taskmanager.common.TeamMembersDto;
import eu.chrost.taskmanager.team.dto.TeamDto;
import eu.chrost.taskmanager.team.exception.TeamAlreadyExistsException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TeamFacadeTest {
    private static final Long EXISTING_TEAM_ID = 1L;

    private final InMemoryTeamRepository teamRepository = new InMemoryTeamRepository(
            createTeam(1L, "Avengers", "A", "Mighty Avengers", "the greatest team on Earth")
    );

    private final TeamFacade teamFacade = new TeamFacade(teamRepository, teamRepository);

    @Test
    void A_new_team_should_be_persisted_and_its_id_returned() {
        //given
        TeamDto teamDto = new TeamDto();
        teamDto.setName("Invaders");

        //when
        long createdTeamId = teamFacade.createTeamAndReturnItsId(teamDto);

        //then
        assertThat(teamRepository.findById(createdTeamId))
                .hasValueSatisfying(t -> assertThat(t.getName()).isEqualTo("Invaders"));
    }

    @Test
    void A_new_team_cannot_be_persisted_when_another_one_with_the_same_name_exists() {
        //given
        TeamDto teamDto = new TeamDto();
        teamDto.setName("Avengers");

        //when / then
        assertThatExceptionOfType(TeamAlreadyExistsException.class)
                .isThrownBy(() -> teamFacade.createTeamAndReturnItsId(teamDto));
    }

    @Test
    void An_existing_team_can_be_deleted() {
        //when
        teamFacade.deleteTeamWithId(EXISTING_TEAM_ID);

        //then
        assertThat(teamRepository.findById(EXISTING_TEAM_ID)).isEmpty();
    }

    @Test
    void An_existing_team_can_be_updated() {
        //given
        String newName = randomString();
        String newCodenameShort = randomString();
        String newCodenameFull = randomString();
        String newDescription = randomString();
        TeamDto dto = new TeamDto();
        dto.setName(newName);
        dto.setCodenameShort(newCodenameShort);
        dto.setCodenameFull(newCodenameFull);
        dto.setDescription(newDescription);

        //when
        teamFacade.updateTeamWithId(EXISTING_TEAM_ID, dto);

        //then
        assertThat(teamRepository.findById(EXISTING_TEAM_ID)).hasValueSatisfying(t -> {
            assertThat(t.getName()).isEqualTo(newName);
            assertThat(t.getCodename().getShortName()).isEqualTo(newCodenameShort);
            assertThat(t.getCodename().getFullName()).isEqualTo(newCodenameFull);
            assertThat(t.getDescription()).isEqualTo(newDescription);
        });
    }

    @Test
    void Members_can_be_added_to_the_team() {
        //given
        Team team = createTeam(1L, "Avengers", "A", "Mighty Avengers", "the greatest team on Earth");
        teamRepository.save(team);
        TeamMembersDto teamMembersDto = new TeamMembersDto();
        teamMembersDto.setUserIds(List.of(1L, 2L));

        //when
        teamFacade.addMembersToTeam(1L, teamMembersDto);

        //then
        assertThat(teamRepository.findById(1L)).hasValueSatisfying(t -> {
            assertThat(t.getMemberIds()).containsExactlyInAnyOrder(1L, 2L);
        });
    }

    @Test
    void Members_can_be_removed_from_the_team() {
        //given
        Team team = createTeam(1L, "Avengers", "A", "Mighty Avengers", "the greatest team on Earth");
        team.addMember(new SimpleUser(1L));
        team.addMember(new SimpleUser(2L));
        teamRepository.save(team);
        TeamMembersDto teamMembersDto = new TeamMembersDto();
        teamMembersDto.setUserIds(List.of(2L));

        //when
        teamFacade.removeMembersFromTeam(1L, teamMembersDto);

        //then
        assertThat(teamRepository.findById(1L)).hasValueSatisfying(t -> {
            assertThat(t.getMemberIds()).containsExactlyInAnyOrder(1L);
        });
    }

    private static Team createTeam(long id, String name, String codenameShort, String codenameFull, String description) {
        Team team = new Team();
        team.setId(id);
        team.setName(name);
        Codename codename = new Codename();
        codename.setShortName(codenameShort);
        codename.setFullName(codenameFull);
        team.setCodename(codename);
        team.setDescription(description);
        return team;
    }

    private static String randomString() {
        return UUID.randomUUID().toString();
    }

}
