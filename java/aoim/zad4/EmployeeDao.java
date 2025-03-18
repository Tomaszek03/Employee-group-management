package aoim.zad4;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {

    public void addEmployee(String name, String surname, EmployeeCondition state, int year, double salary, int groupId) {
        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            EmployeeEntity employee = new EmployeeEntity();
            employee.setName(name);
            employee.setSurname(surname);
            employee.setState(state);
            employee.setYearOfBirth(year);
            employee.setSalary(salary);
            employee.setIdGroup(groupId);

            session.save(employee);
            session.getTransaction().commit();
            System.out.println("Saved employee: " + employee.getEmployeeId());
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void removeEmployee(int id) {
        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            String hql = "DELETE FROM EmployeeEntity WHERE employeeId = :employeeId";
            Query query = session.createQuery(hql);
            query.setParameter("employeeId", id);

            int deletedRows = query.executeUpdate();
            System.out.println("Removed " + deletedRows + " rows");
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<EmployeeEntity> getAllEmployees(int groupId) {
        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            Criteria criteria = session.createCriteria(EmployeeEntity.class);
            criteria.add(Restrictions.eq("idGroup", groupId));
            List<EmployeeEntity> employees = criteria.list();

            session.getTransaction().commit();
            session.close();

            return employees;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void truncateEmployees(int groupId) {
        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            String hql = "DELETE FROM EmployeeEntity WHERE idGroup = :idGroup";
            Query query = session.createQuery(hql);
            query.setParameter("idGroup", groupId);
            query.executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getEmployeeId(String name, String surname, int year) {
        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            String hql = "FROM EmployeeEntity WHERE name = :name AND surname = :surname AND yearOfBirth = :year";
            Query query = session.createQuery(hql);
            query.setParameter("name", name);
            query.setParameter("surname", surname);
            query.setParameter("year", year);

            EmployeeEntity employee = (EmployeeEntity) query.uniqueResult();
            session.getTransaction().commit();
            session.close();
            return employee.getEmployeeId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Employee> searchEmployees(String text, int groupId) {
        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            String hql = "SELECT e FROM EmployeeEntity e WHERE (e.name LIKE :name OR e.surname LIKE :surname) AND e.idGroup = :idGroup";
            Query query = session.createQuery(hql);
            query.setParameter("name", "%" + text + "%");
            query.setParameter("surname", "%" + text + "%");
            query.setParameter("idGroup", groupId);
            List<EmployeeEntity> employees = query.list();
            List<Employee> foundEmployees = new ArrayList<Employee>();
            for(EmployeeEntity employee : employees) {
                Employee found = new Employee(employee.getName(),employee.getSurname(),employee.getStateEnum(),employee.getYearOfBirth(),employee.getSalary());
                foundEmployees.add(found);
            }
            session.getTransaction().commit();
            session.close();

            return foundEmployees;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void changeCondition(Employee employee, EmployeeCondition condition, int groupId) {
        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            int employeeId = getEmployeeId(employee.getImie(),employee.getNazwisko(),employee.getRok_urodzenia());

            String hql = "UPDATE EmployeeEntity e SET e.state=:condition WHERE e.idGroup = :idGroup AND e.employeeId=:employeeId";
            Query query = session.createQuery(hql);
            query.setParameter("condition", condition);
            query.setParameter("idGroup", groupId);
            query.setParameter("employeeId", employeeId);
            query.executeUpdate();
            session.getTransaction().commit();
            session.close();
            System.out.println("Changed condition to " + condition);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeSalary(Employee employee, double salary, int groupId) {
        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            int employeeId = getEmployeeId(employee.getImie(),employee.getNazwisko(),employee.getRok_urodzenia());

            String hql = "UPDATE EmployeeEntity e SET e.salary=:salary WHERE e.idGroup = :idGroup AND e.employeeId=:employeeId";
            Query query = session.createQuery(hql);
            query.setParameter("salary", salary);
            query.setParameter("idGroup", groupId);
            query.setParameter("employeeId", employeeId);
            query.executeUpdate();
            session.getTransaction().commit();
            session.close();
            System.out.println("Changed salary to " + salary);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<EmployeeEntity> getEmployees() {
        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            String hql = "from EmployeeEntity";
            Query query = session.createQuery(hql);
            List<EmployeeEntity> employees = query.list();
            session.getTransaction().commit();
            session.close();
            return employees;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
