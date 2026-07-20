package com.bigbilly.lightningblade.item;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.minecraft.core.registries.Registries;
import com.bigbilly.lightningblade.LightningBladeMod;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(LightningBladeMod.MODID);

    public static final DeferredItem<Item> LIGHTNING_BLADE = ITEMS.register("lightning_blade",
            () -> new LightningBladeItem(new Item.Properties()
                    .durability(2048)));

    public static void register() {
        // Registration happens via the event bus
    }
}
