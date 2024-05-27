package eu.chrost.taskmanager;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class TaskManagerModulithTest {
    @Test
    void applicationModulesShouldObeyTheRules() {
        ApplicationModules modules = ApplicationModules.of(TaskManagerApplication.class);
        modules.verify();
    }
}
