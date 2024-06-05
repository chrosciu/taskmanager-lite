package eu.chrost.taskmanager.team;

import eu.chrost.taskmanager.team.dto.TeamFullDto;
import eu.chrost.taskmanager.team.dto.TeamShortDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

class InMemoryTeamRepository implements TeamRepository, TeamQueryRepository {
    private final Map<Long, Team> teamsMap;
    private final Random random = new Random();

    public InMemoryTeamRepository(Team... teams) {
        this.teamsMap = new HashMap<>(teams.length);
        for (Team team : teams) {
            save(team);
        }
    }

    @Override
    public Team save(Team team) {
        if (null == team.getId()) {
            while (true) {
                long newId = random.nextLong();
                if (!teamsMap.containsKey(newId)) {
                    team.setId(newId);
                    break;
                }
            }
        }
        teamsMap.put(team.getId(), team);
        return team;
    }

    @Override
    public void delete(Team team) {
        teamsMap.remove(team.getId());
    }

    @Override
    public Optional<Team> findById(long id) {
        return Optional.ofNullable(teamsMap.get(id));
    }

    @Override
    public List<TeamShortDto> findAllBy() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<TeamFullDto> findDtoById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean existsByName(String name) {
        return teamsMap.entrySet().stream()
                .anyMatch(entry -> Objects.equals(entry.getValue().getName(), name));
    }

}
