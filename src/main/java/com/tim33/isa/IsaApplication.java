package com.tim33.isa;

import com.tim33.isa.controller.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"controller", "dto.filter", "model", "repository", "service"})
public class IsaApplication {

    public static void main(String[] args) {
        SpringApplication.run(IsaApplication.class, args);
    }

}
