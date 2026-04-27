package ino.placement.ui;

import ino.placement.entity.Student;
import ino.placement.service.AssessmentResultService;
import ino.placement.service.AssessmentTypeService;
import ino.placement.util.SessionUtil;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@PageTitle("Add Assessment | Placement Portal")
@Route(value = "add-marks", layout = MainLayout.class)
public class AddAssessmentView extends VerticalLayout {

    public AddAssessmentView(AssessmentResultService service,
                             AssessmentTypeService typeService) {

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        getStyle().set("background-color", "#f3f5f7");

        Student user = SessionUtil.getUser();
        if (user == null) {
            getUI().ifPresent(ui -> ui.navigate("login"));
            return;
        }

        VerticalLayout card = new VerticalLayout();
        card.setMaxWidth("450px");
        card.setPadding(false);
        card.setSpacing(false);
        card.addClassNames(
                LumoUtility.Background.BASE,
                LumoUtility.BorderRadius.LARGE,
                LumoUtility.BoxShadow.MEDIUM
        );

        VerticalLayout cardHeader = new VerticalLayout();
        cardHeader.setPadding(true);
        cardHeader.getStyle()
                .set("background", "linear-gradient(to right, #667eea, #764ba2)")
                .set("border-radius", "12px 12px 0 0")
                .set("color", "white");

        H3 title = new H3("Add New Assessment");
        title.getStyle().set("margin", "0");

        cardHeader.add(title, new Span("Record your latest performance scores"));
        cardHeader.setAlignItems(Alignment.CENTER);

        VerticalLayout formBody = new VerticalLayout();
        formBody.getStyle().set("padding", "2rem");
        formBody.setSpacing(true);

        // ✅ DYNAMIC TYPES FROM DB
        ComboBox<String> type = new ComboBox<>("Assessment Type");
        type.setItems(
                typeService.getAll().stream()
                        .map(t -> t.getName())
                        .toList()
        );
        type.setAllowCustomValue(true);
        type.setWidthFull();

        NumberField score = new NumberField("Score (0-100)");
        score.setMin(0);
        score.setMax(100);
        score.setWidthFull();

        Button submit = new Button("Save", VaadinIcon.CHECK.create());
        submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submit.setWidthFull();

        submit.addClickListener(e -> {

            if (type.isEmpty() || score.isEmpty()) {
                showError("Fill all fields");
                return;
            }

            double val = score.getValue();

            if (val < 0 || val > 100) {
                showError("Score must be 0-100");
                return;
            }

            try {
                // Save new type automatically if not exists
                typeService.addType(type.getValue().trim());

                service.saveOrUpdate(
                        user.getId(),
                        type.getValue().trim(),
                        val
                );

                Notification.show("Saved");

                type.clear();
                score.clear();

            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        formBody.add(type, score, submit);
        card.add(cardHeader, formBody);
        add(card);
    }

    private void showError(String msg) {
        Notification n = Notification.show(msg);
        n.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }
}