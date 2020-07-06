package domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="Games", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
public class Game implements Serializable {
    private Integer id;
    private String startConfiguration;
    private String currentConfiguration;
    private String player1;
    private String player2;
    private String player3;
    private String currentPlayer;

    public Game() {
    }

    public Game(Integer id, String startConfiguration, String currentConfiguration, String player1, String player2, String player3, String currentPlayer) {
        this.id = id;
        this.startConfiguration = startConfiguration;
        this.currentConfiguration = currentConfiguration;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.currentPlayer = currentPlayer;
    }

    @Column(name="startConfiguration")
    public String getStartConfiguration() {
        return startConfiguration;
    }

    public void setStartConfiguration(String startConfiguration) {
        this.startConfiguration = startConfiguration;
    }

    @Column(name="currentConfiguration")
    public String getCurrentConfiguration() {
        return currentConfiguration;
    }

    public void setCurrentConfiguration(String currentConfiguration) {
        this.currentConfiguration = currentConfiguration;
    }

    @Column(name="player1")
    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    @Column(name="player2")
    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    @Column(name="player3")
    public String getPlayer3() {
        return player3;
    }

    public void setPlayer3(String player3) {
        this.player3 = player3;
    }

    @Column(name="currentPlayer")
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Id
    @Column(name="id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



}