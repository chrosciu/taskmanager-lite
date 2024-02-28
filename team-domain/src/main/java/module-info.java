module eu.chrost.taskmanager.team.domain {
    requires transitive eu.chrost.taskmanager.commons;

    exports eu.chrost.taskmanager.team.domain
            to eu.chrost.taskmanager.team.application, eu.chrost.taskmanager.infrastructure;
}