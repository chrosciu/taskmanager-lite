package eu.chrost.taskmanager.user;

import eu.chrost.taskmanager.user.dto.UserDto;
import eu.chrost.taskmanager.user.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static eu.chrost.taskmanager.user.UserRole.BUSINESS_ANALYST;
import static eu.chrost.taskmanager.user.UserRole.DEVELOPER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserFacadeTest {
    private static final Long EXISTING_USER_ID = 1L;

    private final InMemoryUserRepository userRepository = new InMemoryUserRepository(
            createUser(1L, "Bruce", "Banner", "bbanner", DEVELOPER)
    );

    private final UserFacade userFacade = new UserFacade(userRepository, userRepository);

    @Test
    void A_new_user_should_be_persisted_and_its_id_returned() {
        //given
        UserDto userDto = new UserDto();
        userDto.setFirstName("Natasha");
        userDto.setLastName("Romanow");
        userDto.setUserRole("DEVELOPER");

        //when
        long createdUserId = userFacade.createNewUserAndReturnItsId(userDto);

        //then
        assertThat(userRepository.findById(createdUserId))
                .hasValueSatisfying(u -> assertThat(u.getUserName().getFirstName()).isEqualTo("Natasha"))
                .hasValueSatisfying(u -> assertThat(u.getUserName().getLastName()).isEqualTo("Romanow"))
                .hasValueSatisfying(u -> assertThat(u.getUserRole()).isEqualTo(DEVELOPER));
    }

    @Test
    void A_new_user_cannot_be_persisted_when_another_one_with_the_same_first_and_last_name_exists() {
        //given
        UserDto userDto = new UserDto();
        userDto.setFirstName("Bruce");
        userDto.setLastName("Banner");

        //when / then
        assertThatExceptionOfType(UserAlreadyExistsException.class)
                .isThrownBy(() -> userFacade.createNewUserAndReturnItsId(userDto));
    }

    @Test
    void An_existing_user_can_be_deleted() {
        //when
        userFacade.deleteUserWithId(EXISTING_USER_ID);

        //then
        assertThat(userRepository.findById(EXISTING_USER_ID)).isEmpty();
    }

    @Test
    void An_existing_team_can_be_updated() {
        //given
        String newLogin = randomString();
        String userRole = "BUSINESS_ANALYST";
        UserDto dto = new UserDto();
        dto.setLogin(newLogin);
        dto.setUserRole(userRole);

        //when
        userFacade.updateUserWithId(EXISTING_USER_ID, dto);

        //then
        assertThat(userRepository.findById(EXISTING_USER_ID)).hasValueSatisfying(u -> {
            assertThat(u.getLogin()).isEqualTo(newLogin);
            assertThat(u.getUserRole()).isEqualTo(BUSINESS_ANALYST);
        });
    }

    private User createUser(long id, String firstName, String lastName, String login, UserRole userRole) {
        User user = new User();
        user.setId(id);
        UserName userName = new UserName();
        userName.setFirstName(firstName);
        userName.setLastName(lastName);
        user.setUserName(userName);
        user.setLogin(login);
        user.setPassword("DUMMY_PASSWORD");
        user.setUserRole(userRole);
        return user;
    }

    private static String randomString() {
        return UUID.randomUUID().toString();
    }
}
