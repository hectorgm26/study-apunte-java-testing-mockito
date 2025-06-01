package org.testing.mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

// Mockito testea solamente las dependencias y no la clase que se esta testeando,
// y JUnit trabajara con la clase a testear
@ExtendWith(MockitoExtension.class)
public class GameTest {

    public static final int OPTION_ROCK = 0;
    public static final int OPTION_PAPER = 1;
    public static final int OPTION_SCISSORS = 2;

    // InjectMocks es para la clase que se esta testeando
    @InjectMocks
    private Game game;

    // El objeto scanner sera un mock y estara dentro de la clase Game
    // Mock es para las dependencias, simulando un objeto que no es real
    @Mock
    Scanner scanner;

    @Mock
    Random random;

    private ByteArrayOutputStream out;

    @BeforeEach
    // setup de configuracion que se ejecuta antes de cada test. De esta manera, cada prueba empieza con un ByteArrayOutputStream limpio, lo que garantiza que las verificaciones de salida (out.toString().contains(...)) se hagan solo sobre lo que esa prueba produjo. Reinicia el buffer en cada prueba para evitar interferencias
    public void setup() {
        // Capturaremos al completo lo que sale de la consola para verificar lo que se imprime en ella
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out)); //indicaremos que objeto utilizaremos como consola de salida
    }

    @Test
    public void when_writeQuit_then_exitGame() {
        // cuando se llame a nextline, devolvera "Quit" y saldrá del juego
        when(scanner.nextLine()).thenReturn("Paper").thenReturn("Quit");

        game.play();

        // comparamos el texto con el toString del out del ByteArrayOutputStream
        assertTrue(out.toString().contains("Let's play Rock, Paper, Scissors!"));
    }

    @Test
    public void when_chooseRock_then_beatsScissors() {
        when(scanner.nextLine()).thenReturn("Rock").thenReturn("Quit");
        when(random.nextInt(3)).thenReturn(OPTION_SCISSORS);

        game.play();

        assertTrue(out.toString().contains("Computer chose scissors"));
        assertTrue(out.toString().contains("wins:1"));
    }

    @Test
    public void when_chooseScissors_then_beatsPaper() {
        when(scanner.nextLine()).thenReturn("Scissors").thenReturn("Quit");
        when(random.nextInt(3)).thenReturn(OPTION_PAPER);

        game.play();

        assertTrue(out.toString().contains("Computer chose paper"));
        assertTrue(out.toString().contains("wins:1"));
    }

    @Test
    public void when_choosePaper_then_beatsRock() {
        when(scanner.nextLine()).thenReturn("Paper").thenReturn("Quit");
        when(random.nextInt(3)).thenReturn(OPTION_ROCK);

        game.play();

        assertTrue(out.toString().contains("Computer chose rock"));
        assertTrue(out.toString().contains("wins:1"));
    }

    @Test
    public void when_bothChooseRock_then_tie() {
        when(scanner.nextLine()).thenReturn("Rock").thenReturn("Quit");
        when(random.nextInt(3)).thenReturn(OPTION_ROCK);

        game.play();

        assertTrue(out.toString().contains("Computer chose rock"));
        assertTrue(out.toString().contains("ties:1"));
    }

    @Test
    public void when_chooseRockAndComputerChoosePaper_then_loose() {
        when(scanner.nextLine()).thenReturn("Rock").thenReturn("Quit");
        when(random.nextInt(3)).thenReturn(OPTION_PAPER);

        game.play();

        assertTrue(out.toString().contains("Computer chose paper"));
        assertTrue(out.toString().contains("loses:1"));
    }
}
