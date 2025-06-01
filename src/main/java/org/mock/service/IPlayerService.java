package org.mock.service;

import org.mock.persistence.entity.Player;

import java.util.List;

public interface IPlayerService {

    public List<Player> findAll();

    public Player findById(Long id);

    public void save(Player player);

    public void deleteById(Long id);
}
