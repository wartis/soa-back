package dao;

import model.SpaceMarine;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class SpaceMarineDAO {

    public Optional<SpaceMarine> getSpaceMarine(final Long id) {
        try (final Session session = HibernateUtil.getSessionFactory().openSession()) {
            final Transaction transaction = session.beginTransaction();
            final SpaceMarine spaceMarine = session.find(SpaceMarine.class, id);
            transaction.commit();

            return Optional.ofNullable(spaceMarine);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }



    public List<SpaceMarine> getAllSpaceMarines() {
        Transaction transaction = null;
        try (final Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            final List<SpaceMarine> spaceMarines = session.createQuery("from SpaceMarine").getResultList();
            transaction.commit();

            return spaceMarines;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

            return null;
        }
    }

    public long createSpaceMarine(final SpaceMarine spaceMarine) {
        Transaction transaction = null;
        try (final Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            final Long id = (Long) session.save(spaceMarine);
            transaction.commit();

            return id;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw ex;
        }
    }

    public boolean deleteSpaceMarine(Long id) {
        Transaction transaction = null;
        try (final Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            final SpaceMarine spaceMarine = session.find(SpaceMarine.class, id);
            spaceMarine.setChapter(null);
            if (spaceMarine != null) {
                session.delete(spaceMarine);
                session.flush();
                return true;
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return false;
    }

    public void updateSpaceMarine(final SpaceMarine spaceMarine) {
        Transaction transaction = null;
        try (final Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.update(spaceMarine);
            transaction.commit();
        } catch (Exception ex){
            if (transaction != null) {
                transaction.rollback();
            }

            throw ex;
        }
    }
}
