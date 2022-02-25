package beans.forum;

import javax.ejb.Remote;
import entities.Comment;
import entities.Discussion;

//bean that manages log in
@Remote
public interface ForumBeanRemote {

	// insert a new discussion
	void insertDiscussion(Discussion discussion);

	// returns all the discussions
	Discussion[] getDiscussions();

	// return the specific discussion
	Discussion getSpecificDiscussion(int id);

	// return all the comments of a discussion
	Comment[] getComments(Discussion discussion);

	// insert a new comment
	void insertComment(Comment comment);
}
