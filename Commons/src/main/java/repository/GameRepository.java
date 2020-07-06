package repository;

import domain.Game;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameRepository implements IGameRepository {

    public GameRepository() {
        System.out.println("creating game repo");
    }

    @Override
    public Game findOne(Integer gameID) {
        try (Session session = JdbcUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String queryString = "from Game as g where g.id = ?";
                List<Game> game = session.createQuery(queryString, Game.class)
                        .setParameter(0, gameID)
                        .list();
                tx.commit();
                if (game.size() == 1)
                    return game.get(0);
                else return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(Game g) {
        try (Session session = JdbcUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Game game = (Game) session.load(Game.class, g.getId());
                game.setCurrentPlayer(g.getCurrentPlayer());
                game.setCurrentConfiguration(g.getCurrentConfiguration());
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Game g) {
        try (Session session = JdbcUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(g);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
