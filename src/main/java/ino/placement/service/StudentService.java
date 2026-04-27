package ino.placement.service;

import ino.placement.entity.Student;
import ino.placement.entity.Role;
import ino.placement.repository.StudentRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    // Get all students 
    public List<Student> getAllStudents() {
        return repo.findAll()
                .stream()
                .filter(s -> s.getRole() == Role.STUDENT)
                .collect(Collectors.toList());
    }

    // Search students by name or email
    public List<Student> search(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllStudents();
        }

        String lower = keyword.toLowerCase();

        return repo.findAll()
                .stream()
                .filter(s -> s.getRole() == Role.STUDENT)
                .filter(s ->
                        (s.getFullName() != null && s.getFullName().toLowerCase().contains(lower)) ||
                        (s.getEmail() != null && s.getEmail().toLowerCase().contains(lower))
                )
                .collect(Collectors.toList());
    }

    // Get by ID
    public Optional<Student> getById(Long id) {
        return repo.findById(id);
    }

    // Save student
    public Student save(Student s) {
        return repo.save(s);
    }

    // Delete student
    public void delete(Long id) {
        repo.deleteById(id);
    }

    // Check existence
    public boolean existsById(Long id) {
        return repo.existsById(id);
    }

    // Update student
    public Student updateStudent(Student existingStudent, Student updatedData) {
        existingStudent.setFullName(updatedData.getFullName());
        existingStudent.setDepartment(updatedData.getDepartment());
        existingStudent.setBatch(updatedData.getBatch());
        existingStudent.setCgpa(updatedData.getCgpa());

        return repo.save(existingStudent);
    }
}