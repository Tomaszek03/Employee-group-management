package aoim.zad4;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Data {

    // INSERT INTO groups (group_name,group_capacity) VALUES ("Star Wars", 15), ("ZOO", 20), ("Pirates of the Caribbean", 10);

//    INSERT INTO employees (name, surname, state, year_of_birth, salary, id_group) VALUES
//        ("Luke", "Skywalker", "OBECNY", 1990, 15000, 1),
//        ("Obi-Wan", "Kenobi", "OBECNY", 1980, 14000, 1),
//        ("Darth", "Vader", "CHORY", 1985, 20000, 1),
//        ("Padme", "Amidala", "NIEOBECNY", 2000, 14500, 1),
//        ("Leia", "Organa", "OBECNY", 1990, 17500, 1),
//        ("Han", "Solo", "DELEGACJA", 1989, 12000, 1),
//        ("R2", "D2", "DELEGACJA", 2005, 10000, 1),
//        ("C", "3PO", "DELEGACJA", 1997, 15000, 1),
//        ("Kylo", "Ren", "CHORY", 2003, 34000, 1),
//        ("Lando", "Calrissian", "NIEOBECNY", 1989, 18700, 1),
//        ("Jack", "Sparrow", "DELEGACJA", 1750, 1000000, 3),
//        ("Hector", "Barbossa", "OBECNY", 1744, 760000, 3),
//        ("Elizabeth", "Swann", "NIEOBECNY", 1770, 250000, 3),
//        ("Davy", "Jones", "CHORY", 1678, 140000, 3);

    GroupsDao groupsDao = new GroupsDao();
    EmployeeDao employeeDao = new EmployeeDao();
    RateDao rateDao = new RateDao();

    public List<ClassEmployee> loadGroups() {
        List<GroupsEntity> groups = groupsDao.getGroups();
        List<ClassEmployee> groupsList = new ArrayList<>();
        for (GroupsEntity group : groups) {
            ClassEmployee g = new ClassEmployee(group.getGroup_name(), group.getGroup_capacity());
            groupsList.add(g);
        }
        return groupsList;
    }

    public List<Employee> loadEmployees(String groupName) {
        int groupId = groupsDao.searchGroupId(groupName);
        List<EmployeeEntity> employees = employeeDao.getAllEmployees(groupId);
        List<Employee> employeesList = new ArrayList<>();

        for (EmployeeEntity employee : employees) {
            Employee e = new Employee(employee.getName(),employee.getSurname(), employee.getStateEnum(),employee.getYearOfBirth(),employee.getSalary());
            employeesList.add(e);
        }

        return employeesList;
    }

    public void exportToCSV() {
        List<GroupsEntity> groups = groupsDao.getGroups();
        List<EmployeeEntity> employees = employeeDao.getEmployees();
        List<RateEntity> rates = rateDao.getRates();

        String file = "data.csv";

        try(OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            fileWriter.write("\uFEFF");

            fileWriter.append("Groups\n");
            fileWriter.append("group_id;group_name;group_capacity\n");
            for (GroupsEntity group : groups) {
                fileWriter.append(group.getGroup_id() + ";");
                fileWriter.append(group.getGroup_name() + ";");
                fileWriter.append(group.getGroup_capacity() + "\n");
            }

            fileWriter.append("\nEmployees\n");
            fileWriter.append("employee_id; name; surname; state; year_of_birth; salary; id_group\n");
            for (EmployeeEntity employee : employees) {
                fileWriter.append(employee.getEmployeeId() + ";");
                fileWriter.append(employee.getName() + ";");
                fileWriter.append(employee.getSurname() + ";");
                fileWriter.append(employee.getStateEnum() + ";");
                fileWriter.append(employee.getYearOfBirth() + ";");
                fileWriter.append(employee.getSalary() + ";");
                fileWriter.append(employee.getIdGroup() + "\n");
            }

            fileWriter.append("\nRates\n");
            fileWriter.append("rate_id; group_id; rate; date; comment\n");
            for (RateEntity rate : rates) {
                fileWriter.append(rate.getRate_id() + ";");
                fileWriter.append(rate.getGroup_id() + ";");
                fileWriter.append(rate.getRate() + ";");
                fileWriter.append(rate.getDate() + ";");
                fileWriter.append(rate.getComment() + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
