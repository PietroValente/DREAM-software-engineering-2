package beans.forum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import entities.Comment;
import entities.Discussion;
import util.HibernateUtil;
import util.VARIABLES;

//bean that manages forum
@Stateful
public class ForumBean implements ForumBeanRemote {

	@PersistenceContext(unitName = "first_unit")
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("first_unit");
	@SuppressWarnings("unused")
	private EntityManager manager = emf.createEntityManager();

	public ForumBean() {
	}

	// insert a new discussion
	@Override
	public void insertDiscussion(Discussion discussion) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dream", VARIABLES.databaseUser,
					VARIABLES.databasePassword);
			Statement statement = c.createStatement();
			statement.executeUpdate(
					"INSERT into discussion (author,subject,description) values(" + discussion.getAuthor() + ",'"
							+ discussion.getSubject() + "','" + discussion.getDescription() + "');");
			c.close();
			transaction.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// returns all the discussions
	@SuppressWarnings("unchecked")
	@Override
	public Discussion[] getDiscussions() {
		Transaction transaction = null;
		Discussion[] discussion;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			List<Integer> discussionList = manager
					.createNativeQuery("SELECT id FROM discussion ORDER BY timestamp DESC").getResultList();
			discussion = new Discussion[discussionList.size()];
			for (int i = 0; i < discussionList.size(); i++) {
				discussion[i] = session.get(Discussion.class, discussionList.get(i));
			}
			transaction.commit();
			session.close();
			return discussion;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// return the specific discussion
	@Override
	public Discussion getSpecificDiscussion(int id) {
		Discussion[] discussions = getDiscussions();
		for (int i = 0; i < discussions.length; i++) {
			if (discussions[i].getId() == id) {
				return discussions[i];
			}
		}
		return null;
	}

	// return all the comments of a discussion
	@SuppressWarnings("unchecked")
	@Override
	public Comment[] getComments(Discussion discussion) {
		Transaction transaction = null;
		Comment[] comment;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			List<Integer> commentList = manager.createNativeQuery(
					"SELECT id FROM comment WHERE discussion=" + discussion.getId() + " ORDER BY timestamp DESC")
					.getResultList();
			comment = new Comment[commentList.size()];
			for (int i = 0; i < commentList.size(); i++) {
				comment[i] = session.get(Comment.class, commentList.get(i));
			}
			transaction.commit();
			session.close();
			return comment;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// insert a new comment
	@Override
	public void insertComment(Comment comment) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dream", VARIABLES.databaseUser,
					VARIABLES.databasePassword);
			Statement statement = c.createStatement();
			statement.executeUpdate("INSERT into comment (author,discussion,text) values(" + comment.getAuthor() + ","
					+ comment.getDiscussion() + ",'" + comment.getText() + "');");
			c.close();
			transaction.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
