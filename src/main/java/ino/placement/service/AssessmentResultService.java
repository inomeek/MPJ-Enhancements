package ino.placement.service;

import ino.placement.entity.AssessmentResult;
import ino.placement.entity.Student;
import ino.placement.repository.AssessmentResultRepository;
import ino.placement.repository.StudentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentResultService {

    private final AssessmentResultRepository repo;
    private final StudentRepository studentRepo;

    public AssessmentResultService(AssessmentResultRepository repo,
                                   StudentRepository studentRepo) {
        this.repo = repo;
        this.studentRepo = studentRepo;
    }

    // ADMIN
    public List<AssessmentResult> getAll() {
        return repo.findAll();
    }

    // STUDENT
    public List<AssessmentResult> getAll(Long studentId) {
        return repo.findByStudentId(studentId);
    }

    public void saveOrUpdate(Long studentId, String type, double score) {

        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        AssessmentResult a = new AssessmentResult();
        a.setStudent(student);
        a.setAssessmentType(type);
        a.setScoreObtained(score);
        a.setMaxScore(100.0);

        repo.save(a);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}