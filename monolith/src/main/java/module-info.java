module eu.chrost.taskmanager {
    requires eu.chrost.taskmanager.team.domain;
    requires eu.chrost.taskmanager.team.application;
    requires eu.chrost.taskmanager.user.domain;
    requires eu.chrost.taskmanager.user.application;

    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
}