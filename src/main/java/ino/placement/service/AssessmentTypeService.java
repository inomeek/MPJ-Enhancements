package ino.placement.service;

import ino.placement.entity.AssessmentType;
import ino.placement.repository.AssessmentTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentTypeService {

    private final AssessmentTypeRepository repo;

    public AssessmentTypeService(AssessmentTypeRepository repo) {
        this.repo = repo;
    }

    public List<AssessmentType> getAll() {
        return repo.findAll();
    }

    public void addType(String name) {
        if (repo.findByName(name).isPresent()) return;

        AssessmentType t = new AssessmentType();
        t.setName(name);
        repo.save(t);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}