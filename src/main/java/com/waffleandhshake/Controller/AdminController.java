package com.waffleandhshake.Controller;


import com.waffleandhshake.Entity.Admin;
import com.waffleandhshake.Repository.AdminRepository;
import com.waffleandhshake.Util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/public")
public class AdminController {

    private final AdminRepository adminRepository;
    private final JwtUtil jwtUtil;

    public AdminController(AdminRepository adminRepository, JwtUtil jwtUtil) {
        this.adminRepository = adminRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/auth/register")
    public String register(@RequestBody Admin admin) {
        if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
            return "Admin already exists!";
        }
        // Save password as plain text
        adminRepository.save(admin);
        return "Admin registered successfully!";
    }

    @PostMapping("/auth/login")
    public Map<String, String> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Compare plain text password directly
        if (!password.equals(admin.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(admin.getEmail());
        return Map.of("token", token);
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Welcome Admin! This is a protected route.";
    }
}
