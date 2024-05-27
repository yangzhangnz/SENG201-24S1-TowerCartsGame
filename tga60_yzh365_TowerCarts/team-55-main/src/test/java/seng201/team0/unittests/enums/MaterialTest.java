package seng201.team0.unittests.enums;

import org.junit.jupiter.api.Test;
import seng201.team0.enums.Material;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaterialTest {
    @Test
    public void testGetProductionRate() {
        assertEquals(20, Material.DIRT.getProductionRate());
        assertEquals(19, Material.WOOD.getProductionRate());
        assertEquals(18, Material.COAL.getProductionRate());
        assertEquals(17, Material.STONE.getProductionRate());
        assertEquals(16, Material.IRON.getProductionRate());
        assertEquals(15, Material.COPPER.getProductionRate());
        assertEquals(14, Material.SILVER.getProductionRate());
        assertEquals(13, Material.GOLD.getProductionRate());
        assertEquals(0, Material.ANY.getProductionRate());
    }

    @Test
    public void testMaterialGetCost() {
        assertEquals(1500, Material.DIRT.getCost());
        assertEquals(1700, Material.WOOD.getCost());
        assertEquals(1900, Material.COAL.getCost());
        assertEquals(2100, Material.STONE.getCost());
        assertEquals(2300, Material.IRON.getCost());
        assertEquals(2500, Material.COPPER.getCost());
        assertEquals(2700, Material.SILVER.getCost());
        assertEquals(2900, Material.GOLD.getCost());
        assertEquals(0, Material.ANY.getCost());
    }

    @Test
    public void testMaterialGetName() {
        assertEquals("Dirt", Material.DIRT.getName());
        assertEquals("Wood", Material.WOOD.getName());
        assertEquals("Coal", Material.COAL.getName());
        assertEquals("Stone", Material.STONE.getName());
        assertEquals("Iron", Material.IRON.getName());
        assertEquals("Copper", Material.COPPER.getName());
        assertEquals("Silver", Material.SILVER.getName());
        assertEquals("Gold", Material.GOLD.getName());
        assertEquals("Any", Material.ANY.getName());
    }

    @Test
    public void testGetMaterialPrice() {
        assertEquals(1, Material.DIRT.getMaterialPrice());
        assertEquals(2, Material.WOOD.getMaterialPrice());
        assertEquals(3, Material.COAL.getMaterialPrice());
        assertEquals(4, Material.STONE.getMaterialPrice());
        assertEquals(5, Material.IRON.getMaterialPrice());
        assertEquals(6, Material.COPPER.getMaterialPrice());
        assertEquals(7, Material.SILVER.getMaterialPrice());
        assertEquals(8, Material.GOLD.getMaterialPrice());
        assertEquals(0, Material.ANY.getMaterialPrice());
    }
}
