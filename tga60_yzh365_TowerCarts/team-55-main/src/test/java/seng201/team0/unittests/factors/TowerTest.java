package seng201.team0.unittests.factors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.enums.ItemType;
import seng201.team0.enums.Material;
import seng201.team0.factors.Tower;

import static org.junit.jupiter.api.Assertions.*;

public class TowerTest {
    private Tower tower;

    @BeforeEach
    void setUp() {
        tower = new Tower(Material.WOOD);
    }

    @Test
    public void testInitialState() {
        assertEquals("Wood", tower.getItemName());
        assertEquals(ItemType.TOWER, tower.getItemType());
        assertEquals(Material.WOOD.getCost(), tower.getItemCost());
        assertEquals(0, tower.getMoneyMade());
        assertEquals(Material.WOOD.getProductionRate(), tower.getProductionRate());
    }

    @Test
    public void testChangeType() {
        tower.changeType(Material.IRON);
        assertEquals(Material.IRON, tower.getMaterial());
        assertEquals(Material.IRON.getCost(), tower.getItemCost());
        assertEquals("Iron", tower.getItemName());
    }

    @Test
    public void testMoneyManagement() {
        tower.increaseMoneyMade();
        int expectMoney = Material.WOOD.getMaterialPrice() * Material.WOOD.getProductionRate();
        assertEquals(expectMoney, tower.getMoneyMade());
    }

    @Test
    public void testOTowerStatus() {
        assertFalse(tower.getIsBroken());
        tower.setIsBroken(true);
        assertTrue(tower.getIsBroken());
    }

    @Test
    public void testLevelManagement() {
        assertEquals(1, tower.getLevel());
        tower.addLevel(1);
        assertEquals(2, tower.getLevel());

        assertEquals(0, tower.getUpgradeLevel());
        tower.increaseProductionRate(10);
        assertEquals(1, tower.getUpgradeLevel());
    }
}
