package ino.placement.repository;

import ino.placement.entity.Student;
import ino.placement.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    // ✅ ONLY STUDENTS
    List<Student> findByRole(Role role);

    // ✅ SEARCH
    List<Student> findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);
}