package domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Rounds")
public class Round implements Serializable {
    private Integer gameID;
    private Integer round;
    private String player;
    private String configuration;
    private Integer dice;

    public Round() {
    }

    public Round(Integer gameID, String player, Integer round) {
        this.gameID = gameID;
        this.player = player;
        this.round = round;
    }

    public Round(Integer gameID, Integer round, String player, String configuration, Integer dice) {
        this.gameID = gameID;
        this.round = round;
        this.player = player;
        this.configuration = configuration;
        this.dice = dice;
    }

    @Id
    @Column(name="gameID")
    public Integer getGameID() {
        return gameID;
    }

    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }

    @Id
    @Column(name="round")
    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    @Id
    @Column(name="player")
    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    @Column(name="configuration")
    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    @Column(name="dice")
    public Integer getDice() {
        return dice;
    }

    public void setDice(Integer dice) {
        this.dice = dice;
    }
}
