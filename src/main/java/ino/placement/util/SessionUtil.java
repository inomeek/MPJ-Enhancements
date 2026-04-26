package ino.placement.util;

import com.vaadin.flow.server.VaadinSession;
import ino.placement.entity.Student;
import ino.placement.entity.Role;

public class SessionUtil {

    private static final String USER = "loggedUser";

    public static void setUser(Student student) {
        VaadinSession.getCurrent().setAttribute(USER, student);
    }

    public static Student getUser() {
        return (Student) VaadinSession.getCurrent().getAttribute(USER);
    }

    public static boolean isAdmin() {
        Student user = getUser();
        return user != null && user.getRole() == Role.ADMIN;
    }

    public static void logout() {
        VaadinSession.getCurrent().setAttribute(USER, null);
    }
}