package ino.placement.service;

import ino.placement.entity.AssessmentResult;
import ino.placement.repository.AssessmentResultRepository;
import ino.placement.dto.ReadinessResponse;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadinessService {

    private final AssessmentResultRepository repo;

    public ReadinessService(AssessmentResultRepository repo) {
        this.repo = repo;
    }

    public ReadinessResponse evaluateStudent(Long studentId) {

        List<AssessmentResult> list = repo.findByStudentId(studentId);

        double coding = 0;
        double aptitude = 0;
        double interview = 0;

        for (AssessmentResult a : list) {

            String type = a.getAssessmentType();

            if (type == null) continue;

            type = type.trim().toLowerCase();

            switch (type) {

                case "coding":
                    coding = a.getScoreObtained();
                    break;

                case "aptitude":
                    aptitude = a.getScoreObtained();
                    break;

                case "interview":
                    interview = a.getScoreObtained();
                    break;
            }
        }

        double avg = (coding + aptitude + interview) / 3;

        ReadinessResponse r = new ReadinessResponse();
        r.setCodingScore((int) coding);
        r.setAptitudeScore((int) aptitude);
        r.setInterviewScore((int) interview);

        if (avg >= 75) {
            r.setStatus("INTERVIEW_READY");
            r.setMessage("Ready for placements");
        } else if (avg >= 50) {
            r.setStatus("ALMOST_READY");
            r.setMessage("Needs improvement");
        } else {
            r.setStatus("NOT_READY");
            r.setMessage("Focus on basics");
        }

        return r;
    }
}