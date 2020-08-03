package net.glasslauncher.modloader;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import lombok.Getter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.launch.common.MappingConfiguration;
import net.fabricmc.loader.util.mappings.TinyRemapperMappingsHelper;
import net.fabricmc.mapping.tree.TinyMappingFactory;
import net.fabricmc.mapping.tree.TinyTree;
import net.fabricmc.tinyremapper.ClassInstance;
import net.fabricmc.tinyremapper.OutputConsumerPath;
import net.fabricmc.tinyremapper.TinyRemapper;
import net.glasslauncher.mixin.CraftingManagerAccessor;
import net.glasslauncher.modloadermp.*;
import net.glasslauncher.playerapi.ClientPlayerAccessor;
import net.glasslauncher.playerapi.PlayerAPI;
import net.glasslauncher.utils.ClassPath;
import net.minecraft.achievement.Achievement;
import net.minecraft.block.BlockBase;
import net.minecraft.client.GameStartupError;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StatEntity;
import net.minecraft.client.TexturePackManager;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.render.TextureBinder;
import net.minecraft.client.render.TileRenderer;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.entity.TileEntityRenderDispatcher;
import net.minecraft.client.render.tileentity.TileEntityRenderer;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.Session;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.PlaceableTileEntity;
import net.minecraft.item.tool.ToolBase;
import net.minecraft.level.TileView;
import net.minecraft.level.Level;
import net.minecraft.level.biome.Biome;
import net.minecraft.level.biome.Hell;
import net.minecraft.level.biome.Sky;
import net.minecraft.level.source.LevelSource;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeRegistry;
import net.minecraft.recipe.SmeltingRecipeRegistry;
import net.minecraft.sortme.GameRenderer;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.tileentity.TileEntityBase;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import javax.imageio.ImageIO;
import javax.tools.Tool;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.objectweb.asm.Opcodes.INVOKESPECIAL;

public class ModLoader {

    public static final boolean DEBUG = false;

    /* BEGIN MODLOADER */
    public static final Properties props = new Properties();
    public static final String VERSION = "ModLoader Beta 1.7.3";
    private static final List animList = new LinkedList();
    private static final Map blockModels = new HashMap();
    private static final Map blockSpecialInv = new HashMap();
    private static final File cfgdir;
    private static final File cfgfile;
    private static final Map inGameHooks = new HashMap();
    private static final Map inGUIHooks = new HashMap();
    private static final Map keyList = new HashMap();
    private static final File logfile = new File(Minecraft.getGameDirectory(), "ModLoader.txt");
    private static final Logger logger = Logger.getLogger("ModLoader");
    private static final File modDir = new File(Minecraft.getGameDirectory(), "/mods/");
    private static final File remappedModDir = new File(modDir, "/remapped/");
    private static final LinkedList<BaseMod> modList = new LinkedList<>();
    private static final Map overrides = new HashMap();
    private static final boolean[] usedItemSprites = new boolean[256];
    private static final boolean[] usedTerrainSprites = new boolean[256];
    public static java.util.logging.Level cfgLoggingLevel;
    public static boolean hasInit = false;
    private static Map classMap = null;
    private static long clock = 0L;
    private static Field field_animList = null;
    private static Field field_armorList = null;
    private static Field field_blockList = null;
    private static Field field_modifiers = null;
    private static Field field_TileEntityRenderers = null;
    private static int highestEntityId = 3000;
    private static Minecraft instance = null;
    private static int itemSpriteIndex = 0;
    private static int itemSpritesLeft = 0;
    private static FileHandler logHandler = null;
    private static Method method_RegisterEntityID = null;
    private static Method method_RegisterTileEntity = null;
    private static int nextBlockModelID = 1000;
    private static Biome[] standardBiomes;
    private static int terrainSpriteIndex = 0;
    private static int terrainSpritesLeft = 0;
    private static String texPack = null;
    private static boolean texturesAdded = false;
    @Getter
    private static float[][] redstoneColors;

    static {
        cfgdir = new File(Minecraft.getGameDirectory(), "/config/");
        cfgfile = new File(cfgdir, "ModLoader.cfg");
        cfgLoggingLevel = java.util.logging.Level.FINER;

        redstoneColors = new float[16][];
        for (int i = 0; i < redstoneColors.length; i++) {
            float j = (float) i / 15F;
            float red = j * 0.6F + 0.4F;
            if (i == 0) {
                j = 0.0F;
            }
            float green = j * j * 0.7F - 0.5F;
            float blue = j * j * 0.6F - 0.7F;
            if (green < 0.0F) {
                green = 0.0F;
            }
            if (blue < 0.0F) {
                blue = 0.0F;
            }
            redstoneColors[i] = (new float[]{
                    red, green, blue
            });
        }
    }

    private ModLoader() {
    }

    public static void setRedstoneColors(float[][] colors) {
        if (colors.length != 16) {
            throw new IllegalArgumentException("Must be 16 colors.");
        }
        for (int i = 0; i < colors.length; i++) {
            if (colors[i].length != 3) {
                throw new IllegalArgumentException("Must be 3 channels in a color.");
            }
        }

        redstoneColors = colors;
    }

    @Environment(EnvType.CLIENT)
    public static void addAchievementDesc(Achievement achievement, String name, String description) {
        try {
            if (achievement.name.contains(".")) {
                String[] split = achievement.name.split("\\.");
                if (split.length == 2) {
                    String key = split[1];
                    addLocalization("achievement." + key, name);
                    addLocalization("achievement." + key + ".desc", description);
                    setPrivateValue(Stat.class, achievement, 1, TranslationStorage.getInstance().translate("achievement." + key));
                    setPrivateValue(Achievement.class, achievement, 3, TranslationStorage.getInstance().translate("achievement." + key + ".desc"));
                } else {
                    setPrivateValue(Stat.class, achievement, 1, name);
                    setPrivateValue(Achievement.class, achievement, 3, description);
                }
            } else {
                setPrivateValue(Stat.class, achievement, 1, name);
                setPrivateValue(Achievement.class, achievement, 3, description);
            }
        } catch (IllegalArgumentException | NoSuchFieldException | SecurityException e) {
            logger.throwing("ModLoader", "AddAchievementDesc", e);
            throwException(e);
        }
    }

    public static int addAllFuel(int id) {
        logger.finest("Finding fuel for " + id);
        int result = 0;
        for (Iterator iter = modList.iterator(); iter.hasNext() && result == 0; result = ((BaseMod) iter.next()).addFuel(id)) {
        }
        if (result != 0) {
            logger.finest("Returned " + result);
        }
        return result;
    }

    @Environment(EnvType.CLIENT)
    public static void addAllRenderers(Map o) {
        if (!hasInit) {
            init();
            logger.fine("Initialized");
        }
        BaseMod mod;
        for (Iterator iterator = modList.iterator(); iterator.hasNext(); mod.addRenderer(o)) {
            mod = (BaseMod) iterator.next();
        }

    }

    @Environment(EnvType.CLIENT)
    public static void addAnimation(TextureBinder anim) {
        logger.finest("Adding animation " + anim.toString());
        for (Iterator iterator = animList.iterator(); iterator.hasNext(); ) {
            TextureBinder oldAnim = (TextureBinder) iterator.next();
            if (oldAnim.renderMode == anim.renderMode && oldAnim.index == anim.index) {
                animList.remove(anim);
                break;
            }
        }

        animList.add(anim);
    }

    @Environment(EnvType.CLIENT)
    public static int addArmor(String armor) {
        try {
            String[] existingArmor = (String[]) field_armorList.get(null);
            List existingArmorList = Arrays.asList(existingArmor);
            List combinedList = new ArrayList();
            combinedList.addAll(existingArmorList);
            if (!combinedList.contains(armor)) {
                combinedList.add(armor);
            }
            int index = combinedList.indexOf(armor);
            field_armorList.set(null, combinedList.toArray(new String[0]));
            return index;
        } catch (IllegalArgumentException | IllegalAccessException e) {
            logger.throwing("ModLoader", "AddArmor", e);
            throwException("An impossible error has occured!", e);
        }
        return -1;
    }

    @Environment(EnvType.CLIENT)
    public static void addLocalization(String key, String value) {
        Properties props = null;
        try {
            props = (Properties) getPrivateValue(TranslationStorage.class, TranslationStorage.getInstance(), 1);
        } catch (SecurityException | NoSuchFieldException e) {
            logger.throwing("ModLoader", "AddLocalization", e);
            throwException(e);
        }
        if (props != null) {
            props.put(key, value);
        }
    }

    @Environment(EnvType.CLIENT)
    private static void addMod(ClassLoader loader, String filename) {
        try {
            ArrayList<String> parts = new ArrayList<>(Arrays.asList(filename.split("\\.")));
            parts.remove(filename.split("\\.").length - 1);
            String name = String.join(".", parts);
            System.out.println(name);
            if (name.contains("$")) {
                return;
            }
            if (props.containsKey(name) && (props.getProperty(name).equalsIgnoreCase("no") || props.getProperty(name).equalsIgnoreCase("off"))) {
                return;
            }
            Class instclass = loader.loadClass(name);
            if (!(BaseMod.class).isAssignableFrom(instclass)) {
                return;
            }
            setupProperties(instclass);
            BaseMod mod = (BaseMod) instclass.newInstance();
            if (mod != null) {
                modList.add(mod);
                logger.fine("Mod Loaded: \"" + mod.toString() + "\" from " + filename);
                System.out.println("Mod Loaded: " + mod.toString());
            }
        } catch (Throwable e) {
            logger.fine("Failed to load mod from \"" + filename + "\"");
            System.out.println("Failed to load mod from \"" + filename + "\"");
            logger.throwing("ModLoader", "addMod", e);
            throwException(e);
        }
    }

    @Environment(EnvType.CLIENT)
    public static void addName(Object instance, String name) {
        String tag = null;
        if (instance instanceof Item) {
            Item item = (Item) instance;
            if (item.method_1314() != null) {
                tag = item.method_1314() + ".name";
            }
        } else if (instance instanceof BlockBase) {
            BlockBase block = (BlockBase) instance;
            if (block.getName() != null) {
                tag = block.getName() + ".name";
            }
        } else if (instance instanceof ItemInstance) {
            ItemInstance stack = (ItemInstance) instance;
            if (stack.getTranslationKey() != null) {
                tag = stack.getTranslationKey() + ".name";
            }
        } else {
            Exception e = new Exception(instance.getClass().getName() + " cannot have name attached to it!");
            logger.throwing("ModLoader", "AddName", e);
            throwException(e);
        }
        if (tag != null) {
            addLocalization(tag, name);
        } else {
            Exception e = new Exception(instance + " is missing name tag!");
            logger.throwing("ModLoader", "AddName", e);
            throwException(e);
        }
    }

    @Environment(EnvType.CLIENT)
    public static int addOverride(String fileToOverride, String fileToAdd) {
        try {
            int i = getUniqueSpriteIndex(fileToOverride);
            addOverride(fileToOverride, fileToAdd, i);
            return i;
        } catch (Throwable e) {
            logger.throwing("ModLoader", "addOverride", e);
            throwException(e);
            throw new RuntimeException(e);
        }
    }

    public static void addOverride(String path, String overlayPath, int index) {
        int dst = -1;
        int left = 0;
        if (path.equals("/terrain.png")) {
            dst = 0;
            left = terrainSpritesLeft;
        } else if (path.equals("/gui/items.png")) {
            dst = 1;
            left = itemSpritesLeft;
        } else {
            return;
        }
        System.out.println("Overriding " + path + " with " + overlayPath + " @ " + index + ". " + left + " left.");
        logger.finer("addOverride(" + path + "," + overlayPath + "," + index + "). " + left + " left.");
        Map overlays = (Map) overrides.get(dst);
        if (overlays == null) {
            overlays = new HashMap();
            overrides.put(dst, overlays);
        }
        overlays.put(overlayPath, index);
    }

    public static void addRecipe(ItemInstance output, Object params[]) {
        try {
            ((CraftingManagerAccessor) RecipeRegistry.getInstance()).callAddShapedRecipe(output, params);
        } catch (Exception e) {
            logger.severe("Failed to add recipe!");
            e.printStackTrace();
        }
    }

    public static void addShapelessRecipe(ItemInstance output, Object params[]) {
        try {
            ((CraftingManagerAccessor) RecipeRegistry.getInstance()).callAddShapelessRecipe(output, params);
        } catch (Exception e) {
            logger.severe("Failed to add recipe!");
            e.printStackTrace();
        }
    }

    public static void addSmelting(int input, ItemInstance output) {
        SmeltingRecipeRegistry.getInstance().addSmeltingRecipe(input, output);
    }

    public static void addSpawn(Class entityClass, int weightedProb, EntityType spawnList) {
        addSpawn(entityClass, weightedProb, spawnList, null);
    }

    public static void addSpawn(Class entityClass, int weightedProb, EntityType spawnList, Biome biomes[]) {
        if (entityClass == null) {
            throw new IllegalArgumentException("entityClass cannot be null");
        }
        if (spawnList == null) {
            throw new IllegalArgumentException("spawnList cannot be null");
        }
        if (biomes == null) {
            biomes = standardBiomes;
        }
        for (int i = 0; i < biomes.length; i++) {
            List list = biomes[i].getSpawnList(spawnList);
            if (list != null) {
                boolean exists = false;
                for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
                    EntityEntry entry = (EntityEntry) iterator.next();
                    if (entry.entryClass == entityClass) {
                        entry.rarity = weightedProb;
                        exists = true;
                        break;
                    }
                }

                if (!exists) {
                    list.add(new EntityEntry(entityClass, weightedProb));
                }
            }
        }

    }

    public static void addSpawn(String entityName, int weightedProb, EntityType spawnList) {
        addSpawn(entityName, weightedProb, spawnList, null);
    }

    public static void addSpawn(String entityName, int weightedProb, EntityType spawnList, Biome biomes[]) {
        Class entityClass = (Class) classMap.get(entityName);
        if (entityClass != null && (EntityBase.class).isAssignableFrom(entityClass)) {
            addSpawn(entityClass, weightedProb, spawnList, biomes);
        }
    }

    public static boolean dispenseEntity(Level world, double x, double y, double z, int xVel, int zVel, ItemInstance item) {
        boolean result = false;
        for (Iterator iter = modList.iterator(); iter.hasNext() && !result; result = ((BaseMod) iter.next()).dispenseEntity(world, x, y, z, xVel, zVel, item)) {
        }
        return result;
    }

    public static List getLoadedMods() {
        return Collections.unmodifiableList(modList);
    }

    public static Logger getLogger() {
        return logger;
    }

    @Environment(EnvType.CLIENT)
    public static Minecraft getMinecraftInstance() {
        if (instance == null) {
            try {
                ThreadGroup group = Thread.currentThread().getThreadGroup();
                int count = group.activeCount();
                Thread[] threads = new Thread[count];
                group.enumerate(threads);
                for (int i = 0; i < threads.length; i++) {
                    if (!threads[i].getName().equals("Minecraft main thread")) {
                        continue;
                    }
                    instance = (Minecraft) getPrivateValue(java.lang.Thread.class, threads[i], "target");
                    break;
                }

            } catch (SecurityException | NoSuchFieldException e) {
                logger.throwing("ModLoader", "getMinecraftInstance", e);
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    @Environment(EnvType.CLIENT)
    public static Object getPrivateValue(Class instanceclass, Object instance, int fieldindex)
            throws IllegalArgumentException, SecurityException, NoSuchFieldException {
        try {
            Field f = instanceclass.getDeclaredFields()[fieldindex];
            f.setAccessible(true);
            return f.get(instance);
        } catch (IllegalAccessException e) {
            logger.throwing("ModLoader", "getPrivateValue", e);
            throwException("An impossible error has occured!", e);
            return null;
        }
    }

    @Environment(EnvType.CLIENT)
    public static Object getPrivateValue(Class instanceclass, Object instance, String field)
            throws IllegalArgumentException, SecurityException, NoSuchFieldException {
        try {
            Field f = instanceclass.getDeclaredField(field);
            f.setAccessible(true);
            return f.get(instance);
        } catch (IllegalAccessException e) {
            logger.throwing("ModLoader", "getPrivateValue", e);
            throwException("An impossible error has occured!", e);
            return null;
        }
    }

    public static int getUniqueBlockModelID(BaseMod mod, boolean full3DItem) {
        int id = nextBlockModelID++;
        blockModels.put(id, mod);
        blockSpecialInv.put(id, full3DItem);
        return id;
    }

    public static int getUniqueEntityId() {
        return highestEntityId++;
    }

    @Environment(EnvType.CLIENT)
    private static int getUniqueItemSpriteIndex() {
        for (; itemSpriteIndex < usedItemSprites.length; itemSpriteIndex++) {
            if (!usedItemSprites[itemSpriteIndex]) {
                usedItemSprites[itemSpriteIndex] = true;
                itemSpritesLeft--;
                return itemSpriteIndex++;
            }
        }

        Exception e = new Exception("No more empty item sprite indices left!");
        logger.throwing("ModLoader", "getUniqueItemSpriteIndex", e);
        throwException(e);
        return 0;
    }

    @Environment(EnvType.CLIENT)
    public static int getUniqueSpriteIndex(String path) {
        if (path.equals("/gui/items.png")) {
            return getUniqueItemSpriteIndex();
        }
        if (path.equals("/terrain.png")) {
            return getUniqueTerrainSpriteIndex();
        } else {
            Exception e = new Exception("No registry for this texture: " + path);
            logger.throwing("ModLoader", "getUniqueItemSpriteIndex", e);
            throwException(e);
            return 0;
        }
    }

    @Environment(EnvType.CLIENT)
    private static int getUniqueTerrainSpriteIndex() {
        for (; terrainSpriteIndex < usedTerrainSprites.length; terrainSpriteIndex++) {
            if (!usedTerrainSprites[terrainSpriteIndex]) {
                usedTerrainSprites[terrainSpriteIndex] = true;
                terrainSpritesLeft--;
                return terrainSpriteIndex++;
            }
        }

        Exception e = new Exception("No more empty terrain sprite indices left!");
        logger.throwing("ModLoader", "getUniqueItemSpriteIndex", e);
        throwException(e);
        return 0;
    }

    private static final byte[] readFully(InputStream stream) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(stream.available());
            int r;
            while ((r = stream.read()) != -1)
                bos.write(r);

            return bos.toByteArray();
        } catch (Throwable t) {
            return new byte[0];
        }
    }

    private static final byte[] getClassBytes(String name) throws IOException {
        InputStream classStream = null;
        try {
            URL classResource = ModLoader.class.getResource(name.replace('.', '/').concat(".class"));
            if (classResource == null)
                return null;
            classStream = classResource.openStream();
            return readFully(classStream);
        }
        finally {
            if (classStream != null)
                try {
                    classStream.close();
                } catch (IOException e) {}
        }
    }

    private static byte[] transformClass(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        String parentEntityRenderer = Tool.class.getName().replace(".", "/");
        for (MethodNode method : classNode.methods) {
            Iterator<AbstractInsnNode> insns = method.instructions.iterator();
            for (AbstractInsnNode insn = insns.next();insns.hasNext();insn = insns.next())
                if (insn.getOpcode() == INVOKESPECIAL && ((MethodInsnNode)insn).owner.equals(classNode.superName))
                    ((MethodInsnNode)insn).owner = parentEntityRenderer;
        }
        classNode.superName = parentEntityRenderer;
        ClassWriter classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        bytes = classWriter.toByteArray();
        return bytes;
    }

    @Environment(EnvType.CLIENT)
    @SuppressWarnings("UnstableApiUsage")
    public static void init() {
        hasInit = true;
        try {
            transformClass(getClassBytes(ToolBase.class.getName().replace(".", "/")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*org.apache.logging.log4j.Logger[] loggers = new org.apache.logging.log4j.Logger[]{};
        loggerContext.getLoggers().toArray(loggers);
        for (org.apache.logging.log4j.Logger newLogger : loggers) {
            newLogger.
        }*/

        String usedItemSpritesString = "1111111111111111111111111111111111111101111111011111111111111001111111111111111111111111111011111111100110000011111110000000001111111001100000110000000100000011000000010000001100000000000000110000000000000000000000000000000000000000000000001100000000000000";
        String usedTerrainSpritesString = "1111111111111111111111111111110111111111111111111111110111111111111111111111000111111011111111111111001111111110111111111111100011111111000010001111011110000000111111000000000011111100000000001111000000000111111000000000001101000000000001111111111111000011";
        for (int i = 0; i < 256; i++) {
            usedItemSprites[i] = usedItemSpritesString.charAt(i) == '1';
            if (!usedItemSprites[i]) {
                itemSpritesLeft++;
            }
            usedTerrainSprites[i] = usedTerrainSpritesString.charAt(i) == '1';
            if (!usedTerrainSprites[i]) {
                terrainSpritesLeft++;
            }
        }

        try {
            instance = (Minecraft) getPrivateValue(Minecraft.class, null, 1);
            instance.gameRenderer = new GameRenderer(instance);
            classMap = (Map) getPrivateValue(EntityRegistry.class, null, 0);
            field_modifiers = (java.lang.reflect.Field.class).getDeclaredField("modifiers");
            field_modifiers.setAccessible(true);
            field_blockList = (Session.class).getDeclaredFields()[0];
            field_blockList.setAccessible(true);
            field_TileEntityRenderers = (TileEntityRenderer.class).getDeclaredFields()[0];
            field_TileEntityRenderers.setAccessible(true);
            field_armorList = (PlayerRenderer.class).getDeclaredFields()[3];
            field_modifiers.setInt(field_armorList, field_armorList.getModifiers() & 0xffffffef);
            field_armorList.setAccessible(true);
            field_animList = (TileRenderer.class).getDeclaredFields()[6];
            field_animList.setAccessible(true);
            Field[] fieldArray = (Biome.class).getDeclaredFields();
            List biomes = new LinkedList();
            for (int i = 0; i < fieldArray.length; i++) {
                Class fieldType = fieldArray[i].getType();
                if ((fieldArray[i].getModifiers() & 8) != 0 && fieldType.isAssignableFrom(Biome.class)) {
                    Biome biome = (Biome) fieldArray[i].get(null);
                    if (!(biome instanceof Hell) && !(biome instanceof Sky)) {
                        biomes.add(biome);
                    }
                }
            }

            standardBiomes = (Biome[]) biomes.toArray(new Biome[0]);
            try {
                method_RegisterTileEntity = (TileEntityBase.class).getDeclaredMethod("a", Class.class, String.class);
            } catch (NoSuchMethodException e) {
                method_RegisterTileEntity = (TileEntityBase.class).getDeclaredMethod("register", Class.class, String.class);
            }
            method_RegisterTileEntity.setAccessible(true);
            try {
                method_RegisterEntityID = (EntityRegistry.class).getDeclaredMethod("a", Class.class, String.class, Integer.TYPE);
            } catch (NoSuchMethodException e) {
                method_RegisterEntityID = (EntityRegistry.class).getDeclaredMethod("register", Class.class, String.class, int.class);
            }
            method_RegisterEntityID.setAccessible(true);
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | NoSuchFieldException e) {
            logger.throwing("ModLoader", "init", e);
            throwException(e);
            throw new RuntimeException(e);
        }
        try {
            loadConfig();
            if (props.containsKey("loggingLevel")) {
                cfgLoggingLevel = java.util.logging.Level.parse(props.getProperty("loggingLevel"));
            }
            // TODO: Grass Fix!
            /*if(props.containsKey("grassFix"))
            {
                RenderBlocks.cfgGrassFix = Boolean.parseBoolean(props.getProperty("grassFix"));
            }*/
            logger.setLevel(cfgLoggingLevel);
            if ((logfile.exists() || logfile.createNewFile()) && logfile.canWrite() && logHandler == null) {
                logHandler = new FileHandler(logfile.getPath());
                logHandler.setFormatter(new SimpleFormatter());
                logger.addHandler(logHandler);
            }
            logger.fine("ModLoader Beta 1.7.3 Initializing...");
            System.out.println("ModLoader Beta 1.7.3 Initializing...");
            modDir.mkdirs();
            remappedModDir.mkdirs();
            (new File("libraries")).mkdirs();

            // Mods remapping
            List<String> modsClasses = new ArrayList<>();
            HashMap<File, File> modsFiles = new HashMap<>();

            if (!(new File("libraries/ModLoader.jar")).exists()) {
                net.glasslauncher.utils.FileUtils.downloadFile("https://files.pymcl.net/client/b1.7.3/ModLoader.jar", "libraries");
            }

            if (!(new File("libraries/b1.7.3.jar")).exists()) {
                net.glasslauncher.utils.FileUtils.downloadFile("https://launcher.mojang.com/v1/objects/43db9b498cb67058d2e12d394e6507722e71bb45/client.jar", "libraries", null, "b1.7.3.jar");
            }

            for (File modFile : Objects.requireNonNull(modDir.listFiles())) {
                String extension = FilenameUtils.getExtension(modFile.getName());
                if (modFile.isFile() && (extension.equalsIgnoreCase("jar") || extension.equalsIgnoreCase("zip"))) {
                    modsFiles.put(modFile, new File(remappedModDir, modFile.getName().replace(extension, "remapped.jar")));
                    ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(modFile));
                    while (true) {
                        ZipEntry entry = zipInputStream.getNextEntry();
                        if (entry == null) {
                            zipInputStream.close();
                            break;
                        }

                        if (!entry.isDirectory()) {
                            modsClasses.add(entry.getName().replace(".class", ""));
                        }
                    }
                }
            }

            List<Class<?>> fixPackage = Arrays.asList(
                    BaseMod.class, EntityRendererProxy.class, MLProp.class, ModLoader.class, ModTextureAnimation.class, ModTextureStatic.class, // ModLoader
                    PlayerAPI.class, PlayerBase.class, // PlayerAPI
                    BaseModMp.class, ISpawnable.class, ModLoaderMp.class, NetClientHandlerEntity.class, Packet230ModLoader.class // ModLoaderMP
                    );
            for (Map.Entry<File, File> entry : modsFiles.entrySet()) {
                File file = entry.getKey();
                File remappedFile = entry.getValue();

                if (Files.exists(FileSystems.newFileSystem(file.toPath(), null).getPath("fabric.mod.json"))) {
                    System.out.println(file.getName() + " is a fabric mod, skipping mapping");
                    continue;
                }

                HashCode hash = com.google.common.io.Files.asByteSource(file).hash(Hashing.sha256());
                File hashFile = new File(remappedModDir, file.getName() + ".sha256");

                if (hashFile.exists() && remappedFile.exists() && HashCode.fromString(FileUtils.readFileToString(hashFile, Charsets.UTF_8)).equals(hash)) {
                    ClassPath.addURL(remappedFile.toURI().toURL());
                    System.out.println(file.getName() + " had valid hash, skipping mapping");
                    continue;
                }

                TinyTree mappings = TinyMappingFactory.loadWithDetection(new BufferedReader(new InputStreamReader(Objects.requireNonNull(ModLoader.class.getClassLoader().getResourceAsStream("assets/modloader/mappings.tiny")))));
                TinyTree fabricMappings = new MappingConfiguration().getMappings();
                mappings.getClasses().addAll(fabricMappings.getClasses());
                mappings.getDefaultNamespaceClassMap().putAll(fabricMappings.getDefaultNamespaceClassMap());

                remapper = TinyRemapper.newRemapper()
                        .withMappings(TinyRemapperMappingsHelper.create(mappings, "official", "named"))
                        .extraRemapper(new Remapper() {
                            @Override
                            public String map(String internalName) {
                                Class<?> aClass = fixPackage.stream().filter(c -> c.getSimpleName().equals(internalName)).findAny().orElse(null);
                                if (aClass != null) {
                                    return super.map(aClass.getName().replace(".", "/"));
                                }
                                return super.map(internalName);
                            }

                            @Override
                            public String mapMethodName(String owner, String name, String descriptor) {
                                boolean fixCasing = fixPackage.stream().anyMatch(c -> c.getSimpleName().equals(owner));
                                if (!fixCasing) {
                                    ClassInstance classInstance = remapper.getClasses().get(owner);
                                    if (classInstance != null) {
                                        fixCasing = fixPackage.stream().anyMatch(c -> c.getSimpleName().equals(classInstance.getSuperName()));
                                    }
                                }
                                if (fixCasing) {
                                    return super.mapMethodName(owner, StringUtils.uncapitalize(name), descriptor);
                                }
                                return super.mapMethodName(owner, name, descriptor);
                            }
                        })
                        .extraVisitor((visitor, remapper1) -> new ClassRemapper(visitor, remapper1) {
                            @Override
                            protected MethodVisitor createMethodRemapper(MethodVisitor mv) {
                                return new MethodVisitor(Opcodes.ASM7, mv) {
                                    @Override
                                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);

                                        // Workaround for mods accessing URLClassLoader and fabric's classloader
                                        if (owner.equals("java/lang/Class") && name.equals("getClassLoader") && descriptor.equals("()Ljava/lang/ClassLoader;")) {
                                            System.out.println("injecting getParent to classloader");
                                            super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/ClassLoader", "getParent", "()Ljava/lang/ClassLoader;", false);
                                        }
                                    }

                                    @Override
                                    public void visitLdcInsn(Object value) {
                                        // mine_diver dumbie
                                        if (value.equals("net.minecraft.src.")) {
                                            super.visitLdcInsn("");
                                            return;
                                        }

                                        super.visitLdcInsn(value);
                                    }
                                };
                            }
                        })
                        .extraClassNameMapper((tinyRemapper, def) -> def)
                        .build();
                OutputConsumerPath outputConsumer = new OutputConsumerPath.Builder(remappedFile.toPath()).build();
                outputConsumer.addNonClassFiles(file.toPath());
                remapper.readClassPath(modsFiles.keySet().stream().filter(x -> x != file).map(File::toPath).toArray(Path[]::new));
                remapper.readClassPath(Paths.get("libraries", "b1.7.3.jar"), Paths.get("libraries", "ModLoader.jar"), Paths.get("libraries", "ModLoaderMP.jar"), Paths.get("libraries", "PlayerAPI.jar"));
                remapper.readInputs(file.toPath());

                remapper.apply(outputConsumer);
                outputConsumer.close();
                remapper.finish();

                ClassPath.addURL(remappedFile.toURI().toURL());

                FileUtils.writeStringToFile(hashFile, hash.toString(), Charsets.UTF_8);
                System.out.println(file.getName() + " remapped successfully!");
            }

            // Loading mods from classpath
            for (URL url : ClassPath.getURLs()) {
                try {
                    loadMod(new File(url.toURI()), null);
                } catch (Exception e) {
                    System.out.println("WARN: Failed to check \"" + url + "\" for mods.");
                    e.printStackTrace();
                }
            }

            System.out.println("Done.");
            props.setProperty("loggingLevel", cfgLoggingLevel.getName());
            // TODO: Grass Fix!
            //props.setProperty("grassFix", Boolean.toString(RenderBlocks.cfgGrassFix));
            for (Iterator iterator = modList.iterator(); iterator.hasNext(); ) {
                BaseMod mod = (BaseMod) iterator.next();
                mod.modsLoaded();
                if (!props.containsKey(mod.getClass().getName())) {
                    props.setProperty(mod.getClass().getName(), "on");
                }
            }

            instance.isApplet = false;
            instance.options.keyBindings = registerAllKeys(instance.options.keyBindings);
            instance.options.load();
            initStats();
            saveConfig();
        } catch (Throwable e) {
            logger.throwing("ModLoader", "init", e);
            throwException("ModLoader has failed to initialize.", e);
            if (logHandler != null) {
                logHandler.close();
            }
            throw new RuntimeException(e);
        }
    }

    // workaround for accessing remapper inside anonymous class ;-;
    protected static TinyRemapper remapper;

    private static void initStats() {
        Map oneShotStats = StatListWorkAround.getOneShotStats();

        for (int id = 0; id < BlockBase.BY_ID.length; id++) {
            if (!oneShotStats.containsKey(0x1000000 + id) && BlockBase.BY_ID[id] != null && BlockBase.BY_ID[id].isStatEnabled()) {
                String str = TranslationStorage.getInstance().translate("stat.mineBlock", BlockBase.BY_ID[id].getTranslatedName());
                Stats.STAT_MINE_BLOCK[id] = (new StatEntity(0x1000000 + id, str, id)).register();
                Stats.field_818.add(Stats.STAT_MINE_BLOCK[id]);
            }
        }

        for (int id = 0; id < ItemBase.byId.length; id++) {
            if (!oneShotStats.containsKey(0x1020000 + id) && ItemBase.byId[id] != null) {
                String str = TranslationStorage.getInstance().translate("stat.useItem", ItemBase.byId[id].getTranslatedName());
                Stats.useItem[id] = (new StatEntity(0x1020000 + id, str, id)).register();
                if (id >= BlockBase.BY_ID.length) {
                    Stats.field_817.add(StatListWorkAround.useItem[id]);
                }
            }
            if (!oneShotStats.containsKey(0x1030000 + id) && ItemBase.byId[id] != null && ItemBase.byId[id].method_465()) {
                String str = TranslationStorage.getInstance().translate("stat.breakItem", ItemBase.byId[id].getTranslatedName());
                Stats.breakItem[id] = (new StatEntity(0x1030000 + id, str, id)).register();
            }
        }

        HashSet idHashSet = new HashSet();
        Object result;
        for (Iterator iterator = RecipeRegistry.getInstance().getRecipes().iterator(); iterator.hasNext(); idHashSet.add(((Recipe) result).getOutput().itemId)) {
            result = iterator.next();
        }

        for (Iterator iterator1 = SmeltingRecipeRegistry.getInstance().getRecipes().values().iterator(); iterator1.hasNext(); idHashSet.add(((ItemInstance) result).itemId)) {
            result = iterator1.next();
        }

        for (Iterator iterator2 = idHashSet.iterator(); iterator2.hasNext(); ) {
            int id = (Integer) iterator2.next();
            if (!oneShotStats.containsKey(0x1010000 + id) && ItemBase.byId[id] != null) {
                String str = TranslationStorage.getInstance().translate("stat.craftItem", ItemBase.byId[id].getTranslatedName());
                Stats.field_809[id] = (new StatEntity(0x1010000 + id, str, id)).register();
            }
        }

    }

    @Environment(EnvType.CLIENT)
    public static boolean isGUIOpen(Class gui) {
        Minecraft game = getMinecraftInstance();
        if (gui == null) {
            return game.currentScreen == null;
        }
        if (game.currentScreen == null) {
            return false;
        } else {
            return gui.isInstance(game.currentScreen);
        }
    }

    public static boolean isModLoaded(String modname) {
        Class chk = null;
        try {
            chk = Class.forName(modname);
        } catch (ClassNotFoundException e) {
            return false;
        }
        if (chk != null) {
            for (Iterator iterator = modList.iterator(); iterator.hasNext(); ) {
                BaseMod mod = (BaseMod) iterator.next();
                if (chk.isInstance(mod)) {
                    return true;
                }
            }

        }
        return false;
    }

    public static void loadConfig()
            throws IOException {
        cfgdir.mkdir();
        if (!cfgfile.exists() && !cfgfile.createNewFile()) {
            return;
        }
        if (cfgfile.canRead()) {
            InputStream in = new FileInputStream(cfgfile);
            props.load(in);
            in.close();
        }
    }

    @Environment(EnvType.CLIENT)
    public static java.awt.image.BufferedImage loadImage(TextureManager texCache, String path)
            throws Exception {
        TexturePackManager pack = (TexturePackManager) getPrivateValue(TextureManager.class, texCache, 11);
        InputStream input = pack.texturePack.method_976(path);
        if (input == null) {
            throw new Exception("Image not found: " + path);
        }
        java.awt.image.BufferedImage image = ImageIO.read(input);
        if (image == null) {
            throw new Exception("Image corrupted: " + path);
        } else {
            return image;
        }
    }

    public static void onItemPickup(PlayerBase player, ItemInstance item) {
        BaseMod mod;
        for (Iterator iterator = modList.iterator(); iterator.hasNext(); mod.onItemPickup(player, item)) {
            mod = (BaseMod) iterator.next();
        }

    }

    @Environment(EnvType.CLIENT)
    public static void onTick(Minecraft game) {
        if (!hasInit) {
            init();
            logger.fine("Initialized");
        }
        if (!game.options.skin.equals(texPack)) {
            texturesAdded = false;
            texPack = game.options.skin;
        }
        if (!texturesAdded && game.gameRenderer != null) {
            registerAllTextureOverrides(game.textureManager);
            texturesAdded = true;
        }
        long newclock = 0L;
        if (game.level != null) {
            newclock = game.level.getLevelTime();
            for (Iterator iter = inGameHooks.entrySet().iterator(); iter.hasNext(); ) {
                java.util.Map.Entry modSet = (java.util.Map.Entry) iter.next();
                if ((clock != newclock || !(Boolean) modSet.getValue()) && !((BaseMod) modSet.getKey()).onTickInGame(game)) {
                    iter.remove();
                }
            }

        }
        if (game.currentScreen != null) {
            for (Iterator iter = inGUIHooks.entrySet().iterator(); iter.hasNext(); ) {
                java.util.Map.Entry modSet = (java.util.Map.Entry) iter.next();
                if ((clock != newclock || !((Boolean) modSet.getValue() & (game.level != null))) && !((BaseMod) modSet.getKey()).onTickInGUI(game, game.currentScreen)) {
                    iter.remove();
                }
            }

        }
        if (clock != newclock) {
            for (Object o : keyList.entrySet()) {
                Map.Entry modSet = (Map.Entry) o;
                for (Object value : ((Map) modSet.getValue()).entrySet()) {
                    Map.Entry keySet = (Map.Entry) value;
                    boolean state = Keyboard.isKeyDown(((KeyBinding) keySet.getKey()).key);
                    boolean[] keyInfo = (boolean[]) keySet.getValue();
                    boolean oldState = keyInfo[1];
                    keyInfo[1] = state;
                    if (state && (!oldState || keyInfo[0])) {
                        ((BaseMod) modSet.getKey()).keyboardEvent((KeyBinding) keySet.getKey());
                    }
                }

            }

        }
        clock = newclock;
    }

    @Environment(EnvType.CLIENT)
    public static void openGUI(PlayerBase player, ScreenBase gui) {
        if (!hasInit) {
            init();
            logger.fine("Initialized");
        }
        Minecraft game = getMinecraftInstance();
        if (game.player != player) {
            return;
        }
        if (gui != null) {
            game.openScreen(gui);
        }
    }

    @Environment(EnvType.CLIENT)
    public static void populateChunk(LevelSource generator, int chunkX, int chunkZ, Level world) {
        if (!hasInit) {
            init();
            logger.fine("Initialized");
        }
        Random rnd = new Random(world.getSeed());
        long xSeed = (rnd.nextLong() / 2L) * 2L + 1L;
        long zSeed = (rnd.nextLong() / 2L) * 2L + 1L;
        rnd.setSeed((long) chunkX * xSeed + (long) chunkZ * zSeed ^ world.getSeed());
        for (Iterator iterator = modList.iterator(); iterator.hasNext(); ) {
            BaseMod mod = (BaseMod) iterator.next();
            if (generator.toString().equals("RandomLevelSource")) {
                mod.generateSurface(world, rnd, chunkX << 4, chunkZ << 4);
            } else if (generator.toString().equals("HellRandomLevelSource")) {
                mod.generateNether(world, rnd, chunkX << 4, chunkZ << 4);
            }
        }

    }

    @Environment(EnvType.CLIENT)
    private static void loadMod(File source, File parent) throws IOException {
        logger.finer("Adding mods from " + source.getCanonicalPath());
        ClassLoader loader = (Minecraft.class).getClassLoader();
        if (source.getPath().endsWith(".class")) {
            String name = parent == null ? source.getPath() : source.getPath().substring(parent.getPath().length() + 1);
            if (source.getName().startsWith("mod_")) {
                addMod(loader, name.replace("\\", "/").replace("/", "."));
            }
        } else if (source.isFile()) {
            if ((source.getPath().endsWith(".jar") || source.getPath().endsWith(".zip"))) {
                logger.finer("Adding mods from " + source.getCanonicalPath());
                InputStream input = new FileInputStream(source);
                ZipInputStream zip = new ZipInputStream(input);
                ZipEntry entry = zip.getNextEntry();
                while (entry != null) {
                    loadMod(new File(entry.getName()), null);
                    entry = zip.getNextEntry();
                }
                zip.close();
                input.close();
            }
        } else if (source.isDirectory()) {
            logger.finer("Directory found.");
            List<File> files = Files.walk(source.toPath()).filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList());

            for (File file : files) {
                loadMod(file, source);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static KeyBinding[] registerAllKeys(KeyBinding w[]) {
        List combinedList = new LinkedList();
        combinedList.addAll(Arrays.asList(w));
        Map keyMap;
        for (Iterator iterator = keyList.values().iterator(); iterator.hasNext(); combinedList.addAll(keyMap.keySet())) {
            keyMap = (Map) iterator.next();
        }

        return (KeyBinding[]) combinedList.toArray(new KeyBinding[0]);
    }

    @Environment(EnvType.CLIENT)
    public static void registerAllTextureOverrides(TextureManager texCache) {
        animList.clear();
        Minecraft game = getMinecraftInstance();
        BaseMod mod;
        for (Iterator iterator = modList.iterator(); iterator.hasNext(); mod.registerAnimation(game)) {
            mod = (BaseMod) iterator.next();
        }

        TextureBinder anim;
        for (Iterator iterator1 = animList.iterator(); iterator1.hasNext(); texCache.add(anim)) {
            anim = (TextureBinder) iterator1.next();
        }

        for (Iterator iterator2 = overrides.entrySet().iterator(); iterator2.hasNext(); ) {
            java.util.Map.Entry overlay = (java.util.Map.Entry) iterator2.next();
            for (Iterator iterator3 = ((Map) overlay.getValue()).entrySet().iterator(); iterator3.hasNext(); ) {
                java.util.Map.Entry overlayEntry = (java.util.Map.Entry) iterator3.next();
                String overlayPath = (String) overlayEntry.getKey();
                int index = (Integer) overlayEntry.getValue();
                int dst = (Integer) overlay.getKey();
                try {
                    java.awt.image.BufferedImage im = loadImage(texCache, overlayPath);
                    anim = new ModTextureStatic(index, dst, im);
                    texCache.add(anim);
                } catch (Exception e) {
                    logger.throwing("ModLoader", "RegisterAllTextureOverrides", e);
                    throwException(e);
                    throw new RuntimeException(e);
                }
            }

        }

    }

    @Environment(EnvType.CLIENT)
    public static void registerBlock(BlockBase block) {
        registerBlock(block, null);
    }

    @Environment(EnvType.CLIENT)
    public static void registerBlock(BlockBase block, Class itemclass) {
        try {
            if (block == null) {
                throw new IllegalArgumentException("block parameter cannot be null.");
            }
            List list = (List) field_blockList.get(null);
            list.add(block);
            int id = block.id;
            PlaceableTileEntity item = null;
            if (itemclass != null) {
                item = (PlaceableTileEntity) itemclass.getConstructor(Integer.TYPE).newInstance(new Object[]{
                        id - 256
                });
            } else {
                item = new PlaceableTileEntity(id - 256);
            }
            if (BlockBase.BY_ID[id] != null && ItemBase.byId[id] == null) {
                ItemBase.byId[id] = item;
            }
        } catch (IllegalArgumentException | NoSuchMethodException | InvocationTargetException | InstantiationException | SecurityException | IllegalAccessException e) {
            logger.throwing("ModLoader", "RegisterBlock", e);
            throwException(e);
        }
    }

    @Environment(EnvType.CLIENT)
    public static void registerEntityID(Class entityClass, String entityName, int id) {
        try {
            method_RegisterEntityID.invoke(null, entityClass, entityName, id);
        } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
            logger.throwing("ModLoader", "RegisterEntityID", e);
            throwException(e);
        }
    }

    public static void registerKey(BaseMod mod, KeyBinding keyHandler, boolean allowRepeat) {
        Map keyMap = (Map) keyList.get(mod);
        if (keyMap == null) {
            keyMap = new HashMap();
        }
        boolean[] aflag = new boolean[2];
        aflag[0] = allowRepeat;
        keyMap.put(keyHandler, aflag);
        keyList.put(mod, keyMap);
    }

    @Environment(EnvType.CLIENT)
    public static void registerTileEntity(Class tileEntityClass, String id) {
        registerTileEntity(tileEntityClass, id, null);
    }

    @Environment(EnvType.CLIENT)
    public static void registerTileEntity(Class tileEntityClass, String id, TileEntityRenderer renderer) {
        try {
            method_RegisterTileEntity.invoke(null, tileEntityClass, id);
            if (renderer != null) {
                TileEntityRenderDispatcher ref = TileEntityRenderDispatcher.INSTANCE;
                Map renderers = (Map) field_TileEntityRenderers.get(ref);
                renderers.put(tileEntityClass, renderer);
                renderer.setRenderDispatcher(ref);
            }
        } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
            logger.throwing("ModLoader", "RegisterTileEntity", e);
            throwException(e);
        }
    }

    public static void removeSpawn(Class entityClass, EntityType spawnList) {
        removeSpawn(entityClass, spawnList, null);
    }

    public static void removeSpawn(Class entityClass, EntityType spawnList, Biome biomes[]) {
        if (entityClass == null) {
            throw new IllegalArgumentException("entityClass cannot be null");
        }
        if (spawnList == null) {
            throw new IllegalArgumentException("spawnList cannot be null");
        }
        if (biomes == null) {
            biomes = standardBiomes;
        }
        for (int i = 0; i < biomes.length; i++) {
            List list = biomes[i].getSpawnList(spawnList);
            if (list != null) {
                for (Iterator iter = list.iterator(); iter.hasNext(); ) {
                    EntityEntry entry = (EntityEntry) iter.next();
                    if (entry.entryClass == entityClass) {
                        iter.remove();
                    }
                }

            }
        }

    }

    public static void removeSpawn(String entityName, EntityType spawnList) {
        removeSpawn(entityName, spawnList, null);
    }

    public static void removeSpawn(String entityName, EntityType spawnList, Biome biomes[]) {
        Class entityClass = (Class) classMap.get(entityName);
        if (entityClass != null && (Living.class).isAssignableFrom(entityClass)) {
            removeSpawn(entityClass, spawnList, biomes);
        }
    }

    public static boolean renderBlockIsItemFull3D(int modelID) {
        if (!blockSpecialInv.containsKey(modelID)) {
            return modelID == 16;
        } else {
            return (Boolean) blockSpecialInv.get(modelID);
        }
    }

    public static void renderInvBlock(TileRenderer renderer, BlockBase block, int metadata, int modelID) {
        BaseMod mod = (BaseMod) blockModels.get(modelID);
        if (mod == null) {
            return;
        } else {
            mod.renderInvBlock(renderer, block, metadata, modelID);
            return;
        }
    }

    public static boolean renderWorldBlock(TileRenderer renderer, TileView world, int x, int y, int z, BlockBase block, int modelID) {
        BaseMod mod = (BaseMod) blockModels.get(modelID);
        if (mod == null) {
            return false;
        } else {
            return mod.renderWorldBlock(renderer, world, x, y, z, block, modelID);
        }
    }

    public static void saveConfig()
            throws IOException {
        cfgdir.mkdir();
        if (!cfgfile.exists() && !cfgfile.createNewFile()) {
            return;
        }
        if (cfgfile.canWrite()) {
            OutputStream out = new FileOutputStream(cfgfile);
            props.store(out, "ModLoader Config");
            out.close();
        }
    }

    public static void setInGameHook(BaseMod mod, boolean enable, boolean useClock) {
        if (enable) {
            inGameHooks.put(mod, useClock);
        } else {
            inGameHooks.remove(mod);
        }
    }

    public static void setInGUIHook(BaseMod mod, boolean enable, boolean useClock) {
        if (enable) {
            inGUIHooks.put(mod, useClock);
        } else {
            inGUIHooks.remove(mod);
        }
    }

    @Environment(EnvType.CLIENT)
    public static void setPrivateValue(Class instanceclass, Object instance, int fieldindex, Object value)
            throws IllegalArgumentException, SecurityException, NoSuchFieldException {
        try {
            Field f = instanceclass.getDeclaredFields()[fieldindex];
            f.setAccessible(true);
            int modifiers = field_modifiers.getInt(f);
            if ((modifiers & 0x10) != 0) {
                field_modifiers.setInt(f, modifiers & 0xffffffef);
            }
            f.set(instance, value);
        } catch (IllegalAccessException e) {
            logger.throwing("ModLoader", "setPrivateValue", e);
            throwException("An impossible error has occured!", e);
        }
    }

    @Environment(EnvType.CLIENT)
    public static void setPrivateValue(Class instanceclass, Object instance, String field, Object value)
            throws IllegalArgumentException, SecurityException, NoSuchFieldException {
        try {
            Field f = instanceclass.getDeclaredField(field);
            int modifiers = field_modifiers.getInt(f);
            if ((modifiers & 0x10) != 0) {
                field_modifiers.setInt(f, modifiers & 0xffffffef);
            }
            f.setAccessible(true);
            f.set(instance, value);
        } catch (IllegalAccessException e) {
            logger.throwing("ModLoader", "setPrivateValue", e);
            throwException("An impossible error has occured!", e);
        }
    }

    private static void setupProperties(Class mod)
            throws IllegalArgumentException, IllegalAccessException, IOException, SecurityException, NoSuchFieldException {
        Properties modprops = new Properties();
        File modcfgfile = new File(cfgdir, mod.getName() + ".cfg");
        if (modcfgfile.exists() && modcfgfile.canRead()) {
            modprops.load(new FileInputStream(modcfgfile));
        }
        StringBuilder helptext = new StringBuilder();
        Field[] afield;
        int j = (afield = mod.getFields()).length;
        for (int i = 0; i < j; i++) {
            Field field = afield[i];
            if ((field.getModifiers() & 8) == 0 || !field.isAnnotationPresent(MLProp.class)) {
                continue;
            }
            Class type = field.getType();
            MLProp annotation = field.getAnnotation(MLProp.class);
            String key = annotation.name().length() != 0 ? annotation.name() : field.getName();
            Object currentvalue = field.get(null);
            StringBuilder range = new StringBuilder();
            if (annotation.min() != (-1.0D / 0.0D)) {
                range.append(String.format(",>=%.1f", annotation.min()));
            }
            if (annotation.max() != (1.0D / 0.0D)) {
                range.append(String.format(",<=%.1f", annotation.max()));
            }
            StringBuilder info = new StringBuilder();
            if (annotation.info().length() > 0) {
                info.append(" -- ");
                info.append(annotation.info());
            }
            helptext.append(String.format("%s (%s:%s%s)%s\n", key, type.getName(), currentvalue, range, info));
            if (modprops.containsKey(key)) {
                String strvalue = modprops.getProperty(key);
                Object value = null;
                if (type.isAssignableFrom(java.lang.String.class)) {
                    value = strvalue;
                } else if (type.isAssignableFrom(Integer.TYPE)) {
                    value = Integer.parseInt(strvalue);
                } else if (type.isAssignableFrom(Short.TYPE)) {
                    value = Short.parseShort(strvalue);
                } else if (type.isAssignableFrom(Byte.TYPE)) {
                    value = Byte.parseByte(strvalue);
                } else if (type.isAssignableFrom(Boolean.TYPE)) {
                    value = Boolean.parseBoolean(strvalue);
                } else if (type.isAssignableFrom(Float.TYPE)) {
                    value = Float.parseFloat(strvalue);
                } else if (type.isAssignableFrom(Double.TYPE)) {
                    value = Double.parseDouble(strvalue);
                }
                if (value == null) {
                    continue;
                }
                if (value instanceof Number) {
                    double num = ((Number) value).doubleValue();
                    if (annotation.min() != (-1.0D / 0.0D) && num < annotation.min() || annotation.max() != (1.0D / 0.0D) && num > annotation.max()) {
                        continue;
                    }
                }
                logger.finer(key + " set to " + value);
                if (!value.equals(currentvalue)) {
                    field.set(null, value);
                }
            } else {
                logger.finer(key + " not in config, using default: " + currentvalue);
                modprops.setProperty(key, currentvalue.toString());
            }
        }

        if (!modprops.isEmpty() && (modcfgfile.exists() || modcfgfile.createNewFile()) && modcfgfile.canWrite()) {
            modprops.store(new FileOutputStream(modcfgfile), helptext.toString());
        }
    }

    public static void takenFromCrafting(PlayerBase player, ItemInstance item) {
        BaseMod mod;
        for (Iterator iterator = modList.iterator(); iterator.hasNext(); mod.takenFromCrafting(player, item)) {
            mod = (BaseMod) iterator.next();
        }

    }

    public static void takenFromFurnace(PlayerBase player, ItemInstance item) {
        BaseMod mod;
        for (Iterator iterator = modList.iterator(); iterator.hasNext(); mod.takenFromFurnace(player, item)) {
            mod = (BaseMod) iterator.next();
        }

    }

    @Environment(EnvType.CLIENT)
    public static void throwException(String message, Throwable e) {
        Minecraft game = getMinecraftInstance();
        if (game != null) {
            e.printStackTrace();
            game.onGameStartupFailure(new GameStartupError(message, e));
        } else {
            throw new RuntimeException(e);
        }
    }

    @Environment(EnvType.CLIENT)
    private static void throwException(Throwable e) {
        throwException("Exception occured in ModLoader", e);
    }
}
