package com.tim33.isa;

import com.tim33.isa.model.TipUsera;
import com.tim33.isa.model.User;
import com.tim33.isa.model.UserRole;
import com.tim33.isa.repository.UserRepository;
import com.tim33.isa.repository.UserRoleRepository;
import com.tim33.isa.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class Config {
    @Bean
    CommandLineRunner init(UserRepository userRepository, UserRoleRepository roleRepository, UserService userService) {
        return args -> {
            UserRole role = roleRepository.findByRole("OBICAN");
            if (role == null) {
                UserRole r = new UserRole();
                r.setRole("OBICAN");
                roleRepository.save(r);
            }

            role = roleRepository.findByRole("ADMIN_AK");
            if (role == null) {
                UserRole r = new UserRole();
                r.setRole("ADMIN_AK");
                roleRepository.save(r);
            }

            role = roleRepository.findByRole("ADMIN_HOTELA");
            if (role == null) {
                UserRole r = new UserRole();
                r.setRole("ADMIN_HOTELA");
                roleRepository.save(r);
            }

            role = roleRepository.findByRole("ADMIN_RCS");
            if (role == null) {
                UserRole r = new UserRole();
                r.setRole("ADMIN_RCS");
                roleRepository.save(r);
            }

            UserRole sysAdminRole = roleRepository.findByRole("SISTEM_ADMIN");
            if (sysAdminRole == null) {
                UserRole roleAdmin = new UserRole();
                roleAdmin.setRole("SISTEM_ADMIN");
                roleRepository.save(roleAdmin);
            }

            List<User> users = userRepository.findByRolesContains(sysAdminRole);

            if (users == null || users.size() == 0) {
                User user = new User();
                user.setEmail("vule97@gmail.com");
                user.setUsername("sysadmin");
                user.setPassword("sisica12");
                user.setIme("Admin");
                user.setPrezime("Adminic");
                user.setTip_korisnika(TipUsera.SISTEM_ADMIN);
                Set<UserRole> roles = new HashSet<>();
                roles.add(roleRepository.findByRole("SISTEM_ADMIN"));
                user.setRoles(roles);
                user.setConfirmed(true);

                userService.save(user);
            }
        };
    }
}
