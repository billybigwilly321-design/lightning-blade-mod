package com.bigbilly.lightningblade;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bigbilly.lightningblade.item.ModItems;

@Mod(LightningBladeMod.MODID)
public class LightningBladeMod {
    public static final String MODID = "bigbilly";
    private static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public LightningBladeMod() {
        LOGGER.info("Lightning Blade Mod initialized!");
        ModItems.register();
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("Client setup complete!");
        }
    }
}
