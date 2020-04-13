package net.glasslauncher.mixin.playerapi;

import net.glasslauncher.playerapi.EntityPlayerSPAccessor;
import net.glasslauncher.playerapi.PlayerAPI;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Random;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends EntityPlayer implements EntityPlayerSPAccessor {

    private List playerBases;

    @Shadow public MovementInput movementInput;

    @Shadow private MouseFilter field_21903_bJ;

    @Shadow private MouseFilter field_21904_bK;

    @Shadow private MouseFilter field_21902_bL;

    public MixinEntityPlayerSP(World world) {
        super(world);
        field_21903_bJ = new MouseFilter();
        field_21904_bK = new MouseFilter();
        field_21902_bL = new MouseFilter();
        playerBases = PlayerAPI.playerInit((EntityPlayerSP) (Object) this);
    }

    @Override
    public List getPlayerBases() {
        return playerBases;
    }

    @Shadow public abstract void readEntityFromNBT(NBTTagCompound nbtTagCompound);

    @Inject(method = "moveEntity", at = @At("HEAD"), cancellable = true)
    private void onMoveEntity(double v, double v1, double v2, CallbackInfo ci) {
        if (PlayerAPI.moveEntity((EntityPlayerSP) (Object) this,v, v1, v2)) {
            ci.cancel();
        }
    }

    @Inject(method = "updatePlayerActionState", at = @At("HEAD"), cancellable = true)
    private void onUpdatePlayerActionState(CallbackInfo ci) {
        if (PlayerAPI.updatePlayerActionState((EntityPlayerSP) (Object) this)) {
            ci.cancel();
        }
    }

    @Inject(method = "onLivingUpdate", at = @At("HEAD"), cancellable = true)
    private void onOnLivingUpdate(CallbackInfo ci) {
        if (PlayerAPI.onLivingUpdate((EntityPlayerSP) (Object) this)) {
            ci.cancel();
        }
    }

    @Inject(method = "handleKeyPress", at = @At("HEAD"), cancellable = true)
    private void onHandleKeyPress(int i, boolean b, CallbackInfo ci) {
        if (PlayerAPI.handleKeyPress((EntityPlayerSP) (Object) this, i, b)) {
            ci.cancel();
        }
    }

    @Inject(method = "writeEntityToNBT", at = @At("HEAD"), cancellable = true)
    private void onWriteEntityToNBT(NBTTagCompound nbtTagCompound, CallbackInfo ci) {
        if (PlayerAPI.writeEntityToNBT((EntityPlayerSP) (Object) this, nbtTagCompound)) {
            ci.cancel();
        }
    }

    @Inject(method = "readEntityFromNBT", at = @At("HEAD"), cancellable = true)
    private void onReadEntityFromNBT(NBTTagCompound nbtTagCompound, CallbackInfo ci) {
        if (PlayerAPI.readEntityFromNBT((EntityPlayerSP) (Object) this, nbtTagCompound)) {
            ci.cancel();
        }
    }

    @Inject(method = "closeScreen", at = @At("HEAD"), cancellable = true)
    private void onCloseScreen(CallbackInfo ci) {
        if (PlayerAPI.onExitGUI((EntityPlayerSP) (Object) this)) {
            ci.cancel();
        }
    }

    @Inject(method = "displayGUIChest", at = @At("HEAD"), cancellable = true)
    private void onDisplayGUIChest(IInventory iInventory, CallbackInfo ci) {
        if (PlayerAPI.displayGUIChest((EntityPlayerSP) (Object) this, iInventory)) {
            ci.cancel();
        }
    }

    @Inject(method = "displayWorkbenchGUI", at = @At("HEAD"), cancellable = true)
    private void onDisplayWorkbenchGUI(int i, int i1, int i2, CallbackInfo ci) {
        if (PlayerAPI.displayWorkbenchGUI((EntityPlayerSP) (Object) this, i, i1, i2)) {
            ci.cancel();
        }
    }

    @Inject(method = "displayGUIFurnace", at = @At("HEAD"), cancellable = true)
    private void onDisplayGUIFurnace(TileEntityFurnace tileEntityFurnace, CallbackInfo ci) {
        if (PlayerAPI.displayGUIFurnace((EntityPlayerSP) (Object) this, tileEntityFurnace)) {
            ci.cancel();
        }
    }

    @Inject(method = "displayGUIDispenser", at = @At("HEAD"), cancellable = true)
    private void onDisplayGUIDispenser(TileEntityDispenser tileEntityDispenser, CallbackInfo ci) {
        if (PlayerAPI.displayGUIDispenser((EntityPlayerSP) (Object) this, tileEntityDispenser)) {
            ci.cancel();
        }
    }

    /*@Inject(method = "onItemPickup", at = @At("HEAD"), cancellable = true)
    private void onOnItemPickup(Entity entity, int i, CallbackInfo ci) {
        if (PlayerAPI.onItemPickup((EntityPlayerSP) (Object) this, entity, i)) {
            ci.cancel();
        }
    }*/

    // TODO: onItemPickup

    @Redirect(method = "getPlayerArmorValue", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/InventoryPlayer;getTotalArmorValue()I"))
    private int redirectGetPlayerArmorValue(InventoryPlayer inventoryPlayer) {
        return PlayerAPI.getPlayerArmorValue((EntityPlayerSP) (Object) this, inventoryPlayer.getTotalArmorValue());
    }

    @Inject(method = "isSneaking", at = @At("RETURN"), cancellable = true)
    private void isIsSneaking(CallbackInfoReturnable<Boolean> cir) {
            cir.setReturnValue(PlayerAPI.isSneaking((EntityPlayerSP) (Object) this, cir.getReturnValue()));
    }

    @Override
    public float getCurrentPlayerStrVsBlock(Block block) {
        return PlayerAPI.getCurrentPlayerStrVsBlock((EntityPlayerSP) (Object) this, block, super.getCurrentPlayerStrVsBlock(block));
    }

    @Override
    public void heal(int i) {
        if (!PlayerAPI.heal(((EntityPlayerSP) (Object) this), i)) {
            super.heal(i);
        }
    }

    @Inject(method = "respawnPlayer", at = @At("HEAD"), cancellable = true)
    private void onRespawnPlayer(CallbackInfo ci) {
        if (PlayerAPI.respawn((EntityPlayerSP) (Object) this)) {
            ci.cancel();
        }
    }

    @Inject(method = "pushOutOfBlocks", at = @At("HEAD"), cancellable = true)
    private void onRespawnPlayer(double v, double v1, double v2, CallbackInfoReturnable<Boolean> cir) {
        if (PlayerAPI.pushOutOfBlocks((EntityPlayerSP) (Object) this, v, v1, v2)) {
            cir.setReturnValue(false);
        }
    }

    @Override
    public float getEntityBrightness(float v) {
        return PlayerAPI.getEntityBrightness((EntityPlayerSP) (Object) this, v, super.getEntityBrightness(v));
    }

    @Override
    public void onUpdate() {
        PlayerAPI.beforeUpdate((EntityPlayerSP) (Object) this);
        if (!PlayerAPI.onUpdate((EntityPlayerSP) (Object) this)) {
            super.onUpdate();
        }
        PlayerAPI.afterUpdate((EntityPlayerSP) (Object) this);
    }

    public void superMoveFlying(float f, float f1, float f2)
    {
        super.moveFlying(f, f1, f2);
    }

    @Override
    public void moveEntity(double v, double v1, double v2) {
        PlayerAPI.beforeMoveEntity((EntityPlayerSP) (Object) this, v, v1, v2);
        if (!PlayerAPI.moveEntity((EntityPlayerSP) (Object) this, v, v1, v2)) {
            super.moveEntity(v, v1, v2);
        }
        PlayerAPI.afterMoveEntity((EntityPlayerSP) (Object) this, v, v1, v2);
    }

    @Override
    public EnumStatus sleepInBedAt(int i, int i1, int i2) {
        PlayerAPI.beforeSleepInBedAt((EntityPlayerSP) (Object) this, i, i1, i2);
        EnumStatus enumstatus = PlayerAPI.sleepInBedAt((EntityPlayerSP) (Object) this, i, i1, i2);
        if(enumstatus == null)
        {
            return super.sleepInBedAt(i, i1, i2);
        } else
        {
            return enumstatus;
        }
    }

    public void doFall(float fallDist)
    {
        super.fall(fallDist);
    }

    public float getFallDistance()
    {
        return fallDistance;
    }

    public boolean getSleeping()
    {
        return sleeping;
    }

    public boolean getJumping()
    {
        return isJumping;
    }

    public void doJump()
    {
        jump();
    }

    public Random getRandom()
    {
        return rand;
    }

    public void setFallDistance(float f)
    {
        fallDistance = f;
    }

    public void setYSize(float f)
    {
        ySize = f;
    }

    @Override
    public void moveEntityWithHeading(float v, float v1) {
        if (!PlayerAPI.moveEntityWithHeading((EntityPlayerSP) (Object) this, v, v1)) {
            super.moveEntityWithHeading(v, v1);
        }
    }

    @Override
    public boolean isOnLadder() {
        return PlayerAPI.isOnLadder((EntityPlayerSP) (Object) this, super.isOnLadder());
    }

    public void setActionState(float newMoveStrafing, float newMoveForward, boolean newIsJumping)
    {
        moveStrafing = newMoveStrafing;
        moveForward = newMoveForward;
        isJumping = newIsJumping;
    }

    @Override
    public boolean isInsideOfMaterial(Material material) {
        return PlayerAPI.isInsideOfMaterial((EntityPlayerSP) (Object) this, material, super.isInsideOfMaterial(material));
    }

    @Override
    public void dropCurrentItem() {
        if (!PlayerAPI.dropCurrentItem((EntityPlayerSP) (Object) this)) {
            super.dropCurrentItem();
        }
    }

    @Override
    public void dropPlayerItem(ItemStack itemStack) {
        if (!PlayerAPI.dropPlayerItem((EntityPlayerSP) (Object) this, itemStack)) {
            super.dropPlayerItem(itemStack);
        }
    }

    public boolean superIsInsideOfMaterial(Material material)
    {
        return super.isInsideOfMaterial(material);
    }

    public float superGetEntityBrightness(float f)
    {
        return super.getEntityBrightness(f);
    }

    public void sendChatMessage(String s)
    {
        PlayerAPI.sendChatMessage((EntityPlayerSP) (Object) this, s);
    }

    @Override
    protected String getHurtSound() {
        String hurtSound = PlayerAPI.getHurtSound((EntityPlayerSP) (Object) this);
        if (hurtSound != null) {
            return hurtSound;
        }
        return super.getHurtSound();
    }

    public String superGetHurtSound()
    {
        return super.getHurtSound();
    }

    public float superGetCurrentPlayerStrVsBlock(Block block)
    {
        return super.getCurrentPlayerStrVsBlock(block);
    }


    @Override
    public boolean canHarvestBlock(Block block) {
        Boolean canHarvestBlock = PlayerAPI.canHarvestBlock((EntityPlayerSP) (Object) this, block);
        if (canHarvestBlock != null) {
            return canHarvestBlock;
        }
        return super.canHarvestBlock(block);
    }

    @Override
    protected void fall(float f)
    {
        if(!PlayerAPI.fall((EntityPlayerSP) (Object) this, f))
        {
            super.fall(f);
        }
    }

    public void superFall(float f)
    {
        super.fall(f);
    }

    @Override
    protected void jump()
    {
        if(!PlayerAPI.jump((EntityPlayerSP) (Object) this))
        {
            super.jump();
        }
    }

    public void superJump()
    {
        super.jump();
    }

    @Override
    protected void damageEntity(int i)
    {
        if(!PlayerAPI.damageEntity((EntityPlayerSP) (Object) this, i))
        {
            super.damageEntity(i);
        }
    }

    protected void superDamageEntity(int i)
    {
        super.damageEntity(i);
    }

    @Override
    public double getDistanceSqToEntity(Entity entity) {
        Double result = PlayerAPI.getDistanceSqToEntity((EntityPlayerSP) (Object) this, entity);
        if(result != null) {
            return result;
        }
        return super.getDistanceSqToEntity(entity);
    }

    public double superGetDistanceSqToEntity(Entity entity)
    {
        return super.getDistanceSqToEntity(entity);
    }

    public void attackTargetEntityWithCurrentItem(Entity entity)
    {
        if(!PlayerAPI.attackTargetEntityWithCurrentItem((EntityPlayerSP) (Object) this, entity))
        {
            super.attackTargetEntityWithCurrentItem(entity);
        }
    }

    public void superAttackTargetEntityWithCurrentItem(Entity entity)
    {
        super.attackTargetEntityWithCurrentItem(entity);
    }

    @Override
    public boolean handleWaterMovement() {
        Boolean result = PlayerAPI.handleWaterMovement((EntityPlayerSP) (Object) this);
        if(result != null) {
            return result;
        }
        return super.handleWaterMovement();
    }

    public boolean superHandleWaterMovement()
    {
        return super.handleWaterMovement();
    }

    @Override
    public boolean handleLavaMovement() {
        Boolean result = PlayerAPI.handleLavaMovement((EntityPlayerSP) (Object) this);
        if(result != null) {
            return result;
        }
        return super.handleLavaMovement();
    }

    public boolean superHandleLavaMovement()
    {
        return super.handleLavaMovement();
    }

    public void dropPlayerItemWithRandomChoice(ItemStack itemstack, boolean flag)
    {
        if(!PlayerAPI.dropPlayerItemWithRandomChoice((EntityPlayerSP) (Object) this, itemstack, flag))
        {
            super.dropPlayerItemWithRandomChoice(itemstack, flag);
        }
    }

    public void superDropPlayerItemWithRandomChoice(ItemStack itemstack, boolean flag)
    {
        super.dropPlayerItemWithRandomChoice(itemstack, flag);
    }
}
