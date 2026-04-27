package ino.placement.repository;

import ino.placement.entity.AssessmentResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentResultRepository extends JpaRepository<AssessmentResult, Long> {

    List<AssessmentResult> findByStudentId(Long studentId);
    List<AssessmentResult> findByStudentIdOrderByAssessmentDate(Long studentId);
    AssessmentResult findTopByStudent_IdAndAssessmentTypeOrderByAssessmentDateDesc(
            Long studentId,
            String assessmentType
    );
}