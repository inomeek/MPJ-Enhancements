package ino.placement.repository;

import ino.placement.entity.AssessmentResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentResultRepository extends JpaRepository<AssessmentResult, Long> {

    // ✅ Existing
    List<AssessmentResult> findByStudentId(Long studentId);

    // ✅ ADD THIS METHOD (FIX)
    List<AssessmentResult> findByStudentIdOrderByAssessmentDate(Long studentId);

    // ✅ Latest per type
    AssessmentResult findTopByStudent_IdAndAssessmentTypeOrderByAssessmentDateDesc(
            Long studentId,
            String assessmentType
    );
}