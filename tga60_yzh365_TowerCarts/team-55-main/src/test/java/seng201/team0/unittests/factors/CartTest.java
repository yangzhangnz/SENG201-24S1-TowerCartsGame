package seng201.team0.unittests.factors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.enums.Material;
import seng201.team0.factors.Cart;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartTest {
    private Cart cart;

    @BeforeEach
    public void setUp() {
        cart = new Cart(Material.WOOD);
    }


    @Test
    public void testInitialState() {
        assertEquals(100, cart.getMaxSize());
        assertEquals(0, cart.getCurrentSize());
        assertEquals(Material.WOOD, cart.getMaterialType());    // Wood
        assertEquals(0.0, cart.getCurrentDistance());
        assertEquals(100, cart.getMaxDistance());
    }

    @Test
    public void testFill() {
        cart.fill(50);
        assertEquals(50, cart.getMaxSize());

        cart.fill(60);
        assertEquals(0, cart.getMaxSize());
    }

    @Test
    public void testSetAndGetDistance() {
        cart.setCurrentDistance(50);
        assertEquals(50, cart.getCurrentDistance());    // 50
    }

    @Test
    public void testSetAndGetMaxSize() {
        cart.setMaxSize(120);
        assertEquals(120, cart.getMaxSize());   // 120
    }

    @Test
    public void testSetAndGetCurrentSize() {
        cart.setCurrentSize(20);
        assertEquals(20, cart.getCurrentSize());    // 20
    }

    @Test
    public void testSetAndGetMaterialType() {
        cart.setMaterialType(Material.IRON);
        assertEquals(Material.IRON, cart.getMaterialType());    // IRON
    }
}
