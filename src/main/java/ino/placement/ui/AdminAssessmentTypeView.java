package ino.placement.ui;

import ino.placement.entity.AssessmentType;
import ino.placement.service.AssessmentTypeService;
import ino.placement.util.SessionUtil;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

@Route(value = "admin-types", layout = MainLayout.class)
public class AdminAssessmentTypeView extends VerticalLayout implements BeforeEnterObserver {

    private final AssessmentTypeService service;
    private Grid<AssessmentType> grid = new Grid<>(AssessmentType.class);

    public AdminAssessmentTypeView(AssessmentTypeService service) {

        this.service = service;

        add(new H2("Assessment Types"));

        TextField input = new TextField("New Type");
        Button add = new Button("Add", e -> {
            service.addType(input.getValue().trim());
            input.clear();
            load();
        });

        setupGrid();
        load();

        add(input, add, grid);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (!SessionUtil.isAdmin()) {
            event.forwardTo("login");
        }
    }

    private void setupGrid() {

        grid.removeAllColumns();

        grid.addColumn(AssessmentType::getName).setHeader("Type");

        grid.addComponentColumn(t -> {
            Button delete = new Button("Delete", e -> {
                service.delete(t.getId());
                load();
            });
            return delete;
        });
    }

    private void load() {
        grid.setItems(service.getAll());
    }
}