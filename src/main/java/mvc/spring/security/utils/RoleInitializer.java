package mvc.spring.security.utils;

import mvc.spring.security.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner {
    private final RoleService roleService;

    @Autowired
    public RoleInitializer(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleService.findRoleByName("ROLE_ADMIN") == null) {
            roleService.saveRole("ROLE_ADMIN");
        }
        if (roleService.findRoleByName("ROLE_USER") == null) {
            roleService.saveRole("ROLE_USER");
        }
    }
}