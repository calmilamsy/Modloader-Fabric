package net.glasslauncher.modloader;


import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import lombok.Getter;
import net.fabricmc.loader.launch.common.MappingConfiguration;
import net.fabricmc.loader.util.mappings.TinyRemapperMappingsHelper;
import net.fabricmc.mapping.tree.TinyMappingFactory;
import net.fabricmc.mapping.tree.TinyTree;
import net.fabricmc.tinyremapper.ClassInstance;
import net.fabricmc.tinyremapper.OutputConsumerPath;
import net.fabricmc.tinyremapper.TinyRemapper;
import net.glasslauncher.modloader.cachemanager.utils.ClassPath;
import net.glasslauncher.modloader.mixin.CraftingManagerAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;

import javax.imageio.ImageIO;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ModLoader {

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

    /* BEGIN MODLOADER */

    public static void addAchievementDesc(Achievement achievement, String name, String description) {
        try {
            if (achievement.statName.contains(".")) {
                String[] split = achievement.statName.split("\\.");
                if (split.length == 2) {
                    String key = split[1];
                    addLocalization("achievement." + key, name);
                    addLocalization("achievement." + key + ".desc", description);
                    setPrivateValue(net.minecraft.src.StatBase.class, achievement, 1, StringTranslate.getInstance().translateKey("achievement." + key));
                    setPrivateValue(net.minecraft.src.Achievement.class, achievement, 3, StringTranslate.getInstance().translateKey("achievement." + key + ".desc"));
                } else {
                    setPrivateValue(net.minecraft.src.StatBase.class, achievement, 1, name);
                    setPrivateValue(net.minecraft.src.Achievement.class, achievement, 3, description);
                }
            } else {
                setPrivateValue(net.minecraft.src.StatBase.class, achievement, 1, name);
                setPrivateValue(net.minecraft.src.Achievement.class, achievement, 3, description);
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

    public static void addAnimation(TextureFX anim) {
        logger.finest("Adding animation " + anim.toString());
        for (Iterator iterator = animList.iterator(); iterator.hasNext(); ) {
            TextureFX oldAnim = (TextureFX) iterator.next();
            if (oldAnim.tileImage == anim.tileImage && oldAnim.iconIndex == anim.iconIndex) {
                animList.remove(anim);
                break;
            }
        }

        animList.add(anim);
    }

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

    public static void addLocalization(String key, String value) {
        Properties props = null;
        try {
            props = (Properties) getPrivateValue(net.minecraft.src.StringTranslate.class, StringTranslate.getInstance(), 1);
        } catch (SecurityException | NoSuchFieldException e) {
            logger.throwing("ModLoader", "AddLocalization", e);
            throwException(e);
        }
        if (props != null) {
            props.put(key, value);
        }
    }

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

    public static void addName(Object instance, String name) {
        String tag = null;
        if (instance instanceof Item) {
            Item item = (Item) instance;
            if (item.getItemName() != null) {
                tag = item.getItemName() + ".name";
            }
        } else if (instance instanceof Block) {
            Block block = (Block) instance;
            if (block.getBlockName() != null) {
                tag = block.getBlockName() + ".name";
            }
        } else if (instance instanceof ItemStack) {
            ItemStack stack = (ItemStack) instance;
            if (stack.getItemName() != null) {
                tag = stack.getItemName() + ".name";
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

    public static void addRecipe(ItemStack output, Object params[]) {
        try {
            ((CraftingManagerAccessor) CraftingManager.getInstance()).callAddRecipe(output, params);
        } catch (Exception e) {
            logger.severe("Failed to add recipe!");
            e.printStackTrace();
        }
    }

    public static void addShapelessRecipe(ItemStack output, Object params[]) {
        try {
            ((CraftingManagerAccessor) CraftingManager.getInstance()).callAddShapelessRecipe(output, params);
        } catch (Exception e) {
            logger.severe("Failed to add recipe!");
            e.printStackTrace();
        }
    }

    public static void addSmelting(int input, ItemStack output) {
        FurnaceRecipes.smelting().addSmelting(input, output);
    }

    public static void addSpawn(Class entityClass, int weightedProb, EnumCreatureType spawnList) {
        addSpawn(entityClass, weightedProb, spawnList, null);
    }

    public static void addSpawn(Class entityClass, int weightedProb, EnumCreatureType spawnList, BiomeGenBase biomes[]) {
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
            List list = biomes[i].getSpawnableList(spawnList);
            if (list != null) {
                boolean exists = false;
                for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
                    SpawnListEntry entry = (SpawnListEntry) iterator.next();
                    if (entry.entityClass == entityClass) {
                        entry.spawnRarityRate = weightedProb;
                        exists = true;
                        break;
                    }
                }

                if (!exists) {
                    list.add(new SpawnListEntry(entityClass, weightedProb));
                }
            }
        }

    }

    public static void addSpawn(String entityName, int weightedProb, EnumCreatureType spawnList) {
        addSpawn(entityName, weightedProb, spawnList, null);
    }

    public static void addSpawn(String entityName, int weightedProb, EnumCreatureType spawnList, BiomeGenBase biomes[]) {
        Class entityClass = (Class) classMap.get(entityName);
        if (entityClass != null && (net.minecraft.src.EntityLiving.class).isAssignableFrom(entityClass)) {
            addSpawn(entityClass, weightedProb, spawnList, biomes);
        }
    }

    public static boolean dispenseEntity(World world, double x, double y, double z, int xVel,
                                         int zVel, ItemStack item) {
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

    @SuppressWarnings("UnstableApiUsage")
    public static void init() {
        hasInit = true;
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
            instance = (Minecraft) getPrivateValue(net.minecraft.client.Minecraft.class, null, 1);
            instance.entityRenderer = new EntityRendererProxy(instance);
            classMap = (Map) getPrivateValue(net.minecraft.src.EntityList.class, null, 0);
            field_modifiers = (java.lang.reflect.Field.class).getDeclaredField("modifiers");
            field_modifiers.setAccessible(true);
            field_blockList = (net.minecraft.src.Session.class).getDeclaredFields()[0];
            field_blockList.setAccessible(true);
            field_TileEntityRenderers = (net.minecraft.src.TileEntityRenderer.class).getDeclaredFields()[0];
            field_TileEntityRenderers.setAccessible(true);
            field_armorList = (net.minecraft.src.RenderPlayer.class).getDeclaredFields()[3];
            field_modifiers.setInt(field_armorList, field_armorList.getModifiers() & 0xffffffef);
            field_armorList.setAccessible(true);
            field_animList = (net.minecraft.src.RenderEngine.class).getDeclaredFields()[6];
            field_animList.setAccessible(true);
            Field[] fieldArray = (net.minecraft.src.BiomeGenBase.class).getDeclaredFields();
            List biomes = new LinkedList();
            for (int i = 0; i < fieldArray.length; i++) {
                Class fieldType = fieldArray[i].getType();
                if ((fieldArray[i].getModifiers() & 8) != 0 && fieldType.isAssignableFrom(net.minecraft.src.BiomeGenBase.class)) {
                    BiomeGenBase biome = (BiomeGenBase) fieldArray[i].get(null);
                    if (!(biome instanceof BiomeGenHell) && !(biome instanceof BiomeGenSky)) {
                        biomes.add(biome);
                    }
                }
            }

            standardBiomes = (BiomeGenBase[]) biomes.toArray(new BiomeGenBase[0]);
            try {
                method_RegisterTileEntity = (net.minecraft.src.TileEntity.class).getDeclaredMethod("a", Class.class, String.class);
            } catch (NoSuchMethodException e) {
                method_RegisterTileEntity = (net.minecraft.src.TileEntity.class).getDeclaredMethod("addMapping", Class.class, String.class);
            }
            method_RegisterTileEntity.setAccessible(true);
            try {
                method_RegisterEntityID = (net.minecraft.src.EntityList.class).getDeclaredMethod("a", Class.class, String.class, Integer.TYPE);
            } catch (NoSuchMethodException e) {
                method_RegisterEntityID = (net.minecraft.src.EntityList.class).getDeclaredMethod("addMapping", Class.class, String.class, Integer.TYPE);
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
                cfgLoggingLevel = Level.parse(props.getProperty("loggingLevel"));
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

            // Mods remapping
            List<String> modsClasses = new ArrayList<>();
            HashMap<File, File> modsFiles = new HashMap<>();

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

            List<Class<?>> fixPackage = Arrays.asList(BaseMod.class, Config.class, EntityRendererProxy.class, MLProp.class, ModLoader.class, ModTextureAnimation.class, ModTextureStatic.class, StatListWorkAround.class);
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

                Field classesField = TinyRemapper.class.getDeclaredField("classes");
                classesField.setAccessible(true);

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
                                if(!fixCasing) {
                                    try {
                                        ClassInstance classInstance = ((Map<String, ClassInstance>) classesField.get(remapper)).get(owner);
                                        if (classInstance != null) {
                                            fixCasing = fixPackage.stream().anyMatch(c -> c.getSimpleName().equals(classInstance.getSuperName()));
                                        }
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (fixCasing) {
                                    return super.mapMethodName(owner, StringUtils.uncapitalize(name), descriptor);
                                }
                                return super.mapMethodName(owner, name, descriptor);
                            }
                        })
                        .extraAnalyzeVisitor(new ClassVisitor(Opcodes.ASM7, null) {
                            @Override
                            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                                return new MethodVisitor(Opcodes.ASM7, null) {
                                    @Override
                                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                                    }
                                };
                            }
                        })
                        .build();
                OutputConsumerPath outputConsumer = new OutputConsumerPath.Builder(remappedFile.toPath()).build();
                outputConsumer.addNonClassFiles(file.toPath());
                remapper.readClassPath(modsFiles.keySet().stream().filter(x -> x != file).map(File::toPath).toArray(Path[]::new));
                remapper.readClassPath(Paths.get("libraries", "b1.7.3.jar"), Paths.get("libraries", "ModLoader.jar"));
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
                loadMod(new File(url.toURI()), null);
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

            instance.gameSettings.keyBindings = registerAllKeys(instance.gameSettings.keyBindings);
            instance.gameSettings.loadOptions();
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

        for (int id = 0; id < Block.blocksList.length; id++) {
            if (!oneShotStats.containsKey(0x1000000 + id) && Block.blocksList[id] != null && Block.blocksList[id].getEnableStats()) {
                String str = StringTranslate.getInstance().translateKeyFormat("stat.mineBlock", Block.blocksList[id].translateBlockName());
                StatList.mineBlockStatArray[id] = (new StatCrafting(0x1000000 + id, str, id)).registerStat();
                StatList.objectMineStats.add(StatList.mineBlockStatArray[id]);
            }
        }

        for (int id = 0; id < Item.itemsList.length; id++) {
            if (!oneShotStats.containsKey(0x1020000 + id) && Item.itemsList[id] != null) {
                String str = StringTranslate.getInstance().translateKeyFormat("stat.useItem", Item.itemsList[id].getStatName());
                StatList.objectUseStats[id] = (new StatCrafting(0x1020000 + id, str, id)).registerStat();
                if (id >= Block.blocksList.length) {
                    StatList.itemStats.add(StatList.objectUseStats[id]);
                }
            }
            if (!oneShotStats.containsKey(0x1030000 + id) && Item.itemsList[id] != null && Item.itemsList[id].isDamagable()) {
                String str = StringTranslate.getInstance().translateKeyFormat("stat.breakItem", Item.itemsList[id].getStatName());
                StatList.objectBreakStats[id] = (new StatCrafting(0x1030000 + id, str, id)).registerStat();
            }
        }

        HashSet idHashSet = new HashSet();
        Object result;
        for (Iterator iterator = CraftingManager.getInstance().getRecipeList().iterator(); iterator.hasNext(); idHashSet.add(((IRecipe) result).getRecipeOutput().itemID)) {
            result = iterator.next();
        }

        for (Iterator iterator1 = FurnaceRecipes.smelting().getSmeltingList().values().iterator(); iterator1.hasNext(); idHashSet.add(((ItemStack) result).itemID)) {
            result = iterator1.next();
        }

        for (Iterator iterator2 = idHashSet.iterator(); iterator2.hasNext(); ) {
            int id = (Integer) iterator2.next();
            if (!oneShotStats.containsKey(0x1010000 + id) && Item.itemsList[id] != null) {
                String str = StringTranslate.getInstance().translateKeyFormat("stat.craftItem", Item.itemsList[id].getStatName());
                StatList.objectCraftStats[id] = (new StatCrafting(0x1010000 + id, str, id)).registerStat();
            }
        }

    }

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

    public static java.awt.image.BufferedImage loadImage(RenderEngine texCache, String path)
            throws Exception {
        TexturePackList pack = (TexturePackList) getPrivateValue(net.minecraft.src.RenderEngine.class, texCache, 11);
        InputStream input = pack.selectedTexturePack.getResourceAsStream(path);
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

    public static void onItemPickup(EntityPlayer player, ItemStack item) {
        BaseMod mod;
        for (Iterator iterator = modList.iterator(); iterator.hasNext(); mod.onItemPickup(player, item)) {
            mod = (BaseMod) iterator.next();
        }

    }

    public static void onTick(Minecraft game) {
        if (!hasInit) {
            init();
            logger.fine("Initialized");
        }
        if (texPack == null || game.gameSettings.skin != texPack) {
            texturesAdded = false;
            texPack = game.gameSettings.skin;
        }
        if (!texturesAdded && game.renderEngine != null) {
            registerAllTextureOverrides(game.renderEngine);
            texturesAdded = true;
        }
        long newclock = 0L;
        if (game.theWorld != null) {
            newclock = game.theWorld.getWorldTime();
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
                if ((clock != newclock || !((Boolean) modSet.getValue() & (game.theWorld != null))) && !((BaseMod) modSet.getKey()).onTickInGUI(game, game.currentScreen)) {
                    iter.remove();
                }
            }

        }
        if (clock != newclock) {
            for (Object o : keyList.entrySet()) {
                Map.Entry modSet = (Map.Entry) o;
                for (Object value : ((Map) modSet.getValue()).entrySet()) {
                    Map.Entry keySet = (Map.Entry) value;
                    boolean state = Keyboard.isKeyDown(((KeyBinding) keySet.getKey()).keyCode);
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

    public static void OpenGUI(EntityPlayer player, GuiScreen gui) {
        if (!hasInit) {
            init();
            logger.fine("Initialized");
        }
        Minecraft game = getMinecraftInstance();
        if (game.thePlayer != player) {
            return;
        }
        if (gui != null) {
            game.displayGuiScreen(gui);
        }
    }

    public static void populateChunk(IChunkProvider generator, int chunkX, int chunkZ, World world) {
        if (!hasInit) {
            init();
            logger.fine("Initialized");
        }
        Random rnd = new Random(world.getRandomSeed());
        long xSeed = (rnd.nextLong() / 2L) * 2L + 1L;
        long zSeed = (rnd.nextLong() / 2L) * 2L + 1L;
        rnd.setSeed((long) chunkX * xSeed + (long) chunkZ * zSeed ^ world.getRandomSeed());
        for (Iterator iterator = modList.iterator(); iterator.hasNext(); ) {
            BaseMod mod = (BaseMod) iterator.next();
            if (generator.makeString().equals("RandomLevelSource")) {
                mod.generateSurface(world, rnd, chunkX << 4, chunkZ << 4);
            } else if (generator.makeString().equals("HellRandomLevelSource")) {
                mod.generateNether(world, rnd, chunkX << 4, chunkZ << 4);
            }
        }

    }

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

    public static KeyBinding[] registerAllKeys(KeyBinding w[]) {
        List combinedList = new LinkedList();
        combinedList.addAll(Arrays.asList(w));
        Map keyMap;
        for (Iterator iterator = keyList.values().iterator(); iterator.hasNext(); combinedList.addAll(keyMap.keySet())) {
            keyMap = (Map) iterator.next();
        }

        return (KeyBinding[]) combinedList.toArray(new KeyBinding[0]);
    }

    public static void registerAllTextureOverrides(RenderEngine texCache) {
        animList.clear();
        Minecraft game = getMinecraftInstance();
        BaseMod mod;
        for (Iterator iterator = modList.iterator(); iterator.hasNext(); mod.registerAnimation(game)) {
            mod = (BaseMod) iterator.next();
        }

        TextureFX anim;
        for (Iterator iterator1 = animList.iterator(); iterator1.hasNext(); texCache.registerTextureFX(anim)) {
            anim = (TextureFX) iterator1.next();
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
                    texCache.registerTextureFX(anim);
                } catch (Exception e) {
                    logger.throwing("ModLoader", "RegisterAllTextureOverrides", e);
                    throwException(e);
                    throw new RuntimeException(e);
                }
            }

        }

    }

    public static void registerBlock(Block block) {
        registerBlock(block, null);
    }

    public static void registerBlock(Block block, Class itemclass) {
        try {
            if (block == null) {
                throw new IllegalArgumentException("block parameter cannot be null.");
            }
            List list = (List) field_blockList.get(null);
            list.add(block);
            int id = block.blockID;
            ItemBlock item = null;
            if (itemclass != null) {
                item = (ItemBlock) itemclass.getConstructor(new Class[]{
                        Integer.TYPE
                }).newInstance(new Object[]{
                        id - 256
                });
            } else {
                item = new ItemBlock(id - 256);
            }
            if (Block.blocksList[id] != null && Item.itemsList[id] == null) {
                Item.itemsList[id] = item;
            }
        } catch (IllegalArgumentException | NoSuchMethodException | InvocationTargetException | InstantiationException | SecurityException | IllegalAccessException e) {
            logger.throwing("ModLoader", "RegisterBlock", e);
            throwException(e);
        }
    }

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

    public static void registerTileEntity(Class tileEntityClass, String id) {
        registerTileEntity(tileEntityClass, id, null);
    }

    public static void registerTileEntity(Class tileEntityClass, String id, TileEntitySpecialRenderer renderer) {
        try {
            method_RegisterTileEntity.invoke(null, tileEntityClass, id);
            if (renderer != null) {
                TileEntityRenderer ref = TileEntityRenderer.instance;
                Map renderers = (Map) field_TileEntityRenderers.get(ref);
                renderers.put(tileEntityClass, renderer);
                renderer.setTileEntityRenderer(ref);
            }
        } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
            logger.throwing("ModLoader", "RegisterTileEntity", e);
            throwException(e);
        }
    }

    public static void removeSpawn(Class entityClass, EnumCreatureType spawnList) {
        removeSpawn(entityClass, spawnList, null);
    }

    public static void removeSpawn(Class entityClass, EnumCreatureType spawnList, BiomeGenBase biomes[]) {
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
            List list = biomes[i].getSpawnableList(spawnList);
            if (list != null) {
                for (Iterator iter = list.iterator(); iter.hasNext(); ) {
                    SpawnListEntry entry = (SpawnListEntry) iter.next();
                    if (entry.entityClass == entityClass) {
                        iter.remove();
                    }
                }

            }
        }

    }

    public static void removeSpawn(String entityName, EnumCreatureType spawnList) {
        removeSpawn(entityName, spawnList, null);
    }

    public static void removeSpawn(String entityName, EnumCreatureType spawnList, BiomeGenBase biomes[]) {
        Class entityClass = (Class) classMap.get(entityName);
        if (entityClass != null && (net.minecraft.src.EntityLiving.class).isAssignableFrom(entityClass)) {
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

    public static void renderInvBlock(RenderBlocks renderer, Block block, int metadata, int modelID) {
        BaseMod mod = (BaseMod) blockModels.get(modelID);
        if (mod == null) {
            return;
        } else {
            mod.renderInvBlock(renderer, block, metadata, modelID);
            return;
        }
    }

    public static boolean renderWorldBlock(RenderBlocks renderer, IBlockAccess world, int x, int y, int z, Block block, int modelID) {
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

    public static void takenFromCrafting(EntityPlayer player, ItemStack item) {
        BaseMod mod;
        for (Iterator iterator = modList.iterator(); iterator.hasNext(); mod.takenFromCrafting(player, item)) {
            mod = (BaseMod) iterator.next();
        }

    }

    public static void takenFromFurnace(EntityPlayer player, ItemStack item) {
        BaseMod mod;
        for (Iterator iterator = modList.iterator(); iterator.hasNext(); mod.takenFromFurnace(player, item)) {
            mod = (BaseMod) iterator.next();
        }

    }

    public static void throwException(String message, Throwable e) {
        Minecraft game = getMinecraftInstance();
        if (game != null) {
            e.printStackTrace();
            game.displayUnexpectedThrowable(new UnexpectedThrowable(message, e));
        } else {
            throw new RuntimeException(e);
        }
    }

    private static void throwException(Throwable e) {
        throwException("Exception occured in ModLoader", e);
    }

    private ModLoader() {
    }

    private static final List animList = new LinkedList();
    private static final Map blockModels = new HashMap();
    private static final Map blockSpecialInv = new HashMap();
    private static final File cfgdir;
    private static final File cfgfile;
    public static Level cfgLoggingLevel;
    private static Map classMap = null;
    private static long clock = 0L;
    public static final boolean DEBUG = false;
    private static Field field_animList = null;
    private static Field field_armorList = null;
    private static Field field_blockList = null;
    private static Field field_modifiers = null;
    private static Field field_TileEntityRenderers = null;
    public static boolean hasInit = false;
    private static int highestEntityId = 3000;
    private static final Map inGameHooks = new HashMap();
    private static final Map inGUIHooks = new HashMap();
    private static Minecraft instance = null;
    private static int itemSpriteIndex = 0;
    private static int itemSpritesLeft = 0;
    private static final Map keyList = new HashMap();
    private static final File logfile = new File(Minecraft.getMinecraftDir(), "ModLoader.txt");
    private static final Logger logger = Logger.getLogger("ModLoader");
    private static FileHandler logHandler = null;
    private static Method method_RegisterEntityID = null;
    private static Method method_RegisterTileEntity = null;
    private static final File modDir = new File(Minecraft.getMinecraftDir(), "mods");
    private static final File remappedModDir = new File(modDir, "remapped");
    private static final LinkedList modList = new LinkedList();
    private static int nextBlockModelID = 1000;
    private static final Map overrides = new HashMap();
    public static final Properties props = new Properties();
    private static BiomeGenBase[] standardBiomes;
    private static int terrainSpriteIndex = 0;
    private static int terrainSpritesLeft = 0;
    private static String texPack = null;
    private static boolean texturesAdded = false;
    private static final boolean[] usedItemSprites = new boolean[256];
    private static final boolean[] usedTerrainSprites = new boolean[256];
    public static final String VERSION = "ModLoader Beta 1.7.3";

    @Getter
    private static float[][] redstoneColors;

    static {
        cfgdir = new File(Minecraft.getMinecraftDir(), "/config/");
        cfgfile = new File(cfgdir, "ModLoader.cfg");
        cfgLoggingLevel = Level.FINER;

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
}
