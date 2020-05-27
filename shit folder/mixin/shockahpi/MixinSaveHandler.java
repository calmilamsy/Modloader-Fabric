package net.glasslauncher.mixin.shockahpi;

import net.glasslauncher.shockahpi.DimensionBase;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Mixin(SaveHandler.class)
public class MixinSaveHandler implements ISaveHandler {
   private static final Logger logger = Logger.getLogger("Minecraft");
   private final File saveDirectory;
   private final File playersDirectory;
   private final File field_28114_d;
   private final long now = System.currentTimeMillis();

   public MixinSaveHandler(File paramFile, String paramString, boolean paramBoolean) {
      this.saveDirectory = new File(paramFile, paramString);
      this.saveDirectory.mkdirs();
      this.playersDirectory = new File(this.saveDirectory, "players");
      this.field_28114_d = new File(this.saveDirectory, "data");
      this.field_28114_d.mkdirs();
      if(paramBoolean) {
         this.playersDirectory.mkdirs();
      }
   }

   @Override
   public WorldInfo loadWorldInfo() {
      return null;
   }

   /**
    * @author calmilamsy
    * @reason Too lazy, just want to get working example.
    * //TODO: No overwrite.
    */
   @Overwrite
   public void func_22150_b() {
      try {
         File localIOException = new File(this.saveDirectory, "session.lock");
         DataInputStream localDataInputStream = new DataInputStream(new FileInputStream(localIOException));

         try {
            if(localDataInputStream.readLong() != this.now) {
               throw new MinecraftException("The save is being accessed from another location, aborting");
            }
         } finally {
            localDataInputStream.close();
         }

      } catch (IOException var7) {
         throw new MinecraftException("Failed to check session lock, aborting");
      }
   }

   /**
    * @author calmilamsy
    * @reason Too lazy, just want to get working example.
    * //TODO: No overwrite.
    */
   @Overwrite
   public IChunkLoader getChunkLoader(WorldProvider paramxa) {
      DimensionBase localDimensionBase = DimensionBase.getDimByProvider(paramxa.getClass());
      if(localDimensionBase.number != 0) {
         File localFile = new File(this.saveDirectory, "DIM" + localDimensionBase.number);
         localFile.mkdirs();
         return new ChunkLoader(localFile, true);
      } else {
         return new ChunkLoader(this.saveDirectory, true);
      }
   }

   @Override
   public void saveWorldInfoAndPlayer(WorldInfo worldInfo, List list) {

   }

   @Override
   public void saveWorldInfo(WorldInfo worldInfo) {

   }

   @Override
   public File func_28113_a(String s) {
      return null;
   }
}
