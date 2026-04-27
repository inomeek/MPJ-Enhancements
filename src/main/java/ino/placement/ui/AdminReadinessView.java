package ino.placement.ui;

import ino.placement.entity.Student;
import ino.placement.dto.ReadinessResponse;
import ino.placement.service.StudentService;
import ino.placement.service.ReadinessService;
import ino.placement.util.SessionUtil;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;

import java.util.List;

@Route(value = "admin-readiness", layout = MainLayout.class)
@PageTitle("All Students Readiness")
public class AdminReadinessView extends VerticalLayout implements BeforeEnterObserver {

    private final StudentService studentService;
    private final ReadinessService readinessService;

    private Grid<Student> grid = new Grid<>(Student.class);

    public AdminReadinessView(StudentService studentService,
                              ReadinessService readinessService) {

        this.studentService = studentService;
        this.readinessService = readinessService;

        setSizeFull();
        add(new H2("Student Readiness Overview"));

        setupGrid();
        loadData();

        add(grid);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (!SessionUtil.isAdmin()) {
            event.forwardTo("login");
        }
    }

    private void setupGrid() {

        grid.removeAllColumns();

        grid.addColumn(Student::getFullName).setHeader("Name");
        grid.addColumn(Student::getEmail).setHeader("Email");

        grid.addColumn(student -> {
            ReadinessResponse r = readinessService.evaluateStudent(student.getId());
            return r.getCodingScore();
        }).setHeader("Coding");

        grid.addColumn(student -> {
            ReadinessResponse r = readinessService.evaluateStudent(student.getId());
            return r.getAptitudeScore();
        }).setHeader("Aptitude");

        grid.addColumn(student -> {
            ReadinessResponse r = readinessService.evaluateStudent(student.getId());
            return r.getInterviewScore(); 
        }).setHeader("Interview");

        grid.addColumn(student -> {
            ReadinessResponse r = readinessService.evaluateStudent(student.getId());
            return r.getStatus();
        }).setHeader("Status");

        grid.setSizeFull();
    }

    private void loadData() {
        List<Student> students = studentService.getAllStudents();
        grid.setItems(students);
    }
}