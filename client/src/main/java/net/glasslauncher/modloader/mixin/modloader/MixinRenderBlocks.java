package net.glasslauncher.modloader.mixin.modloader;

import net.glasslauncher.modloader.ModLoader;
import net.minecraft.src.Block;
import net.minecraft.src.BlockRail;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderBlocks.class)
public abstract class MixinRenderBlocks {

    @Shadow
    protected abstract boolean func_31080_c(Block block, int i, int i1, int i2, boolean b);

    @Shadow
    protected abstract boolean func_31074_b(Block block, int i, int i1, int i2, boolean b);

    @Shadow
    protected abstract boolean renderBlockRepeater(Block block, int i, int i1, int i2);

    @Shadow
    protected abstract boolean renderBlockBed(Block block, int i, int i1, int i2);

    @Shadow
    public abstract boolean renderBlockLever(Block block, int i, int i1, int i2);

    @Shadow
    public abstract boolean renderBlockFence(Block block, int i, int i1, int i2);

    @Shadow
    public abstract boolean renderBlockStairs(Block block, int i, int i1, int i2);

    @Shadow
    public abstract boolean renderBlockMinecartTrack(BlockRail blockRail, int i, int i1, int i2);

    @Shadow
    public abstract boolean renderBlockDoor(Block block, int i, int i1, int i2);

    @Shadow
    public abstract boolean renderBlockLadder(Block block, int i, int i1, int i2);

    @Shadow
    public abstract boolean renderBlockRedstoneWire(Block block, int i, int i1, int i2);

    @Shadow
    public abstract boolean renderBlockFire(Block block, int i, int i1, int i2);

    @Shadow
    public abstract boolean renderBlockTorch(Block block, int i, int i1, int i2);

    @Shadow
    public abstract boolean renderBlockCrops(Block block, int i, int i1, int i2);

    @Shadow
    public abstract boolean renderBlockReed(Block block, int i, int i1, int i2);

    @Shadow
    public abstract boolean renderBlockCactus(Block block, int i, int i1, int i2);

    @Shadow
    public abstract boolean renderBlockFluids(Block block, int i, int i1, int i2);

    @Shadow
    public abstract boolean renderStandardBlock(Block block, int i, int i1, int i2);

    @Inject(method = "<init>(Lnet/minecraft/src/IBlockAccess;)V", at = @At(value = "RETURN"))
    public void initIBlockAccess(IBlockAccess iBlockAccess, CallbackInfo callbackInfo) {
        blockAccess = iBlockAccess;
        overrideBlockTexture = 0;
        flipTexture = false;
        renderAllFaces = false;
        field_31088_b = false;
        field_31087_g = 0;
        field_31086_h = 0;
        field_31085_i = 0;
        field_31084_j = 0;
        field_31083_k = 0;
        field_31082_l = 0;
        enableAO = false;
        lightValueOwn = 0.0F;
        aoLightValueXNeg = 0.0F;
        aoLightValueYNeg = 0.0F;
        aoLightValueZNeg = 0.0F;
        aoLightValueXPos = 0.0F;
        aoLightValueYPos = 0.0F;
        aoLightValueZPos = 0.0F;
        field_22377_m = 0.0F;
        field_22376_n = 0.0F;
        field_22375_o = 0.0F;
        field_22374_p = 0.0F;
        field_22373_q = 0.0F;
        field_22372_r = 0.0F;
        field_22371_s = 0.0F;
        field_22370_t = 0.0F;
        field_22369_u = 0.0F;
        field_22368_v = 0.0F;
        field_22367_w = 0.0F;
        field_22366_x = 0.0F;
        field_22365_y = 0.0F;
        field_22364_z = 0.0F;
        field_22362_A = 0.0F;
        field_22360_B = 0.0F;
        field_22358_C = 0.0F;
        field_22356_D = 0.0F;
        field_22354_E = 0.0F;
        field_22353_F = 0.0F;
        field_22352_G = 0;
        colorRedTopLeft = 0.0F;
        colorRedBottomLeft = 0.0F;
        colorRedBottomRight = 0.0F;
        colorRedTopRight = 0.0F;
        colorGreenTopLeft = 0.0F;
        colorGreenBottomLeft = 0.0F;
        colorGreenBottomRight = 0.0F;
        colorGreenTopRight = 0.0F;
        colorBlueTopLeft = 0.0F;
        colorBlueBottomLeft = 0.0F;
        colorBlueBottomRight = 0.0F;
        colorBlueTopRight = 0.0F;
        field_22339_T = false;
        field_22338_U = false;
        field_22337_V = false;
        field_22336_W = false;
        field_22335_X = false;
        field_22334_Y = false;
        field_22333_Z = false;
        field_22363_aa = false;
        field_22361_ab = false;
        field_22359_ac = false;
        field_22357_ad = false;
        field_22355_ae = false;
        overrideBlockTexture = -1;
        field_31088_b = true;
        field_22352_G = 1;
    }

    @Inject(method = "<init>()V", at = @At("RETURN"))
    public void init(CallbackInfo ci) {
        blockAccess = null;
        overrideBlockTexture = 0;
        flipTexture = false;
        renderAllFaces = false;
        field_31088_b = false;
        field_31087_g = 0;
        field_31086_h = 0;
        field_31085_i = 0;
        field_31084_j = 0;
        field_31083_k = 0;
        field_31082_l = 0;
        enableAO = false;
        lightValueOwn = 0.0F;
        aoLightValueXNeg = 0.0F;
        aoLightValueYNeg = 0.0F;
        aoLightValueZNeg = 0.0F;
        aoLightValueXPos = 0.0F;
        aoLightValueYPos = 0.0F;
        aoLightValueZPos = 0.0F;
        field_22377_m = 0.0F;
        field_22376_n = 0.0F;
        field_22375_o = 0.0F;
        field_22374_p = 0.0F;
        field_22373_q = 0.0F;
        field_22372_r = 0.0F;
        field_22371_s = 0.0F;
        field_22370_t = 0.0F;
        field_22369_u = 0.0F;
        field_22368_v = 0.0F;
        field_22367_w = 0.0F;
        field_22366_x = 0.0F;
        field_22365_y = 0.0F;
        field_22364_z = 0.0F;
        field_22362_A = 0.0F;
        field_22360_B = 0.0F;
        field_22358_C = 0.0F;
        field_22356_D = 0.0F;
        field_22354_E = 0.0F;
        field_22353_F = 0.0F;
        field_22352_G = 0;
        colorRedTopLeft = 0.0F;
        colorRedBottomLeft = 0.0F;
        colorRedBottomRight = 0.0F;
        colorRedTopRight = 0.0F;
        colorGreenTopLeft = 0.0F;
        colorGreenBottomLeft = 0.0F;
        colorGreenBottomRight = 0.0F;
        colorGreenTopRight = 0.0F;
        colorBlueTopLeft = 0.0F;
        colorBlueBottomLeft = 0.0F;
        colorBlueBottomRight = 0.0F;
        colorBlueTopRight = 0.0F;
        field_22339_T = false;
        field_22338_U = false;
        field_22337_V = false;
        field_22336_W = false;
        field_22335_X = false;
        field_22334_Y = false;
        field_22333_Z = false;
        field_22363_aa = false;
        field_22361_ab = false;
        field_22359_ac = false;
        field_22357_ad = false;
        field_22355_ae = false;
        overrideBlockTexture = -1;
        field_31088_b = true;
        field_22352_G = 1;
    }

    @Redirect(method = "renderBlockByRenderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Block;setBlockBoundsBasedOnState(Lnet/minecraft/src/IBlockAccess;III)V"))
    public void renderBlockByRenderType(Block block, IBlockAccess iBlockAccess, int i, int i1, int i2) {
        block.setBlockBoundsBasedOnState(this.blockAccess, i, i1, i2);
        ModLoader.renderWorldBlock((RenderBlocks) (Object) this, blockAccess, i, i1, i2, block, block.getRenderType());
    }

    @Shadow
    private IBlockAccess blockAccess;
    @Shadow
    private boolean flipTexture;
    @Shadow
    private boolean renderAllFaces;
    @Shadow
    public boolean field_31088_b;
    @Shadow
    private int overrideBlockTexture;
    @Shadow
    private int field_31087_g;
    @Shadow
    private int field_31086_h;
    @Shadow
    private int field_31085_i;
    @Shadow
    private int field_31084_j;
    @Shadow
    private int field_31083_k;
    @Shadow
    private int field_31082_l;
    @Shadow
    private boolean enableAO;
    @Shadow
    private float lightValueOwn;
    @Shadow
    private float aoLightValueXNeg;
    @Shadow
    private float aoLightValueYNeg;
    @Shadow
    private float aoLightValueZNeg;
    @Shadow
    private float aoLightValueXPos;
    @Shadow
    private float aoLightValueYPos;
    @Shadow
    private float aoLightValueZPos;
    @Shadow
    private float field_22377_m;
    @Shadow
    private float field_22376_n;
    @Shadow
    private float field_22375_o;
    @Shadow
    private float field_22374_p;
    @Shadow
    private float field_22373_q;
    @Shadow
    private float field_22372_r;
    @Shadow
    private float field_22371_s;
    @Shadow
    private float field_22370_t;
    @Shadow
    private float field_22369_u;
    @Shadow
    private float field_22368_v;
    @Shadow
    private float field_22367_w;
    @Shadow
    private float field_22366_x;
    @Shadow
    private float field_22365_y;
    @Shadow
    private float field_22364_z;
    @Shadow
    private float field_22362_A;
    @Shadow
    private float field_22360_B;
    @Shadow
    private float field_22358_C;
    @Shadow
    private float field_22356_D;
    @Shadow
    private float field_22354_E;
    @Shadow
    private float field_22353_F;
    @Shadow
    private int field_22352_G;
    @Shadow
    private float colorRedTopLeft;
    @Shadow
    private float colorRedBottomLeft;
    @Shadow
    private float colorRedBottomRight;
    @Shadow
    private float colorRedTopRight;
    @Shadow
    private float colorGreenTopLeft;
    @Shadow
    private float colorGreenBottomLeft;
    @Shadow
    private float colorGreenBottomRight;
    @Shadow
    private float colorGreenTopRight;
    @Shadow
    private float colorBlueTopLeft;
    @Shadow
    private float colorBlueBottomLeft;
    @Shadow
    private float colorBlueBottomRight;
    @Shadow
    private float colorBlueTopRight;
    @Shadow
    private boolean field_22339_T;
    @Shadow
    private boolean field_22338_U;
    @Shadow
    private boolean field_22337_V;
    @Shadow
    private boolean field_22336_W;
    @Shadow
    private boolean field_22335_X;
    @Shadow
    private boolean field_22334_Y;
    @Shadow
    private boolean field_22333_Z;
    @Shadow
    private boolean field_22363_aa;
    @Shadow
    private boolean field_22361_ab;
    @Shadow
    private boolean field_22359_ac;
    @Shadow
    private boolean field_22357_ad;
    @Shadow
    private boolean field_22355_ae;
}
