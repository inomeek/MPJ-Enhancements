package ino.placement.repository;

import ino.placement.entity.AssessmentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssessmentTypeRepository extends JpaRepository<AssessmentType, Long> {

    Optional<AssessmentType> findByName(String name);
}