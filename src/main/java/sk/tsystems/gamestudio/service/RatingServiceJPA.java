package sk.tsystems.gamestudio.service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;
import sk.tsystems.gamestudio.entity.Rating;

@Component
@Transactional
public class RatingServiceJPA implements RatingService {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void setRating(Rating r) {
		try {
			Rating db = (Rating) entityManager
					.createQuery("select r from Rating r where r.username = :username and r.game = :game")
					.setParameter("username", r.getUsername())
					.setParameter("game", r.getGame())
					.getSingleResult();
			db.setValue(r.getValue());
		} catch (NoResultException e) {
			e.printStackTrace();
			entityManager.persist(r);
		}
	}

	@Override
	public double getAverageRating() {
		// TODO Auto-generated method stub
		return 0;
	}
}