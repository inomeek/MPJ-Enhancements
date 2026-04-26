package ino.placement.ui;

import ino.placement.util.SessionUtil;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;

public class MainLayout extends AppLayout implements BeforeEnterObserver {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (SessionUtil.getUser() == null) {
            event.forwardTo("login");
        }
    }

    private void createHeader() {
        H3 logo = new H3("Placement System");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
        header.setWidthFull();
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }

    private void createDrawer() {

        boolean isAdmin = SessionUtil.isAdmin();

        VerticalLayout menu;

        if (isAdmin) {
            menu = new VerticalLayout(
                    new RouterLink("Admin Dashboard", AdminView.class),
                    new RouterLink("Students", AdminStudentsView.class),
                    new RouterLink("Assessments", AdminAssessmentView.class),
                    new RouterLink("Assessment Types", AdminAssessmentTypeView.class),
                    new RouterLink("My Profile", StudentView.class)
            );
        } else {
            menu = new VerticalLayout(
                    new RouterLink("Home", HomeView.class),
                    new RouterLink("My Profile", StudentView.class),
                    new RouterLink("Add Marks", AddAssessmentView.class),
                    new RouterLink("Readiness", ReadinessView.class),
                    new RouterLink("Analytics", AnalyticsView.class)
            );
        }

        Button logout = new Button("Logout", e -> {
            SessionUtil.logout();
            getUI().ifPresent(ui -> ui.navigate("login"));
        });

        logout.addThemeVariants(ButtonVariant.LUMO_ERROR);

        VerticalLayout drawer = new VerticalLayout(menu, logout);
        drawer.setSizeFull();
        drawer.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        addToDrawer(drawer);
    }
}