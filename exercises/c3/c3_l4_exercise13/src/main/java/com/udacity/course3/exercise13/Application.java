package com.udacity.course3.exercise13;

import com.udacity.course3.exercise13.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(MemberRepository memberRepository) {
        return args -> {
            // STEP 1: Define Member and MemberRepository first before changing this class


            // STEP 2: create a Member record


            // STEP 3: save the Member record


            // read the Member using memeber last name
        };
    }
}