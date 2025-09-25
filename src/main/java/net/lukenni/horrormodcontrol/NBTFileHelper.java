package net.lukenni.horrormodcontrol;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.LevelResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class NBTFileHelper {

    public static CompoundTag loadNBT(ServerLevel level, String fileName) {
        try {
            MinecraftServer server = level.getServer();
            Path path = server.getWorldPath(LevelResource.ROOT).resolve("data").resolve(fileName);

            if (path.toFile().exists()) {
                return NbtIo.readCompressed(path, NbtAccounter.unlimitedHeap());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CompoundTag();
    }

    public static void saveNBT(ServerLevel level, String fileName, CompoundTag tag) {
        try {
            MinecraftServer server = level.getServer();
            Path path = server.getWorldPath(LevelResource.ROOT).resolve("data").resolve(fileName);

            NbtIo.writeCompressed(tag, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}