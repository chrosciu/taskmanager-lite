package eu.chrost.taskmanager;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

//source: https://blog.malt.engineering/enforcing-dependency-rules-between-maven-modules-using-junit-3c04bca179f4
class MavenDependencyVerifier {
    // Needed resources are searched starting from the current source file. This allows for launching
    // tests from any directory, which is especially useful when run from an IDE.
    private static final Path testClassesDirectory = getTestClassesDirectory();
    private static final Path targetDirectory = testClassesDirectory.getParent();
    private static final Path moduleDirectory = targetDirectory.getParent();
    private static final Path projectRootDirectory = moduleDirectory.getParent();

    public static void givenModuleShouldNotHaveUnauthorizedDependencies(String module, Set<String> unauthorizedDependencies) {
        Model modulePom = readPom(projectRootDirectory.resolve(module));
        Set<String> moduleDependencies = modulePom.getDependencies().stream()
                .map(Dependency::getArtifactId)
                .collect(Collectors.toSet());
        moduleDependencies.retainAll(unauthorizedDependencies);
        assertThat(moduleDependencies)
                .withFailMessage("Unauthorized dependencies found for module '%s': %s", module, moduleDependencies)
                .isEmpty();
    }

    public static void givenModuleShouldBeDefined(String module) {
        assertThatNoException().
                as("Module '%s' should be defined", module)
                .isThrownBy(() -> {
            readPom(projectRootDirectory.resolve(module));
        });
    }

    private static Path getTestClassesDirectory() {
        try {
            return Paths.get(new URI("file://" + MavenDependencyVerifier.class.getClassLoader().getResource(".").getPath()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Model readPom(Path moduleDir) {
        try {
            return new MavenXpp3Reader().read(new FileReader(moduleDir.resolve("pom.xml").toFile()));
        } catch (IOException | XmlPullParserException e) {
            throw new RuntimeException(e);
        }
    }
}
