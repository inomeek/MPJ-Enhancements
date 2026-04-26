package ino.placement.ui;

import ino.placement.entity.Student;
import ino.placement.service.StudentService;
import ino.placement.util.SessionUtil;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;

@Route(value = "admin-students", layout = MainLayout.class)
public class AdminStudentsView extends VerticalLayout implements BeforeEnterObserver {

    private final StudentService studentService;
    private Grid<Student> grid = new Grid<>(Student.class);

    public AdminStudentsView(StudentService studentService) {

        this.studentService = studentService;

        setSizeFull();
        add(new H2("All Students"));

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

        grid.setColumns("id", "fullName", "email", "department", "batch", "cgpa");

        grid.addComponentColumn(student -> {

            Button delete = new Button("Delete", e -> {
                studentService.delete(student.getId());
                loadData();
            });

            return delete;

        }).setHeader("Actions");

        grid.setSizeFull();
    }

    private void loadData() {
        grid.setItems(studentService.getAllStudents());
    }
}