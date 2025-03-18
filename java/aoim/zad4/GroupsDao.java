package aoim.zad4;

import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public class GroupsDao {

    public void addGroup(String name, int capacity) {
        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            GroupsEntity group = new GroupsEntity();
            group.setGroup_name(name);
            group.setGroup_capacity(capacity);

            String hql = "from GroupsEntity where group_name=:group_name";
            Query query = session.createQuery(hql);
            query.setParameter("group_name", name);
            List<GroupsEntity> groupExists = query.getResultList();

            if(!groupExists.isEmpty()) {
                System.out.println("Group " + name + " already exists");
                session.close();
            }
            else {
                session.save(group);
                session.getTransaction().commit();
                System.out.println("Created group: " + group.getGroup_name());
                session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteGroup(String name) {
        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            EmployeeDao employeeDao = new EmployeeDao();
            employeeDao.truncateEmployees(searchGroupId(name));

            String hql = "DELETE FROM GroupsEntity WHERE group_name=:group_name";
            Query query = session.createQuery(hql);
            query.setParameter("group_name", name);
            query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("Deleted group: " + name);
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<GroupsEntity> getGroups() {
        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            String hql = "from GroupsEntity";
            Query query = session.createQuery(hql);
            List<GroupsEntity> groups = query.getResultList();
            session.getTransaction().commit();
            session.close();
            return groups;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int searchGroupId(String name) {
        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            String hql = "from GroupsEntity where group_name=:group_name";
            Query query = session.createQuery(hql);
            query.setParameter("group_name", name);
            GroupsEntity group = (GroupsEntity) query.getSingleResult();
            if(group != null) {
                return group.getGroup_id();
            }
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
        }
        return 0;
    }

    public ClassEmployee getGroup(String group_name) {
        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            String hql = "from GroupsEntity where group_name=:group_name";
            Query query = session.createQuery(hql);
            query.setParameter("group_name", group_name);
            GroupsEntity group = (GroupsEntity) query.getSingleResult();
            session.getTransaction().commit();
            session.close();

            ClassEmployee g = new ClassEmployee(group.getGroup_name(), group.getGroup_capacity());

            return g;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int calculateGroup(int groupId) {
        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            String hql = "SELECT COUNT(*) FROM EmployeeEntity WHERE idGroup=:idGroup";
            Query query = session.createQuery(hql);
            query.setParameter("idGroup", groupId);
            Long count = (Long) query.getSingleResult();
            session.getTransaction().commit();
            session.close();
            return count.intValue();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
