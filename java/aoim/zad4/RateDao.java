package aoim.zad4;

import org.hibernate.Session;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

public class RateDao {

    public void addRate(int groupId, int rate, String comment) {
        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            RateEntity rateEntity = new RateEntity();
            rateEntity.setGroup_id(groupId);
            rateEntity.setRate(rate);
            rateEntity.setDate(LocalDateTime.now());
            rateEntity.setComment(comment);

            session.save(rateEntity);
            session.getTransaction().commit();
            session.close();

            System.out.println("Added rate " + rate + " to group " + groupId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int rateCount(int groupId) {
        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            String hql = "select count(*) from RateEntity where group_id=:groupId";
            Query query = session.createQuery(hql);
            query.setParameter("groupId", groupId);
            Long result = (Long) query.getSingleResult();
            session.getTransaction().commit();
            session.close();
            return result.intValue();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double rateAvg(int groupId) {
        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            String hql = "select avg(rate) from RateEntity where group_id=:groupId";
            Query query = session.createQuery(hql);
            query.setParameter("groupId", groupId);
            Double result = (Double) query.getSingleResult();
            session.getTransaction().commit();
            session.close();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<RateEntity> getRates() {
        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            String hql = "from RateEntity";
            Query query = session.createQuery(hql);
            List<RateEntity> rates = (List<RateEntity>) query.getResultList();
            session.getTransaction().commit();
            session.close();
            return rates;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
