package com.medicloud.config;

import com.medicloud.superadmin.entity.Admin;
import com.medicloud.superadmin.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Seeds a default super-admin on first startup when no admin exists.
 * Change the default credentials immediately in production!
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.setEmail("admin@medicloud.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            adminRepository.save(admin);
            log.warn("Default super-admin created — email: admin@medicloud.com / password: admin123 — CHANGE IN PRODUCTION!");
        }
    }
}
