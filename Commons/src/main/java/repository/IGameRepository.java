package repository;

import domain.Game;

public interface IGameRepository {
    Game findOne(Integer gameID);
    void add(Game g);
    void update(Game g);
}
