package org.mock;

import org.mock.persistence.entity.Player;
import org.mock.persistence.entity.repository.PlayerRepositoryImpl;
import org.mock.service.PlayerServiceImpl;

public class Main {
    public static void main(String[] args) {

        PlayerRepositoryImpl playerRepository = new PlayerRepositoryImpl();
        PlayerServiceImpl playerService = new PlayerServiceImpl(playerRepository);

        // GET ALL
        System.out.println(playerService.findAll());

        // GET BY ID
        System.out.println(playerService.findById(1L));

        // DELETE BY ID
        playerService.deleteById(1L);
        System.out.println(playerService.findAll());

        // SAVE - GUARDAR
        Player player = new Player(7L, "Erling Haaland", "Manchester City", "Forward");
        playerService.save(player);
        System.out.println(playerService.findAll());
    }
}