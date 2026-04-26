package ino.placement.ui;

import ino.placement.entity.AssessmentResult;
import ino.placement.service.AssessmentResultService;
import ino.placement.util.SessionUtil;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;

import java.util.List;

@Route(value = "admin-assessments", layout = MainLayout.class)
public class AdminAssessmentView extends VerticalLayout implements BeforeEnterObserver {

    private final AssessmentResultService service;
    private Grid<AssessmentResult> grid = new Grid<>(AssessmentResult.class);

    public AdminAssessmentView(AssessmentResultService service) {

        this.service = service;

        setSizeFull();
        add(new H2("All Assessments"));

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

        grid.addColumn(AssessmentResult::getId).setHeader("ID");
        grid.addColumn(a -> a.getStudent() != null ? a.getStudent().getFullName() : "N/A")
                .setHeader("Student");
        grid.addColumn(AssessmentResult::getAssessmentType).setHeader("Type");
        grid.addColumn(AssessmentResult::getScoreObtained).setHeader("Score");
        grid.addColumn(AssessmentResult::getAssessmentDate).setHeader("Date");

        grid.addComponentColumn(a -> {

            Button delete = new Button("Delete", e -> {
                service.delete(a.getId());
                loadData();
            });

            return delete;

        }).setHeader("Actions");

        grid.setSizeFull();
    }

    private void loadData() {

        List<AssessmentResult> list = service.getAll();

        if (list == null || list.isEmpty()) {
            add(new Paragraph("No assessments found"));
        }

        grid.setItems(list);
    }
}