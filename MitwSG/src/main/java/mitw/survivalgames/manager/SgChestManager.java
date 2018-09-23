package mitw.survivalgames.manager;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import mitw.survivalgames.SurvivalGames;
import mitw.survivalgames.utils.Arena;

public class SgChestManager {
	public static ArrayList<ItemStack> tir1Items = new ArrayList<>();
	public static ArrayList<ItemStack> tir1Tools = new ArrayList<>();
	public static ArrayList<ItemStack> tir2Items = new ArrayList<>();
	public static ArrayList<ItemStack> centerItems = new ArrayList<>();
	public static ArrayList<Location> opened = new ArrayList<>();
	public static SgChestManager scGm;

	public SgChestManager() {
		scGm = this;

	}

	public void registerChestItems() {
		setupTir1();
		setupTir2();
		setupCenter();
	}

	private void addtirItem(ItemStack i, int persent) {
		for (int a = 0; a < persent; a++)
			tir1Items.add(i);

	}

	private void addtirTool(ItemStack i, int persent) {
		for (int a = 0; a < persent; a++)
			tir1Tools.add(i);

	}

	private void addtir2Item(ItemStack i, int persent) {
		for (int a = 0; a < persent; a++)
			tir2Items.add(i);

	}

	private void addCenterItem(ItemStack i) {
		centerItems.add(i);
	}

	public void putTir2Item(Inventory i) {
		ItemStack it;
		int put;
		for (int a = 0; a < (SurvivalGames.getRandom().nextInt(3) + 4); a++) {
			do {
				it = SgChestManager.tir2Items.get(SurvivalGames.getRandom().nextInt(SgChestManager.tir2Items.size()));
				put = SurvivalGames.getRandom().nextInt(i.getSize());
			} while (i.getItem(put) != null || i.contains(it));
			i.setItem(put, it);
		}
	}

	public void putTir1Item(Inventory i) {
		ItemStack it;
		int put;
		for (int a = 0; a < (SurvivalGames.getRandom().nextInt(3) + 3); a++) {
			do {
				it = SgChestManager.tir1Items.get(SurvivalGames.getRandom().nextInt(SgChestManager.tir1Items.size()));
				put = SurvivalGames.getRandom().nextInt(i.getSize());
			} while (i.getItem(put) != null || i.contains(it));
			i.setItem(put, it);
		}
		for (int a = 0; a < (SurvivalGames.getRandom().nextInt(2) + 2); a++) {
			do {
				it = SgChestManager.tir1Tools.get(SurvivalGames.getRandom().nextInt(SgChestManager.tir1Tools.size()));
				put = SurvivalGames.getRandom().nextInt(i.getSize());
			} while (i.getItem(put) != null || i.contains(it));
			i.setItem(put, it);
		}
	}

	public void putCenteritem(Inventory i) {
		ItemStack it;
		int put;
		final int size = SgChestManager.centerItems.size();
		for (int a = 0; a < (SurvivalGames.getRandom().nextInt(4) + 3); a++) {
			do {
				it = centerItems.get(SurvivalGames.getRandom().nextInt(size));
				put = SurvivalGames.getRandom().nextInt(i.getSize());
			} while (i.getItem(put) != null || i.contains(it));
			i.setItem(put, it);
		}
	}

	public void setTir2(Location l, Arena a) {
		a.tir2Chests.add(l);
		a.saveTir2Chests();
	}

	public void removeTir2(Location l, Arena a) {
		a.tir2Chests.remove(l);
		a.saveTir2Chests();
	}

	public void setCenter(Location l, Arena a) {
		a.addCenter(l);
		a.saveCenterChest();
	}

	public void removeCenterTir2(Location l, Arena a) {
		a.centerChests.remove(l);
		a.saveCenterChest();
	}

	private void setupTir1() {
		addtirTool(new ItemStack(Material.WOOD_AXE), 5);
		addtirTool(new ItemStack(Material.LEATHER_HELMET), 7);
		addtirTool(new ItemStack(Material.LEATHER_CHESTPLATE), 7);
		addtirTool(new ItemStack(Material.LEATHER_LEGGINGS), 7);
		addtirTool(new ItemStack(Material.LEATHER_BOOTS), 7);
		addtirTool(new ItemStack(Material.STONE_AXE), 5);
		addtirTool(new ItemStack(Material.STONE_SWORD), 3);
		addtirTool(new ItemStack(Material.WOOD_SWORD), 5);
		addtirTool(new ItemStack(Material.FISHING_ROD), 3);

		addtirItem(new ItemStack(Material.GOLD_INGOT), 2);
		addtirItem(new ItemStack(Material.IRON_INGOT), 2);
		addtirItem(new ItemStack(Material.FLINT), 7);
		addtirItem(new ItemStack(Material.STICK), 8);
		addtirItem(new ItemStack(Material.FEATHER, 5), 7);
		addtirItem(new ItemStack(Material.WHEAT), 8);
		addtirItem(new ItemStack(Material.APPLE), 6);
		addtirItem(new ItemStack(Material.ROTTEN_FLESH), 7);
		addtirItem(new ItemStack(Material.RAW_CHICKEN), 7);
		addtirItem(new ItemStack(Material.COOKED_CHICKEN), 5);
		addtirItem(new ItemStack(Material.GRILLED_PORK), 4);
		addtirItem(new ItemStack(Material.BOAT), 7);
		addtirItem(new ItemStack(Material.MUSHROOM_SOUP), 4);
		addtirItem(new ItemStack(Material.ARROW, 2), 3);
		addtirItem(new ItemStack(Material.BOW), 1);
		addtirItem(new ItemStack(Material.STRING), 6);
		addtirItem(new ItemStack(Material.POTATO), 8);
		addtirItem(new ItemStack(Material.BAKED_POTATO), 7);
		addtirItem(new ItemStack(Material.CARROT_ITEM), 7);
		addtirItem(new ItemStack(Material.GOLDEN_CARROT), 4);
	}

	private void setupTir2() {
		addtir2Item(new ItemStack(Material.IRON_HELMET), 4);
		addtir2Item(new ItemStack(Material.IRON_CHESTPLATE), 3);
		addtir2Item(new ItemStack(Material.IRON_LEGGINGS), 3);
		addtir2Item(new ItemStack(Material.IRON_BOOTS), 4);
		addtir2Item(new ItemStack(Material.CHAINMAIL_HELMET), 5);
		addtir2Item(new ItemStack(Material.CHAINMAIL_CHESTPLATE), 5);
		addtir2Item(new ItemStack(Material.CHAINMAIL_LEGGINGS), 5);
		addtir2Item(new ItemStack(Material.CHAINMAIL_BOOTS), 5);
		addtir2Item(new ItemStack(Material.GOLD_HELMET), 6);
		addtir2Item(new ItemStack(Material.GOLD_CHESTPLATE), 6);
		addtir2Item(new ItemStack(Material.GOLD_LEGGINGS), 6);
		addtir2Item(new ItemStack(Material.GOLD_BOOTS), 6);
		addtir2Item(new ItemStack(Material.STONE_SWORD), 6);
		addtir2Item(new ItemStack(Material.GOLDEN_APPLE), 3);
		addtir2Item(new ItemStack(Material.DIAMOND), 1);
		addtir2Item(new ItemStack(Material.BOW), 3);
		addtir2Item(new ItemStack(Material.IRON_INGOT), 6);
		addtir2Item(new ItemStack(Material.GOLD_INGOT, 3), 7);
		addtir2Item(new ItemStack(Material.GRILLED_PORK), 7);
		addtir2Item(new ItemStack(Material.BAKED_POTATO), 7);
		addtir2Item(new ItemStack(Material.MUSHROOM_SOUP), 7);
	}

	private void setupCenter() {
		addCenterItem(new ItemStack(Material.IRON_HELMET));
		addCenterItem(new ItemStack(Material.IRON_CHESTPLATE));
		addCenterItem(new ItemStack(Material.IRON_LEGGINGS));
		addCenterItem(new ItemStack(Material.IRON_BOOTS));
		addCenterItem(new ItemStack(Material.CHAINMAIL_HELMET));
		addCenterItem(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		addCenterItem(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		addCenterItem(new ItemStack(Material.CHAINMAIL_BOOTS));
		addCenterItem(new ItemStack(Material.GOLD_HELMET));
		addCenterItem(new ItemStack(Material.GOLD_CHESTPLATE));
		addCenterItem(new ItemStack(Material.GOLD_LEGGINGS));
		addCenterItem(new ItemStack(Material.GOLD_BOOTS));
		addCenterItem(new ItemStack(Material.STONE_SWORD));
		addCenterItem(new ItemStack(Material.GOLDEN_APPLE));
		addCenterItem(new ItemStack(Material.DIAMOND));
		addCenterItem(new ItemStack(Material.WOOD_AXE));
		addCenterItem(new ItemStack(Material.LEATHER_HELMET));
		addCenterItem(new ItemStack(Material.LEATHER_CHESTPLATE));
		addCenterItem(new ItemStack(Material.LEATHER_LEGGINGS));
		addCenterItem(new ItemStack(Material.LEATHER_BOOTS));
		addCenterItem(new ItemStack(Material.STONE_AXE));
		addCenterItem(new ItemStack(Material.STONE_SWORD));
		addCenterItem(new ItemStack(Material.WOOD_SWORD));
		addCenterItem(new ItemStack(Material.FISHING_ROD));
		addCenterItem(new ItemStack(Material.GOLD_INGOT));
		addCenterItem(new ItemStack(Material.IRON_INGOT));
		addCenterItem(new ItemStack(Material.FLINT));
		addCenterItem(new ItemStack(Material.STICK));
		addCenterItem(new ItemStack(Material.FEATHER));
		addCenterItem(new ItemStack(Material.APPLE));
		addCenterItem(new ItemStack(Material.ROTTEN_FLESH));
		addCenterItem(new ItemStack(Material.COOKED_CHICKEN));
		addCenterItem(new ItemStack(Material.MUSHROOM_SOUP));
		addCenterItem(new ItemStack(Material.ARROW, 2));
		addCenterItem(new ItemStack(Material.BOW));
		addCenterItem(new ItemStack(Material.STRING));
		addCenterItem(new ItemStack(Material.BAKED_POTATO));
		addCenterItem(new ItemStack(Material.CARROT_ITEM));
		addCenterItem(new ItemStack(Material.GOLDEN_CARROT));
	}

}
