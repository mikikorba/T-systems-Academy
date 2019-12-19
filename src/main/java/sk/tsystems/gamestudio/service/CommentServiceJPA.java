package sk.tsystems.gamestudio.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import sk.tsystems.gamestudio.entity.Comment;

@Component
@Transactional
public class CommentServiceJPA implements CommentService{
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addComment(Comment comment) {
		entityManager.persist(comment);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getComment(String game) {
//		JPQL jazyk - java persistence querry line
		return (List<Comment>)entityManager.createQuery(
				"select s from Comment s where s.game = :game ")
				.setParameter("game", game).setMaxResults(10).getResultList();
		}

}
