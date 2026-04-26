package ino.placement.ui;

import ino.placement.dto.ReadinessResponse;
import ino.placement.service.ReadinessService;
import ino.placement.util.SessionUtil;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;

@Route(value = "readiness", layout = MainLayout.class)
@PageTitle("Readiness")
public class ReadinessView extends VerticalLayout {

    public ReadinessView(ReadinessService service) {

        setSizeFull();
        setAlignItems(Alignment.CENTER);

        var user = SessionUtil.getUser();
        if (user == null) {
            getUI().ifPresent(ui -> ui.navigate("login"));
            return;
        }

        ReadinessResponse r = service.evaluateStudent(user.getId());

        add(new H2("Placement Readiness"));

        VerticalLayout card = new VerticalLayout();
        card.getStyle()
                .set("background", "white")
                .set("padding", "20px")
                .set("border-radius", "10px");

        card.add(
                new H4("Coding: " + r.getCodingScore()),
                new H4("Aptitude: " + r.getAptitudeScore()),
                new H4("Interview: " + r.getInterviewScore()), // ✅ FIXED
                new Hr(),
                new H3("Status: " + r.getStatus()),
                new Paragraph(r.getMessage())
        );

        add(card);
    }
}