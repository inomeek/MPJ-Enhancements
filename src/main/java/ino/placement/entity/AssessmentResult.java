package ino.placement.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class AssessmentResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    // ✅ STRING (dynamic types)
    private String assessmentType;

    private Double scoreObtained;
    private Double maxScore = 100.0;

    private LocalDate assessmentDate = LocalDate.now();

    public Long getId() { return id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public String getAssessmentType() { return assessmentType; }
    public void setAssessmentType(String assessmentType) { this.assessmentType = assessmentType; }

    public Double getScoreObtained() { return scoreObtained; }
    public void setScoreObtained(Double scoreObtained) { this.scoreObtained = scoreObtained; }

    public Double getMaxScore() { return maxScore; }
    public void setMaxScore(Double maxScore) { this.maxScore = maxScore; }

    public LocalDate getAssessmentDate() { return assessmentDate; }
}