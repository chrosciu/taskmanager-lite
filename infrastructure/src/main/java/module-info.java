module eu.chrost.taskmanager.infrastructure {
    requires eu.chrost.taskmanager.team.domain;
    requires eu.chrost.taskmanager.team.application;
    requires eu.chrost.taskmanager.user.domain;
    requires eu.chrost.taskmanager.user.application;

    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires spring.beans;
    requires spring.data.commons;
    requires spring.context;
    requires spring.web;
    requires spring.tx;
}