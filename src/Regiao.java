import java.util.ArrayList;
import java.util.List;

import javax.management.relation.Role;

/**
 * Class Regiao - a Regiao in an adventure game.
 *
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * A "Regiao" represents one location in the scenery of the game. It is
 * connected to other Regiaos via exits. The exits are labelled north,
 * east, south, west. For each direction, the Regiao stores a reference
 * to the neighboring Regiao, or null if there is no exit in that direction.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Regiao {
    private List<Roles> listaRoles;
    private String nome;
    private Regiao northExit;
    private Regiao southExit;
    private Regiao eastExit;
    private Regiao westExit;

    /**
     * Create a Regiao described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * 
     * @param description The Regiao's description.
     */
    public Regiao(String nome) {
        this.nome = nome;
        this.listaRoles = new ArrayList<>();
    }

    /**
     * Define the exits of this Regiao. Every direction either leads
     * to another Regiao or is null (no exit there).
     * 
     * @param north The north exit.
     * @param east  The east east.
     * @param south The south exit.
     * @param west  The west exit.
     */
    public void setExits(Regiao north, Regiao east, Regiao south, Regiao west) {
        if (north != null) {
            northExit = north;
        }
        if (east != null) {
            eastExit = east;
        }
        if (south != null) {
            southExit = south;
        }
        if (west != null) {
            westExit = west;
        }
    }

    public List<Roles> getListaRoles() {
        return listaRoles;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Regiao getNorthExit() {
        return northExit;
    }

    public void setNorthExit(Regiao northExit) {
        this.northExit = northExit;
    }

    public Regiao getSouthExit() {
        return southExit;
    }

    public void setSouthExit(Regiao southExit) {
        this.southExit = southExit;
    }

    public Regiao getEastExit() {
        return eastExit;
    }

    public void setEastExit(Regiao eastExit) {
        this.eastExit = eastExit;
    }

    public Regiao getWestExit() {
        return westExit;
    }

    public void setWestExit(Regiao westExit) {
        this.westExit = westExit;
    }

    public void setRoles(Roles role) {
        listaRoles.add(role);
    }

    /**
     * @return The description of the Regiao.
     */
    public String getNome() {
        return nome;
    }

}
