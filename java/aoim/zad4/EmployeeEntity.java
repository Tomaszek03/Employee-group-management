package aoim.zad4;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "employee_id", nullable = false)
    private int employeeId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private EmployeeCondition state;

    @Column(name = "year_of_birth", nullable = false)
    private int yearOfBirth;

    @Column(name = "salary", nullable = false)
    private double salary;

    @Column(name =  "id_group", nullable = false)
    private int idGroup;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getState() {
        return state.toString();
    }

    public EmployeeCondition getStateEnum() {
        return state;
    }

    public void setState(EmployeeCondition state) {
        this.state = state;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }
}
