package net.glasslauncher.playerapi;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.player.ClientPlayer;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.SleepStatus;
import net.minecraft.util.io.CompoundTag;

public abstract class PlayerBase {

    public ClientPlayer player;

    public PlayerBase(ClientPlayer p) {
        player = p;
    }

    public void playerInit() {
    }

    public boolean onLivingUpdate() {
        return false;
    }

    public boolean updatePlayerActionState() {
        return false;
    }

    public boolean handleKeyPress(int i, boolean flag) {
        return false;
    }

    public boolean writeEntityBaseToNBT(CompoundTag tag) {
        return false;
    }

    public boolean readEntityBaseFromNBT(CompoundTag tag) {
        return false;
    }

    public boolean setEntityBaseDead() {
        return false;
    }

    public boolean onDeath(EntityBase killer) {
        return false;
    }

    public boolean respawn() {
        return false;
    }

    public boolean attackEntityBaseFrom(EntityBase attacker, int damage) {
        return false;
    }

    public double getDistanceSq(double d, double d1, double d2, double answer) {
        return answer;
    }

    public boolean isInWater(boolean inWater) {
        return inWater;
    }

    public boolean onExitGUI() {
        return false;
    }

    public boolean heal(int i) {
        return false;
    }

    public boolean canTriggerWalking(boolean canTrigger) {
        return canTrigger;
    }

    public int getPlayerArmorValue(int armor) {
        return armor;
    }

    public float getCurrentPlayerStrVsBlock(BlockBase block, float f) {
        return f;
    }

    public boolean moveFlying(float x, float y, float z) {
        return false;
    }

    public boolean moveEntityBase(double x, double y, double d) {
        return false;
    }

    public SleepStatus sleepInBedAt(int x, int y, int z, SleepStatus status) {
        return status;
    }

    public float getEntityBaseBrightness(float f, float brightness) {
        return brightness;
    }

    public boolean pushOutOfBlocks(double x, double y, double d) {
        return false;
    }

    public boolean onUpdate() {
        return false;
    }

    public void afterUpdate() {
    }

    public boolean moveEntityBaseWithHeading(float f, float f1) {
        return false;
    }

    public boolean isOnLadder(boolean onLadder) {
        return onLadder;
    }

    public boolean isInsideOfMaterial(Material material, boolean inMaterial) {
        return inMaterial;
    }

    public boolean isSneaking(boolean sneaking) {
        return sneaking;
    }

    public boolean dropCurrentItem() {
        return false;
    }

    public boolean dropPlayerItem(ItemInstance itemstack) {
        return false;
    }

    public boolean displayGUIEditSign(Sign sign) {
        return false;
    }

    public boolean displayGUIChest(InventoryBase iinventory) {
        return false;
    }

    public boolean displayWorkbenchGUI(int i, int j, int k) {
        return false;
    }

    public boolean displayGUIFurnace(TileEntityFurnace furnace) {
        return false;
    }

    public boolean displayGUIDispenser(TileEntityDispenser dispenser) {
        return false;
    }

    public boolean sendChatMessage(String s) {
        return false;
    }

    public String getHurtSound(String previous) {
        return null;
    }

    public Boolean canHarvestBlock(BlockBase block, Boolean previous) {
        return null;
    }

    public boolean fall(float f) {
        return false;
    }

    public boolean jump() {
        return false;
    }

    public boolean damageEntityBase(int i) {
        return false;
    }

    public Double getDistanceSqToEntityBase(EntityBase EntityBase, Double previous) {
        return null;
    }

    public boolean attackTargetEntityBaseWithCurrentItem(EntityBase EntityBase) {
        return false;
    }

    public Boolean handleWaterMovement(Boolean previous) {
        return null;
    }

    public Boolean handleLavaMovement(Boolean previous) {
        return null;
    }

    public boolean dropPlayerItemWithRandomChoice(ItemInstance itemstack, boolean flag) {
        return false;
    }

    public void beforeUpdate() {
    }

    public void beforeMoveEntityBase(double d3, double d4, double d5) {
    }

    public void afterMoveEntityBase(double d3, double d4, double d5) {
    }

    public void beforeSleepInBedAt(int l, int i1, int j1) {
    }
}
