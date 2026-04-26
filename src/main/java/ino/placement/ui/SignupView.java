package ino.placement.ui;

import ino.placement.entity.Student;
import ino.placement.service.StudentService;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Signup | Placement Readiness")
@Route("signup")
public class SignupView extends VerticalLayout {

    public SignupView(StudentService service) {

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        getStyle().set("background", "linear-gradient(to right, #667eea, #764ba2)");

        VerticalLayout card = new VerticalLayout();
        card.setWidth("420px");
        card.setPadding(true);
        card.setSpacing(true);
        card.setAlignItems(Alignment.CENTER);

        card.getStyle()
                .set("background", "white")
                .set("border-radius", "16px")
                .set("box-shadow", "0 15px 35px rgba(0,0,0,0.2)")
                .set("padding", "30px");

        H2 title = new H2("Create Account");

        TextField name = new TextField("Full Name");
        name.setPrefixComponent(VaadinIcon.USER.create());
        name.setWidthFull();

        TextField email = new TextField("Email");
        email.setPrefixComponent(VaadinIcon.ENVELOPE.create());
        email.setWidthFull();

        // ✅ PASSWORD FIELD
        PasswordField password = new PasswordField("Password");
        password.setPrefixComponent(VaadinIcon.LOCK.create());
        password.setWidthFull();
        password.setRevealButtonVisible(true);

        // ✅ CONFIRM PASSWORD FIELD
        PasswordField confirmPassword = new PasswordField("Confirm Password");
        confirmPassword.setPrefixComponent(VaadinIcon.LOCK.create());
        confirmPassword.setWidthFull();
        confirmPassword.setRevealButtonVisible(true);

        TextField dept = new TextField("Department");
        dept.setWidthFull();

        TextField batch = new TextField("Batch");
        batch.setWidthFull();

        Button register = new Button("Register", e -> {
            try {

                // 🔒 VALIDATION: Empty fields
                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    showError("Please fill all required fields");
                    return;
                }

                // 🔒 VALIDATION: Password match
                if (!password.getValue().equals(confirmPassword.getValue())) {
                    showError("Passwords do not match");
                    return;
                }

                Student s = new Student();
                s.setFullName(name.getValue());
                s.setEmail(email.getValue());
                s.setPassword(password.getValue());
                s.setDepartment(dept.getValue());
                s.setBatch(batch.getValue());

                service.save(s);

                Notification.show("Registered successfully!", 3000, Notification.Position.TOP_CENTER);

                // ✅ Clear fields after success
                name.clear();
                email.clear();
                password.clear();
                confirmPassword.clear();
                dept.clear();
                batch.clear();

            } catch (Exception ex) {
                showError("Error: " + ex.getMessage());
            }
        });

        register.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        register.setWidthFull();

        Button login = new Button("Back to Login",
                e -> getUI().ifPresent(ui -> ui.navigate("login")));
        login.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        card.add(title, name, email, password, confirmPassword, dept, batch, register, login);

        add(card);
    }

    private void showError(String message) {
        Notification n = Notification.show(message);
        n.addThemeVariants(NotificationVariant.LUMO_ERROR);
        n.setPosition(Notification.Position.TOP_CENTER);
        n.setDuration(3000);
    }
}
/*
1. Student Management
View all registered students
Search/filter students (by name, email)
View individual student profiles
Delete or deactivate student accounts
📊 2. Global Analytics
Total number of students
Average scores across all students
Overall placement readiness distribution
Performance trends (coding, aptitude, interview)
📈 3. Student Performance Monitoring
View detailed performance of any student
See latest assessment scores
Track progress over time
Identify weak and strong areas
📝 4. Assessment Management
Add new assessment types (if expandable)
View all submitted assessments
Edit or delete incorrect entries
Filter assessments by date/type
🎯 5. Readiness Overview
List of all students with readiness status
Filter students:
Ready / Not Ready
Weak in specific area
Highlight top-performing students
🚨 6. At-Risk Student Identification
Automatically detect students with low scores
Show “needs improvement” list
Focus on weak categories (e.g., aptitude < 40%)
💡 7. Suggestion Monitoring
View suggestions generated for each student
Analyze common weak areas across students
Improve suggestion logic based on trends
🔐 8. Admin Access Control
Separate login for admin
Restrict student-only features
Secure admin routes/views
10. Dashboard UI Features
Summary cards (Total Students, Avg Score, Ready %)
Tables with sorting & filtering
Simple charts (bar/line graphs)
*/