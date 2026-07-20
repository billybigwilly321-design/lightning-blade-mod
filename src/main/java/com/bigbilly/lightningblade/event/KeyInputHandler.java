package com.bigbilly.lightningblade.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import com.bigbilly.lightningblade.LightningBladeMod;
import com.bigbilly.lightningblade.item.ModItems;
import com.KEYBOARD_MOD;

@EventBusSubscriber(modid = LightningBladeMod.MODID, bus = EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class KeyInputHandler {
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null) return;

        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();

        // Check if player is holding Lightning Blade
        boolean hasLightningBlade = mainHand.getItem() instanceof com.bigbilly.lightningblade.item.LightningBladeItem ||
                offHand.getItem() instanceof com.bigbilly.lightningblade.item.LightningBladeItem;

        if (!hasLightningBlade) return;

        // R key code is 82 (GLFW key code)
        if (event.getKey() == 82 && event.getAction() == 1) { // 1 = PRESS
            teleportWithLightning(player);
            event.setResult(InputEvent.ClickInputEvent.Result.ALLOW);
        }
    }

    private static void teleportWithLightning(Player player) {
        if (player.level().isClientSide) return;

        double lookX = player.getLookAngle().x;
        double lookY = player.getLookAngle().y;
        double lookZ = player.getLookAngle().z;

        double distance = 30.0;
        double teleportX = player.getX() + lookX * distance;
        double teleportY = player.getY() + lookY * distance;
        double teleportZ = player.getZ() + lookZ * distance;

        // Teleport player
        player.teleportTo(teleportX, teleportY, teleportZ);

        // Spawn lightning at destination
        net.minecraft.world.entity.weather.LightningBolt lightning = new net.minecraft.world.entity.weather.LightningBolt(
                net.minecraft.world.entity.EntityType.LIGHTNING_BOLT,
                player.level()
        );
        lightning.setPos(teleportX, teleportY, teleportZ);
        player.level().addFreshEntity(lightning);

        // Play thunder sound
        player.level().playSeededSound(null, player.getX(), player.getY(), player.getZ(),
                net.minecraft.sounds.SoundEvents.LIGHTNING_BOLT_THUNDER,
                net.minecraft.sounds.SoundSource.MASTER, 1.0f, 1.0f, 0);
    }
}
