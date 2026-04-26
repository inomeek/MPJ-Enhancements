package ino.placement.ui;

import ino.placement.entity.Student;
import ino.placement.entity.Role;
import ino.placement.util.SessionUtil;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@PageTitle("My Profile | Placement Portal")
@Route(value = "students", layout = MainLayout.class)
public class StudentView extends VerticalLayout {

    public StudentView() {

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        getStyle().set("background-color", "#f3f5f7");

        Student user = SessionUtil.getUser();
        if (user == null) {
            getUI().ifPresent(ui -> ui.navigate("login"));
            return;
        }

        VerticalLayout card = new VerticalLayout();
        card.setMaxWidth("600px");
        card.setPadding(false);
        card.setSpacing(false);
        card.addClassNames(
            LumoUtility.Background.BASE,
            LumoUtility.BorderRadius.LARGE,
            LumoUtility.BoxShadow.MEDIUM,
            LumoUtility.Margin.Top.XLARGE
        );

        VerticalLayout cardHeader = new VerticalLayout();
        cardHeader.setPadding(true);
        cardHeader.getStyle()
            .set("background", "linear-gradient(to right, #667eea, #764ba2)")
            .set("border-radius", "12px 12px 0 0")
            .set("color", "white");

        H2 name = new H2(user.getFullName());
        name.getStyle().set("margin", "0");

        // ✅ FIXED LABEL
        String label = user.getRole() == Role.ADMIN ? "Admin ID" : "Student ID";
        Span idSpan = new Span(label + ": #" + user.getId());

        cardHeader.add(name, idSpan);

        VerticalLayout cardBody = new VerticalLayout();
        cardBody.setPadding(true);
        cardBody.getStyle().set("padding", "2rem");
        cardBody.setSpacing(false);

        cardBody.add(
            createInfoRow(VaadinIcon.ENVELOPE, "Email Address", user.getEmail()),
            createInfoRow(VaadinIcon.ACADEMY_CAP, "Academic Department", user.getDepartment()),
            createInfoRow(VaadinIcon.CALENDAR, "Graduation Batch", user.getBatch()),
            createInfoRow(VaadinIcon.CHART_LINE, "Current CGPA", String.valueOf(user.getCgpa()))
        );

        card.add(cardHeader, cardBody);
        add(card);
    }

    private HorizontalLayout createInfoRow(VaadinIcon icon, String labelText, String valueText) {

        var iconComp = icon.create();
        iconComp.getStyle().set("color", "#764ba2");

        Span label = new Span(labelText);
        label.addClassNames(LumoUtility.FontSize.XSMALL);

        Span value = new Span(valueText != null ? valueText : "N/A");

        VerticalLayout textStack = new VerticalLayout(label, value);
        textStack.setSpacing(false);
        textStack.setPadding(false);

        HorizontalLayout row = new HorizontalLayout(iconComp, textStack);
        row.setWidthFull();

        return row;
    }
}