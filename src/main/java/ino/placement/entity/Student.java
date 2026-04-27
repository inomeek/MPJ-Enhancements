package ino.placement.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String email;

    private String password;
    private String department;
    private String batch;
    private Double cgpa = 0.0;

    @ElementCollection
    private List<String> skills;

    @Enumerated(EnumType.STRING)
    private Role role = Role.STUDENT; // DEFAULT ROLE

    // Getters & Setters
    public Long getId() { return id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getBatch() { return batch; }
    public void setBatch(String batch) { this.batch = batch; }

    public Double getCgpa() { return cgpa; }
    public void setCgpa(Double cgpa) { this.cgpa = cgpa; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}