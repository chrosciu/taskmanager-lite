package eu.chrost.taskmanager;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static eu.chrost.taskmanager.MavenDependencyVerifier.givenModuleShouldBeDefined;
import static eu.chrost.taskmanager.MavenDependencyVerifier.givenModuleShouldNotHaveUnauthorizedDependencies;

class ModuleDependencyTests {
    @Test
    void teamDomainModuleShouldNotIncludeAnyUserRelatedModules() {
        givenModuleShouldNotHaveUnauthorizedDependencies("team-domain", Set.of("user-domain", "user-application"));
    }

    @Test
    void teamDomainModuleShouldBeDefined() {
        givenModuleShouldBeDefined("team-domain");
    }
}
