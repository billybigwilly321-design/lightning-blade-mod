package com.bigbilly.lightningblade.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;

public class LightningBladeItem extends SwordItem {
    public LightningBladeItem(Item.Properties properties) {
        super(Tiers.DIAMOND, 3, -2.4f, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide && player.isShiftKeyDown()) {
            teleportWithLightning(player);
            itemStack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
            return InteractionResultHolder.success(itemStack);
        }

        return InteractionResultHolder.pass(itemStack);
    }

    private void teleportWithLightning(Player player) {
        double lookX = player.getLookAngle().x;
        double lookY = player.getLookAngle().y;
        double lookZ = player.getLookAngle().z;

        double distance = 30.0;
        double teleportX = player.getX() + lookX * distance;
        double teleportY = player.getY() + lookY * distance;
        double teleportZ = player.getZ() + lookZ * distance;

        // Spawn lightning at current position
        player.level().setBlockAndUpdate(player.blockPosition(), player.level().getBlockState(player.blockPosition()));
        player.level().destroyBlockProgress(-1, player.blockPosition(), -1);

        // Teleport player
        player.teleportTo(teleportX, teleportY, teleportZ);

        // Spawn lightning at destination
        net.minecraft.world.level.block.Blocks.AIR.getStateForPlacement(null);
        
        // Create lightning entity
        net.minecraft.world.entity.weather.Lightning lightning = net.minecraft.world.entity.weather.LightningBolt.create(
                player.level(),
                teleportX,
                teleportY + 1,
                teleportZ,
                false,
                null
        );
        if (lightning != null) {
            player.level().addFreshEntity(lightning);
        }

        player.getCommandSenderWorld().playSeededSound(null, player.getX(), player.getY(), player.getZ(),
                net.minecraft.sounds.SoundEvents.LIGHTNING_BOLT_THUNDER,
                net.minecraft.sounds.SoundSource.MASTER, 1.0f, 1.0f, 0);
    }
}
