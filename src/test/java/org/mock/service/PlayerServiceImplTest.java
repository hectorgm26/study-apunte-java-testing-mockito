package org.mock.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mock.DataProvider;
import org.mock.persistence.entity.Player;
import org.mock.persistence.entity.repository.PlayerRepositoryImpl;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// Mockito solo trabaja con las dependencias y no con la clase que se esta teteando, y JUnit trabajara con la clase a testear
// En este caso se esta testrando PlayerServiceImpl, pero tiene como dependencia PlayerRepositoryImpl

@ExtendWith(MockitoExtension.class) // Forma comun de habilitar las anotaciones de Mockito
public class PlayerServiceImplTest {

    // 1era forma de inicializar mocks (la mas recomendada y comun)
    @Mock // Mock es para las dependencias, simulando un objeto que no es real
    private PlayerRepositoryImpl playerRepository;

    @InjectMocks // InjectMocks es para la clase que se esta testeando
    private PlayerServiceImpl playerService;

    /* FORMA ALTERNATIVA DE HABILITAR LAS ANOTACIONES DE MOCKITO
    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }
    */

    /* 2da forma de inicializar mocks - Siempre se colocan primero las implementaciones de las dependencias (no interfaces)
    private PlayerRepositoryImpl playerRepository;
    private PlayerServiceImpl playerService;

    @BeforeEach
    void init() {
        this.playerRepository = mock(PlayerRepositoryImpl.class);
        this.playerService = new PlayerServiceImpl(this.playerRepository);
    }
    */

    @Test
    public void testFindAll() {
        // Given

        /* 3era forma de inicializar mocks
        PlayerRepositoryImpl playerRepository = mock(PlayerRepositoryImpl.class);
        PlayerServiceImpl playerService = new PlayerServiceImpl(playerRepository);
        */

        // When
        // Primero definimos el comportamiento del mock (de la dependencia) con when
        when(playerRepository.findAll()).thenReturn(DataProvider.playerListMock());
        // Luego llamamos al metodo que queremos testear
        List<Player> result = playerService.findAll();

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Lionel Messi", result.get(0).getName());
        assertEquals("Inter Miami", result.get(0).getTeam());
        assertEquals("Forward", result.get(0).getPosition());

        verify(this.playerRepository, times(1)).findAll();

    }

    @Test
    public void testFindById() {
        // Given
        Long id = 1L;

        // When
        when(this.playerRepository.findById(anyLong())).thenReturn(DataProvider.playerMock());
        Player player = this.playerService.findById(id);

        // Then
        assertNotNull(player);
        assertEquals("Lionel Messi", player.getName());
        assertEquals("Inter Miami", player.getTeam());
        assertEquals("Forward", player.getPosition());

        // Para verificar que se llamo efectivamente al metodo de la dependencia
        verify(this.playerRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testSave() {
        // Given
        Player player = DataProvider.newPlayerMock();

        // When
        this.playerService.save(player);

        // Then - Para testear algo que no devuelve nada, no podemos usar el when para definir el comportamiento del mock
        // Primero con ArgumentCaptor capturamos el argumento que se le pasa al metodo save de la dependencia
        ArgumentCaptor<Player> playerArgumentCaptor = ArgumentCaptor.forClass(Player.class);
        // para en segundo lugar, verificarlo con verify a traves del captor con el metodo save de la dependencia y capturando el argumento con el captor
        verify(this.playerRepository).save(any(Player.class));
        verify(this.playerRepository).save(playerArgumentCaptor.capture());

        assertEquals(7L, playerArgumentCaptor.getValue().getId());
        assertEquals("Arturo Vidal", playerArgumentCaptor.getValue().getName());
        assertEquals("Colo-Colo", playerArgumentCaptor.getValue().getTeam());
    }

    @Test
    public void testDeleteById() {
        // Given
        Long id = 1L;

        // When
        this.playerService.deleteById(id);
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        // Then
        verify(this.playerRepository).deleteById(anyLong());
        verify(this.playerRepository).deleteById(longArgumentCaptor.capture());

        assertEquals(1L, longArgumentCaptor.getValue());
    }
}
