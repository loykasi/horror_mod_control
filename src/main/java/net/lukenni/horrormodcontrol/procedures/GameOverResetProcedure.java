package net.lukenni.horrormodcontrol.procedures;

import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;

import net.lukenni.horrormodcontrol.network.HorrorModControlModVariables;
import net.lukenni.horrormodcontrol.NBTFileHelper;

import javax.annotation.Nullable;

@EventBusSubscriber
public class GameOverResetProcedure {
	@SubscribeEvent
	public static void onWorldUnload(net.neoforged.neoforge.event.level.LevelEvent.Unload event) {
		execute(event, event.getLevel());
	}

	public static void execute(LevelAccessor world) {
		execute(null, world);
	}

	private static void execute(@Nullable Event event, LevelAccessor world) {
		if (HorrorModControlModVariables.MapVariables.get(world).killByGameOver) {
			HorrorModControlModVariables.MapVariables.get(world).killByGameOver = false;
			HorrorModControlModVariables.MapVariables.get(world).syncData(world);

			if (world instanceof ServerLevel _level)
	 		{
				CompoundTag data = NBTFileHelper.loadNBT(_level, "no_moon_worldvars.dat");
	
			    CompoundTag dataTag = data.getCompound("data");
			    dataTag.putByte("Game_Over_Warning", (byte) 0);
			    dataTag.putByte("Game_Over_Spawn", (byte) 0);
			    dataTag.putByte("Game_Over_One", (byte) 0);
			    dataTag.putByte("Game_Over_Hunt", (byte) 0);
			    data.put("data", dataTag);
			    
			    NBTFileHelper.saveNBT(_level, "no_moon_worldvars.dat", data);
			}
		}
	}
}

