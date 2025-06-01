package org.mock;

import org.mock.persistence.entity.Player;

import java.util.List;

public class DataProvider {

    public static List<Player> playerListMock() {

        System.out.println(" -> Obteniendo listado Player/Mock");

        return List.of(
                new Player(1L, "Lionel Messi", "Inter Miami", "Forward"),
                new Player(2L, "Cristiano Ronaldo", "Al Nassr", "Forward"),
                new Player(3L, "Neymar Jr.", "Al Hilal", "Forward"),
                new Player(4L, "Kylian Mbappé", "Paris Saint-Germain", "Forward"),
                new Player(5L, "Kevin De Bruyne", "Manchester City", "Midfielder"),
                new Player(6L, "Karim Benzema", "Al Ittihad", "Forward")
        );
    }

    public static Player playerMock() {

        System.out.println(" -> Obteniendo Player/Mock");

        return new Player(1L, "Lionel Messi", "Inter Miami", "Forward");
    }

    public static Player newPlayerMock() {

        System.out.println(" -> Creando nuevo Player/Mock");

        return new Player(7L, "Arturo Vidal", "Colo-Colo", "Midfielder");
    }
}
