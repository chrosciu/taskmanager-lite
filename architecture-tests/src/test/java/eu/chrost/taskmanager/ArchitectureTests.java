package eu.chrost.taskmanager;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "eu.chrost.taskmanager", importOptions = {ImportOption.DoNotIncludeTests.class})
class ArchitectureTests {

    @ArchTest
    static final ArchRule noClassesFromUserPackageExceptTeamControllerShouldDependOnClassesFromTeamPackage =
            noClasses()
                .that().resideInAPackage("eu.chrost.taskmanager.team")
                .and().doNotHaveSimpleName("TeamController")
            .should()
                .dependOnClassesThat().resideInAPackage("eu.chrost.taskmanager.user");

    @ArchTest
    static final ArchRule noClassesFromTeamPackageShouldDependOnClassesFromUserPackage =
        noClasses()
            .that().resideInAPackage("eu.chrost.taskmanager.user")
        .should()
            .dependOnClassesThat().resideInAPackage("eu.chrost.taskmanager.team");

    @ArchTest
    static final ArchRule noClassesFromTeamPackageExceptTeamFacadeAndTeamQueryRepositoryShouldBePublic =
            noClasses()
                .that().resideInAPackage("eu.chrost.taskmanager.team")
                .and().doNotHaveSimpleName("TeamFacade")
                .and().doNotHaveSimpleName("TeamQueryRepository")
            .should()
                .bePublic();

    @ArchTest
    static final ArchRule noClassesFromUserPackageExceptTeamFacadeAndTeamQueryRepositoryShouldBePublic =
            noClasses()
                .that().resideInAPackage("eu.chrost.taskmanager.user")
                .and().doNotHaveSimpleName("UserFacade")
                .and().doNotHaveSimpleName("UserQueryRepository")
            .should()
                .bePublic();

    @ArchTest
    static final ArchRule noClassesFromTeamOrUserPackageShouldDependOnClassesFromRootPackage =
            noClasses()
                .that().resideInAPackage("eu.chrost.taskmanager.team")
                .or().resideInAPackage("eu.chrost.taskmanager.user")
            .should()
                .dependOnClassesThat().resideInAPackage("eu.chrost.taskmanager");
}
