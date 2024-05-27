package seng201.team0.unittests.factors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import seng201.team0.factors.Rounds;

public class RoundsTest {
    private Rounds rounds;

    @BeforeEach
    public void setUp() {
        rounds = new Rounds();
    }

    @Test
    public void testInitialState() {
        assertEquals(1, rounds.getCurrentRound());
        assertEquals(5, rounds.getMaxRound());
    }

    @Test
    public void testSetAndGetCurrentRound() {
        rounds.setCurrentRound(3);
        assertEquals(3, rounds.getCurrentRound());
    }

    @Test
    public void testSetAndGetMaxRound() {
        rounds.setMaxRound(10);
        assertEquals(10, rounds.getMaxRound());
    }
}
