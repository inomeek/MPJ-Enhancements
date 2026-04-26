package ino.placement.ui;

import ino.placement.entity.Student;
import ino.placement.service.StudentService;
import ino.placement.service.ReadinessService;
import ino.placement.util.SessionUtil;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;

import java.util.List;

@Route(value = "admin", layout = MainLayout.class)
@PageTitle("Admin Dashboard")
public class AdminView extends VerticalLayout implements BeforeEnterObserver {

    private final StudentService studentService;
    private final ReadinessService readinessService;

    private Grid<Student> grid = new Grid<>(Student.class);

    public AdminView(StudentService studentService, ReadinessService readinessService) {

        this.studentService = studentService;
        this.readinessService = readinessService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        add(new H2("Admin Dashboard"));

        add(createSummaryCards());
        add(createSearchBar());

        setupGrid();
        loadStudents();

        add(grid);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (!SessionUtil.isAdmin()) {
            event.forwardTo("login");
        }
    }

    // =========================
    // 📊 SUMMARY CARDS
    // =========================
    private HorizontalLayout createSummaryCards() {

        List<Student> students = studentService.getAllStudents();

        int total = students.size();
        int ready = 0;

        for (Student s : students) {
            if (readinessService.evaluateStudent(s.getId())
                    .getStatus().equals("INTERVIEW_READY")) {
                ready++;
            }
        }

        VerticalLayout totalCard = createCard("Total Students", String.valueOf(total));
        VerticalLayout readyCard = createCard("Ready Students", String.valueOf(ready));

        HorizontalLayout layout = new HorizontalLayout(totalCard, readyCard);
        layout.setWidthFull();
        layout.setJustifyContentMode(JustifyContentMode.START);

        return layout;
    }

    private VerticalLayout createCard(String title, String value) {

        VerticalLayout card = new VerticalLayout(
                new Span(title),
                new H2(value)
        );

        card.setWidth("200px");

        card.getStyle()
                .set("background", "white")
                .set("padding", "20px")
                .set("border-radius", "10px")
                .set("box-shadow", "0 2px 6px rgba(0,0,0,0.1)");

        return card;
    }

    // =========================
    // 🔍 SEARCH BAR
    // =========================
    private HorizontalLayout createSearchBar() {

        TextField search = new TextField();
        search.setPlaceholder("Search by name / email...");
        search.setWidth("300px");

        // 🔥 LIVE SEARCH
        search.addValueChangeListener(e -> {
            String keyword = e.getValue();

            if (keyword == null || keyword.trim().isEmpty()) {
                loadStudents();
            } else {
                grid.setItems(studentService.search(keyword));
            }
        });

        Button clear = new Button("Clear", e -> {
            search.clear();
            loadStudents();
        });

        return new HorizontalLayout(search, clear);
    }

    // =========================
    // 📋 GRID
    // =========================
    private void setupGrid() {

        grid.removeAllColumns();

        grid.addColumn(Student::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Student::getFullName).setHeader("Name").setFlexGrow(1);
        grid.addColumn(Student::getEmail).setHeader("Email").setFlexGrow(1);
        grid.addColumn(Student::getDepartment).setHeader("Dept");
        grid.addColumn(Student::getBatch).setHeader("Batch");
        grid.addColumn(Student::getCgpa).setHeader("CGPA");

        // ✅ DELETE BUTTON
        grid.addComponentColumn(student -> {

            Button delete = new Button("Delete", e -> {
                studentService.delete(student.getId());
                loadStudents();
            });

            return delete;

        }).setHeader("Actions");

        // 🔥 IMPORTANT FIX (shows multiple rows)
        grid.setHeight("500px");
        grid.setWidthFull();
    }

    private void loadStudents() {
        grid.setItems(studentService.getAllStudents());
    }
}