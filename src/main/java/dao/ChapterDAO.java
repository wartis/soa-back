package dao;

import model.Chapter;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class ChapterDAO {

    public List<Chapter> getAll() {
        Transaction transaction = null;
        try (final Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            final List<Chapter> chapters = session.createQuery("from Chapter").getResultList();
            transaction.commit();

            return chapters;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

            return null;
        }
    }

    public Optional<Chapter> getChapter(final Long id) {
        try (final Session session = HibernateUtil.getSessionFactory().openSession()) {
            final Transaction transaction = session.beginTransaction();
            final Chapter chapter = session.find(Chapter.class, id);
            transaction.commit();

            return Optional.ofNullable(chapter);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public void updateChapter(final Chapter chapter) {
        Transaction transaction = null;
        try (final Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.update(chapter);
            transaction.commit();
        } catch (Exception ex){
            if (transaction != null) {
                transaction.rollback();
            }

            throw ex;
        }
    }
}
