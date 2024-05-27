package seng201.team0.unittests.factors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import seng201.team0.enums.ItemType;
import seng201.team0.factors.Upgrade;
import seng201.team0.factors.Tower;
import seng201.team0.enums.Material;
import seng201.team0.enums.Type;

public class UpgradeTest {
    private Upgrade upgrade;

    @BeforeEach
    public void setUp() {
        upgrade = new Upgrade("Speed", 2900, Material.GOLD);
    }

    @Test
    public void testInitialState() {
        assertEquals("Speed", upgrade.getItemName());
        assertEquals(ItemType.UPGRADE, upgrade.getItemType());
        assertEquals(2900, upgrade.getItemCost());
        assertEquals(Material.GOLD, upgrade.getItemResourceType());
        assertEquals(0, upgrade.getAmountOfType());
    }

    @Test
    public void testIncreaseAmount() {
        upgrade.increaseAmount();
        assertEquals(1, upgrade.getAmountOfType());

        upgrade.increaseAmount();
        upgrade.increaseAmount();
        upgrade.increaseAmount();
        assertEquals(4, upgrade.getAmountOfType());
    }

    @Test
    public void testDecreaseAmount() {
        upgrade.increaseAmount();
        upgrade.increaseAmount();
        upgrade.increaseAmount();
        assertEquals(3, upgrade.getAmountOfType());

        upgrade.decreaseAmount();
        assertEquals(2, upgrade.getAmountOfType());
    }
}
