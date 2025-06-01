package org.mock.persistence.entity.repository;

import org.mock.persistence.entity.Player;

import java.util.List;

public interface IPlayerRepository {

    public List<Player> findAll();

    public Player findById(Long id);

    public void save(Player player);

    public void deleteById(Long id);
}
