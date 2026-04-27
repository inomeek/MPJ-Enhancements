package ino.placement.config;

import ino.placement.entity.Role;
import ino.placement.entity.Student;
import ino.placement.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner init(StudentRepository repo) {
        return args -> {

            // RUN ONLY IF DB IS EMPTY
            if (repo.count() == 0) {

                Student admin = new Student();
                admin.setFullName("Professor");
                admin.setEmail("foyong.inomeek@gmail.com");
                admin.setPassword("inomeek");
                admin.setRole(Role.ADMIN);

                repo.save(admin);
            }
        };
    }
}