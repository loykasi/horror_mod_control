package net.lukenni.horrormodcontrol.procedures;

import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.lukenni.horrormodcontrol.network.HorrorModControlModVariables;

import javax.annotation.Nullable;
import java.util.Iterator;

@EventBusSubscriber
public class GameOverDetectorProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		execute(null, world, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (entity instanceof Player && sourceentity.getType().toString().equals("entity.no_moon.game_over_normal")) {
			HorrorModControlModVariables.MapVariables.get(world).killByGameOver = true;
			HorrorModControlModVariables.MapVariables.get(world).syncData(world);

			if (world instanceof ServerLevel serverLevel) {
				Iterator<ServerPlayer> iter = serverLevel.players().iterator();
				while (iter.hasNext()) {
					ServerPlayer sp = iter.next();
					sp.connection.disconnect(Component.literal("Game Over?"));
				}
			}

			sourceentity.discard();
		}
	}

}