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
	public void setRating(Rating rating) {
		try {
			Rating db = (Rating) entityManager
					.createQuery("select r from Rating r where r.username = :username and r.game = :game")
					.setParameter("username", rating.getUsername())
					.setParameter("game", rating.getGame())
					.getSingleResult();
			db.setValue(rating.getValue());
		} catch (NoResultException e) {
			entityManager.persist(rating);
		}
	}

	@Override
	public double getAverageRating() {
		Object result = entityManager.createQuery("select trunc(avg(r.value), 1) from Rating r where r.username = :username and r.game = :game").getSingleResult();
		return result == null ? -1.0 : ((Double)result).doubleValue();
	}
}