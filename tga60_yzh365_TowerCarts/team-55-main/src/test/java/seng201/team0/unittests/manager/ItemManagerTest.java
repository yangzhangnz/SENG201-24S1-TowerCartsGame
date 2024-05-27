package seng201.team0.unittests.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.factors.Tower;
import seng201.team0.factors.Upgrade;
import seng201.team0.manager.ItemManager;
import seng201.team0.enums.Material;
import seng201.team0.enums.Type;
import java.util.Map;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ItemManagerTest {
    private ItemManager itemManager;

    @BeforeEach
    void setUp() {
        itemManager = new ItemManager();
    }

    @Test
    void testTowerInitialState() {
        Map<Material, Tower> towers = itemManager.getTowers();
        assertNotNull(towers);
        assertFalse(towers.isEmpty());

        for (Material mater : Material.values()) {
            assertTrue(towers.containsKey(mater));
            assertNotNull(towers.get(mater));
        }
    }

    @Test
    void testUpgradeInitialState() {
        Map<Type, Upgrade> upgrades = itemManager.getUpgrades();
        assertNotNull(upgrades);
        assertFalse(upgrades.isEmpty());

        for (Type type : Type.values()) {
            assertTrue(upgrades.containsKey(type));
            assertNotNull(upgrades.get(type));
        }
    }

    @Test
    void testGetTowerList() {
        List<Tower> towerList = itemManager.getTowerList();
        assertNotNull(towerList);
        assertFalse(towerList.isEmpty());
    }

    @Test
    void testSetTowerList() {
        List<Tower> newTowers = List.of(new Tower(Material.DIRT), new Tower(Material.WOOD));
        itemManager.setTowerList(newTowers);
        List<Tower> towerList = itemManager.getTowerList();
        assertTrue(towerList.containsAll(newTowers));
    }

    @Test
    void testGetReserveTowers() {
        Tower[] reserveTowers = itemManager.getReserveTowers();
        assertNotNull(reserveTowers);
        assertEquals(5, reserveTowers.length);
    }

    @Test
    void testSetReserveTowers() {
        Tower[] newReserveTowers = new Tower[]{new Tower(Material.GOLD), new Tower(Material.SILVER)};
        itemManager.setReserveTowers(newReserveTowers);
        Tower[] reserveTowers = itemManager.getReserveTowers();
        assertArrayEquals(newReserveTowers, reserveTowers);
    }

    @Test
    void testGetTowerOptions() {
        List<Tower> towerOptions = itemManager.getTowersOptions();
        assertNotNull(towerOptions);
        assertTrue(towerOptions.size() >= 5);
    }

    @Test
    void testDefaultTowersOptions() {
        List<Tower> towerOptions = itemManager.getTowersOptions();
        assertEquals(6, towerOptions.size());

        for (Material mater : new Material[]{Material.COAL, Material.STONE, Material.IRON, Material.COPPER,
                                           Material.SILVER, Material.GOLD}) {
            assertTrue(towerOptions.stream().anyMatch(t -> t.getMaterial() == mater),
                    "Default towers should include: " + mater);
        }
    }

    @Test
    void testGetTower() {
        Map<Material, Tower> towers = itemManager.getTowers();
        assertNotNull(towers);
        assertEquals(Material.values().length, towers.size());
    }
}
