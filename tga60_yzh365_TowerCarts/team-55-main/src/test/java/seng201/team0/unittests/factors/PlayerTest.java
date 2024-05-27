package seng201.team0.unittests.factors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import seng201.team0.factors.Player;
import seng201.team0.enums.Difficulty;
import seng201.team0.enums.Material;
import seng201.team0.factors.Tower;

import java.security.PublicKey;
import java.util.Arrays;
import java.util.List;

public class PlayerTest {
    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
    }

    @Test
    public void testInitialState() {
        assertNull(player.getName());
        assertEquals(Difficulty.NORMAL, player.getDifficulty());
        assertEquals(800, player.getMoney());
        assertEquals(5, player.getLives());
    }

    @Test
    public void testGetAndSetName() {
        assertTrue(player.setName("Yang Zhang"), "Should return true for valid name.");
        assertEquals("Yang Zhang", player.getName());

        assertFalse(player.setName(""));
        assertFalse(player.setName("a"));
        assertFalse(player.setName("1234567890123456"));
        assertFalse(player.setName("Invalid Name!"));
    }

    @Test
    public void testGetAndChooseDifficulty() {
        player.chooseDifficulty(0);
        assertEquals(Difficulty.EASIEST, player.getDifficulty());

        player.chooseDifficulty(10);
        assertEquals(Difficulty.NORMAL, player.getDifficulty());

        player.chooseDifficulty(20);
        assertEquals(Difficulty.HARDEST, player.getDifficulty());
    }

    @Test
    public void testMoneyManagement() {
        player.addMoney(500);
        assertEquals(1300, player.getMoney());
    }

    @Test
    public void testLives() {
        player.loseLives();
        assertEquals(4, player.getLives());

        player.loseLives();
        player.loseLives();
        player.loseLives();
        assertEquals(1, player.getLives());
    }
}
