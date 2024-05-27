package seng201.team0.unittests.factors;

import org.junit.jupiter.api.Test;
import seng201.team0.enums.ItemType;
import seng201.team0.enums.Material;
import seng201.team0.factors.Item;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTest {
    private Item item;

    static class TestItem extends Item {    //A concrete subclass of Item for testing
        public TestItem(String itemName, ItemType itemType, int itemCost, Material resourceType) {
            super(itemName, itemType, itemCost, resourceType);
        }
    }
    @BeforeEach
    void setUp() {
        item = new TestItem("Basic Tower", ItemType.TOWER, 200, Material.WOOD);
    }

    @Test
    public void testInitialState() {
        assertEquals("Basic Tower", item.getItemName());
        assertEquals(ItemType.TOWER, item.getItemType());
        assertEquals(200, item.getItemCost());
        assertEquals(Material.WOOD, item.getItemResourceType());
    }

    @Test
    public void testSetAndGetItemName() {
        item.setItemName("King's Tower");
        assertEquals("King's Tower", item.getItemName());
    }

    @Test
    public void testSetAndGetItemType() {
        item.setItemType(ItemType.UPGRADE);
        assertEquals(ItemType.UPGRADE, item.getItemType());
    }

    @Test
    public void testSetAndGetItemCost() {
        item.setItemCost(300);
        assertEquals(300, item.getItemCost());
    }

    @Test
    public void testGetAndSetItemResourceType() {
        item.setItemResourceType(Material.IRON);
        assertEquals(Material.IRON, item.getItemResourceType());
    }
}
