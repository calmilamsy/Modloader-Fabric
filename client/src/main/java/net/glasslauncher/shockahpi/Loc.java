// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   Loc.java

package net.glasslauncher.shockahpi;

import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

import java.util.ArrayList;

// Referenced classes of package net.minecraft.src:
//            World, ChunkCoordinates, IBlockAccess, TileEntity

public class Loc
{

    public Loc()
    {
        this(0d, 0d, 0d);
    }

    public Loc(int x, int z)
    {
        this(x, 0, z);
    }

    public Loc(int x, int y, int z)
    {
        this((double) x, (double) y, (double) z);
    }

    public Loc(double x, double z)
    {
        this(x, 0.0D, z);
    }

    public Loc(World world)
    {
        this(world.getSpawnPoint().x, world.getSpawnPoint().y, world.getSpawnPoint().z);
    }

    public Loc(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int x()
    {
        return (int)x;
    }

    public int y()
    {
        return (int)y;
    }

    public int z()
    {
        return (int)z;
    }

    public Loc add(int x, int y, int z)
    {
        return new Loc(this.x + (double)x, this.y + (double)y, this.z + (double)z);
    }

    public Loc add(double x, double y, double z)
    {
        return new Loc(this.x + x, this.y + y, this.z + z);
    }

    public Loc add(Loc other)
    {
        return new Loc(x + other.x, y + other.y, z + other.z);
    }

    public Loc subtract(int x, int y, int z)
    {
        return new Loc(this.x - (double)x, this.y - (double)y, this.z - (double)z);
    }

    public Loc subtract(double x, double y, double z)
    {
        return new Loc(this.x - x, this.y - y, this.z - z);
    }

    public Loc subtract(Loc other)
    {
        return new Loc(x - other.x, y - other.y, z - other.z);
    }

    public Loc multiply(double xMult, double yMult, double zMult)
    {
        return new Loc(x * xMult, y * yMult, z * zMult);
    }

    public Loc getSide(int side)
    {
        if(side == 0)
        {
            return new Loc(x, y - 1.0D, z);
        }
        if(side == 1)
        {
            return new Loc(x, y + 1.0D, z);
        }
        if(side == 2)
        {
            return new Loc(x, y, z - 1.0D);
        }
        if(side == 3)
        {
            return new Loc(x, y, z + 1.0D);
        }
        if(side == 4)
        {
            return new Loc(x - 1.0D, y, z);
        }
        if(side == 5)
        {
            return new Loc(x + 1.0D, y, z);
        } else
        {
            return this;
        }
    }

    public boolean equals(Object other)
    {
        if(other instanceof Loc)
        {
            Loc otherLoc = (Loc)other;
            return x == otherLoc.x && y == otherLoc.y && z == otherLoc.z;
        } else
        {
            return false;
        }
    }

    public String toString()
    {
        return (new StringBuilder("(")).append(x).append(",").append(y).append(",").append(z).append(")").toString();
    }

    public int distSimple(Loc other)
    {
        return (int)(Math.abs(x - other.x) + Math.abs(y - other.y) + Math.abs(z - other.z));
    }

    public double distAdv(Loc other)
    {
        return Math.sqrt(Math.pow(x - other.x, 2D) + Math.pow(y - other.y, 2D) + Math.pow(z - other.z, 2D));
    }

    public static Loc[] vecAdjacent()
    {
        Loc array[] = new Loc[6];
        array[0] = new Loc(0, 0, 1);
        array[1] = new Loc(0, 0, -1);
        array[2] = new Loc(0, 1, 0);
        array[3] = new Loc(0, -1, 0);
        array[4] = new Loc(1, 0, 0);
        array[5] = new Loc(-1, 0, 0);
        return array;
    }

    public Loc[] adjacent()
    {
        Loc array[] = vecAdjacent();
        for(int i = 0; i < array.length; i++)
        {
            array[i] = add(array[i]);
        }

        return array;
    }

    public static Loc[] vecAdjacent2D()
    {
        Loc array[] = new Loc[4];
        array[0] = new Loc(0, 1);
        array[1] = new Loc(0, -1);
        array[2] = new Loc(1, 0);
        array[3] = new Loc(-1, 0);
        return array;
    }

    public Loc[] adjacent2D()
    {
        Loc array[] = vecAdjacent();
        for(int i = 0; i < array.length; i++)
        {
            array[i] = add(array[i]);
        }

        return array;
    }

    public static ArrayList vecInRadius(int maxR, boolean advanced)
    {
        ArrayList toReturn = new ArrayList();
        Loc start = new Loc();
        for(int x = -maxR; x <= maxR; x++)
        {
            for(int z = -maxR; z <= maxR; z++)
            {
                for(int y = -maxR; y <= maxR; y++)
                {
                    Loc check = new Loc(x, y, z);
                    double dist = advanced ? start.distAdv(check) : start.distSimple(check);
                    if(dist <= (double)maxR)
                    {
                        toReturn.add(check);
                    }
                }

            }

        }

        return toReturn;
    }

    public ArrayList inRadius(int maxR, boolean advanced)
    {
        ArrayList toReturn = new ArrayList();
        for(int x = -maxR; x <= maxR; x++)
        {
            for(int z = -maxR; z <= maxR; z++)
            {
                for(int y = -maxR; y <= maxR; y++)
                {
                    Loc check = (new Loc(x, y, z)).add(this);
                    double dist = advanced ? distAdv(check) : distSimple(check);
                    if(dist <= (double)maxR)
                    {
                        toReturn.add(check);
                    }
                }

            }

        }

        return toReturn;
    }

    public static ArrayList vecInRadius2D(int maxR, boolean advanced)
    {
        ArrayList toReturn = new ArrayList();
        Loc start = new Loc();
        for(int x = -maxR; x <= maxR; x++)
        {
            for(int z = -maxR; z <= maxR; z++)
            {
                Loc check = new Loc(x, z);
                double dist = advanced ? start.distAdv(check) : start.distSimple(check);
                if(dist <= (double)maxR)
                {
                    toReturn.add(check);
                }
            }

        }

        return toReturn;
    }

    public ArrayList inRadius2D(int maxR, boolean advanced)
    {
        ArrayList toReturn = new ArrayList();
        for(int x = -maxR; x <= maxR; x++)
        {
            for(int z = -maxR; z <= maxR; z++)
            {
                Loc check = (new Loc(x, z)).add(this);
                double dist = advanced ? distAdv(check) : distSimple(check);
                if(dist <= (double)maxR)
                {
                    toReturn.add(check);
                }
            }

        }

        return toReturn;
    }

    public int getBlock(IBlockAccess blockAc)
    {
        return blockAc.getBlockId(x(), y(), z());
    }

    public Loc setBlockNotify(World world, int blockID)
    {
        world.setBlockWithNotify(x(), y(), z(), blockID);
        return this;
    }

    public Loc setBlock(World world, int blockID)
    {
        world.setBlock(x(), y(), z(), blockID);
        return this;
    }

    public int getMeta(IBlockAccess blockAc)
    {
        return blockAc.getBlockMetadata(x(), y(), z());
    }

    public Loc setMeta(World world, int meta)
    {
        world.setBlockMetadata(x(), y(), z(), meta);
        return this;
    }

    public Loc setMetaNotify(World world, int meta)
    {
        world.setBlockMetadataWithNotify(x(), y(), z(), meta);
        return this;
    }

    public Loc setBlockAndMeta(World world, int blockID, int meta)
    {
        world.setBlockAndMetadata(x(), y(), z(), blockID, meta);
        return this;
    }

    public Loc setBlockAndMetaNotify(World world, int blockID, int meta)
    {
        world.setBlockAndMetadataWithNotify(x(), y(), z(), blockID, meta);
        return this;
    }

    public TileEntity getTileEntity(IBlockAccess blockAc)
    {
        return blockAc.getBlockTileEntity(x(), y(), z());
    }

    public Loc setTileEntity(World world, TileEntity tileEntity)
    {
        world.setBlockTileEntity(x(), y(), z(), tileEntity);
        return this;
    }

    public Loc removeTileEntity(World world)
    {
        world.removeBlockTileEntity(x(), y(), z());
        return this;
    }

    public int getLight(World world)
    {
        return world.getFullBlockLightValue(x(), y(), z());
    }

    public Loc notify(World world)
    {
        //TODO world.notifyBlockChange(x(), y(), z(), getBlock(world));
        return this;
    }

    public Loc setSpawnPoint(World world)
    {
        world.setSpawnPoint(new ChunkCoordinates(x(), y(), z()));
        return this;
    }

    public final double x;
    public final double y;
    public final double z;
}
