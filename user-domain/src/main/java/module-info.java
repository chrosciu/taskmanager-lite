module eu.chrost.taskmanager.user.domain {
    requires transitive eu.chrost.taskmanager.commons;

    exports eu.chrost.taskmanager.user.domain
            to eu.chrost.taskmanager.user.application, eu.chrost.taskmanager.infrastructure;
}