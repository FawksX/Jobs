package com.gamingmesh.jobs.cmi.gui;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.cmi.lib.CMIChatColor;
import com.gamingmesh.jobs.cmi.lib.CMIMaterial;
import com.gamingmesh.jobs.cmi.lib.Version;
import com.gamingmesh.jobs.commands.JobsCommands;
import com.gamingmesh.jobs.util.PageInfo;

public class CMIGui {

    private InventoryType invType;
    private GUIManager.GUIRows gUIRows;
    private Player player;
    private Inventory inv;
    private String title;
    private HashMap<Integer, CMIGuiButton> buttons = new HashMap<Integer, CMIGuiButton>();
    private LinkedHashSet<CMIGuiButton> noSlotButtons = new LinkedHashSet<CMIGuiButton>();

    private HashMap<GUIManager.InvType, GUIManager.GUIFieldType> lock = new HashMap<GUIManager.InvType, GUIManager.GUIFieldType>();
    private HashMap<GUIManager.InvType, String> permLock = new HashMap<GUIManager.InvType, String>();

    private GUIManager.CmiInventoryType type = GUIManager.CmiInventoryType.regular;
    private Object whatShows;

    private boolean allowShift = false;

    public CMIGui() {
    }

    public CMIGui(Player player) {
	this.player = player;
    }

    @Override
    public CMIGui clone() {
	CMIGui g = new CMIGui(player);
	g.setInvSize(gUIRows);
	g.setButtons(buttons);
	g.setInv(inv);
	g.setInvType(invType);
	g.setTitle(title);
	g.setCmiInventoryType(type);
	g.setWhatShows(whatShows);
	return g;
    }

    public boolean isSimilar(CMIGui gui) {

	if (this.getInvSize() != gui.getInvSize())
	    return false;

	if (this.getInvType() != gui.getInvType())
	    return false;

	return true;
    }

    public CMIGui open() {
	GUIManager.openGui(this);
	return this;
    }

    public CMIGui update() {
	GUIManager.softUpdateContent(this);
	return this;
    }

    public InventoryType getInvType() {
	if (invType == null)
	    invType = InventoryType.CHEST;
	return invType;
    }

    public void setInvType(InventoryType invType) {
	this.invType = invType;
    }

    public GUIManager.GUIRows getInvSize() {
	if (gUIRows == null)
	    autoResize();
	return gUIRows;
    }

    public void setInvSize(GUIManager.GUIRows GUIRows) {
	this.gUIRows = GUIRows;
    }

    public void setInvSize(int rows) {
	this.gUIRows = GUIManager.GUIRows.getByRows(rows);
    }

    public void autoResize() {
	this.combineButtons();
	int max = 0;
	for (Entry<Integer, CMIGuiButton> one : this.buttons.entrySet()) {
	    if (one.getKey() > max)
		max = one.getKey();
	}

	if (max < 9) {
	    this.gUIRows = GUIManager.GUIRows.r1;
	} else if (max < 18) {
	    this.gUIRows = GUIManager.GUIRows.r2;
	} else if (max < 27) {
	    this.gUIRows = GUIManager.GUIRows.r3;
	} else if (max < 36) {
	    this.gUIRows = GUIManager.GUIRows.r4;
	} else if (max < 45) {
	    this.gUIRows = GUIManager.GUIRows.r5;
	} else {
	    this.gUIRows = GUIManager.GUIRows.r6;
	}
    }

    public Player getPlayer() {
	return player;
    }

    public void setPlayer(Player player) {
	this.player = player;
    }

    public Inventory getInv() {
	if (inv == null)
	    GUIManager.generateInventory(this);
	return inv;
    }

    public void setInv(Inventory inv) {
	this.inv = inv;
    }

    public String getTitle() {
	if (title == null)
	    title = "";
	return CMIChatColor.translate(title);
    }

    public void updateTitle(String title) {
	setTitle(title);
//	Jobs.getNms().updateInventoryTitle(player, this.title);
    }

    public void setTitle(String title) {
	if (Version.isCurrentEqualOrHigher(Version.v1_16_R1)) {
	    if (CMIChatColor.stripColor(title).length() > 64) {
		title = title.substring(0, 63) + "~";
	    }
	} else {
	    if (title.length() > 32) {
		title = title.substring(0, 31) + "~";
	    }
	}
	this.title = title;
    }
    

    public HashMap<Integer, CMIGuiButton> getButtons() {
	combineButtons();
	return buttons;
    }

    public CMIGui addButton(CMIGuiButton button) {
	return addButton(button, 54);
    }

    public CMIGui addButton(CMIGuiButton button, int maxSlot) {
	if (button.getSlot() != null && buttons.get(button.getSlot()) != null) {
	    for (int ii = button.getSlot(); ii < maxSlot; ii++) {
		CMIGuiButton b = buttons.get(ii);
		if (b == null) {
		    buttons.put(ii, button);
		    break;
		}
	    }
	    return this;
	}

	if (button.getSlot() == null) {
	    noSlotButtons.add(button);
	    return this;
	}
	buttons.put(button.getSlot(), button);
	setInv(null);
	return this;
    }

    public CMIGui updateButton(CMIGuiButton button) {
	buttons.put(button.getSlot(), button);
	setInv(null);
	return this;
    }

    private void combineButtons() {
	for (CMIGuiButton button : noSlotButtons) {
	    for (int ii = 0; ii < 54; ii++) {
		CMIGuiButton b = buttons.get(ii);
		if (b == null) {
		    buttons.put(ii, button);
		    break;
		}
	    }
	}
	noSlotButtons.clear();
    }

    public void fillEmptyButtons() {
	combineButtons();
	for (int i = 0; i < this.getInvSize().getFields(); i++) {
	    if (this.buttons.containsKey(i))
		continue;
	    addEmptyButton(i);
	}
    }

    private CMIMaterial filler = CMIMaterial.BLACK_STAINED_GLASS_PANE;

    public void addEmptyButton(int slot) {
	ItemStack MiscInfo = filler.newItemStack();
	if (!CMIMaterial.isAir(MiscInfo.getType())) {
	    ItemMeta MiscInfoMeta = MiscInfo.getItemMeta();
	    if (MiscInfoMeta != null) {
		MiscInfoMeta.setDisplayName(" ");
		MiscInfo.setItemMeta(MiscInfoMeta);
	    }
	}
	addButton(new CMIGuiButton(slot, GUIManager.GUIFieldType.Locked, MiscInfo));
    }

    public void setButtons(HashMap<Integer, CMIGuiButton> buttons) {
//	for (Entry<Integer, CMIGuiButton> one : buttons.entrySet()) {
//	    CMIGuiButton old = this.buttons.get(one.getKey());
//	    if (old == null)
//		old = one.getValue();
//	    buttons.put(one.getKey(), old);
//	}
	this.buttons = buttons;
    }

    public boolean isLocked(GUIManager.InvType type) {
	return lock.containsKey(type) ? (lock.get(type) == GUIManager.GUIFieldType.Locked) : false;
    }

    public void addLock(GUIManager.InvType type) {
	addLock(type, GUIManager.GUIFieldType.Locked);
    }

    public void addLock(GUIManager.InvType type, GUIManager.GUIFieldType lock) {
	this.lock.put(type, lock);
    }

    public boolean isPermLocked(GUIManager.InvType type) {
	return permLock.containsKey(type) ? (!this.player.hasPermission(permLock.get(type))) : true;
    }

    public void addPermLock(GUIManager.InvType type, String perm) {
	this.permLock.put(type, perm);
    }

    public GUIManager.CmiInventoryType getType() {
	return type;
    }

    public void setCmiInventoryType(GUIManager.CmiInventoryType type) {
	this.type = type;
    }

    public Object getWhatShows() {
	return whatShows;
    }

    public void setWhatShows(Object whatShows) {
	this.whatShows = whatShows;
    }

    public Integer getSlot(GUIManager.GUIButtonLocation place) {
	GUIManager.GUIRows size = this.getInvSize();
	int v = place.getCollumn() * 9;
	v = place.getCollumn() > 0 ? v - 1 : v;
	Integer value = (((place.getRow() * (size.getRows())) * 9) - 8) + v;
	value = place.getRow() > 0 ? value : value + 9;
	return value - 1;
    }

    public void onClose() {

    }

    public boolean click(int slot) {
	return click(slot, null, null);
    }

    public boolean click(int slot, GUIManager.GUIClickType type, ItemStack currentItem) {
	return true;
    }

    public void addPagination(PageInfo pi, Object cmd, String pagePref) {
	addPagination(pi, JobsCommands.LABEL + " " + cmd.getClass().getSimpleName(), pagePref);
    }

    public void addPagination(PageInfo pi, String cmd, String pagePref) {

	if (!cmd.startsWith("/"))
	    cmd = "/" + cmd;
//	String separator = this.getMsg(LC.info_fliperSimbols); 

	int CurrentPage = pi.getCurrentPage();
	int pageCount = pi.getTotalPages();
	int totalEntries = pi.getTotalEntries();

	if (pageCount == 1)
	    return;
	if (this.getInvSize().getRows() < 6)
	    this.setInvSize(GUIManager.GUIRows.r6);

	Integer prevSlot = this.getSlot(GUIManager.GUIButtonLocation.bottomLeft);
	Integer nextSlot = this.getSlot(GUIManager.GUIButtonLocation.bottomRight);
	Integer midSlot = this.getSlot(GUIManager.GUIButtonLocation.bottomRight) - 4;

	String pagePrefix = pagePref == null ? "" : pagePref;

	int NextPage = CurrentPage + 1;
	NextPage = CurrentPage < pageCount ? NextPage : CurrentPage;
	int Prevpage = CurrentPage - 1;
	Prevpage = CurrentPage > 1 ? Prevpage : CurrentPage;

//	RawMessage rm = new RawMessage();

	if (pageCount != 0) {

	    for (int i = GUIManager.GUIRows.r5.getFields(); i < GUIManager.GUIRows.r6.getFields(); i++) {
		this.getButtons().remove(i);
	    }

	    CMIGuiButton button = new CMIGuiButton(midSlot, CMIMaterial.WHITE_WOOL.newItemStack());
	    button.setName(Jobs.getLanguage().getMessage("command.help.output.pageCount", "[current]", CurrentPage, "[total]", pageCount));
	    button.addLore(Jobs.getLanguage().getMessage("pageCountHover", "[totalEntries]", totalEntries));
	    this.addButton(button);

	    if (this.getButtons().get(prevSlot) == null && CurrentPage > 1) {
		button = new CMIGuiButton(prevSlot, CMIMaterial.WHITE_WOOL.newItemStack());
		button.setName(Jobs.getLanguage().getMessage("command.help.output.prevPageGui"));
		button.addLore(Jobs.getLanguage().getMessage("command.help.output.pageCount", "[current]", CurrentPage, "[total]", pageCount));
		button.addCommand(cmd + " " + pagePrefix + Prevpage, GUIManager.CommandType.silent);
		this.addButton(button);
	    }

	    if (this.getButtons().get(nextSlot) == null && pageCount > CurrentPage) {
		button = new CMIGuiButton(nextSlot, CMIMaterial.WHITE_WOOL.newItemStack());
		button.setName(Jobs.getLanguage().getMessage("command.help.output.nextPageGui"));
		button.addLore(Jobs.getLanguage().getMessage("command.help.output.pageCount", "[current]", CurrentPage, "[total]", pageCount));
		button.addCommand(cmd + " " + pagePrefix + NextPage, GUIManager.CommandType.silent);
		this.addButton(button);
	    }

	}
    }

    public boolean isAllowShift() {
	return allowShift;
    }

    public void setAllowShift(boolean allowShift) {
	this.allowShift = allowShift;
    }

    public CMIMaterial getFiller() {
	return filler;
    }

    public void setFiller(CMIMaterial filler) {
	this.filler = filler;
    }
}
