package sk.tsystems.gamestudio.service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import sk.tsystems.gamestudio.entity.Player;

@Component
@Transactional
public class RegisterServiceJPA implements RegisterService{
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addPlayer(Player player) {
		try {
			Player db = (Player) entityManager
					.createQuery("select s from Player s where s.name = :name and s.pass = :pass")
					.setParameter("name", player.getName())
					.setParameter("pass", player.getPass())
					.getSingleResult();
			db.setName((player.getName()));
			db.setPass((player.getPass()));
		} catch (NoResultException e) {
			entityManager.persist(player);
		}
	}

	@Override
	public Player getRegister() {
		Object result = entityManager.createQuery(
				"select s from Player s where s.name = :name and s.pass = :pass")
				.setParameter("name", "KM" )
				.setParameter("pass", "ooo" )
				.getSingleResult();
		return (Player)result;
	}
}
