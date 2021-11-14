package ru.swixy.levelup;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;

import java.util.UUID;

@Mod(modid = "LevelUp", name = "SwixyLevelUp-1.7.10", version = "1.0", acceptableRemoteVersions = "*")
public class LevelUpEvent {

    @Mod.EventHandler
    public void preLoad(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        new Config(config);
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent onPlayerPickupXpEvent) {
            MinecraftForge.EVENT_BUS.register(this);
            FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onPlayerPickupXpEvent(PlayerPickupXpEvent ev) {
        EntityPlayer player = ev.entityPlayer;
        if (player != null) {
            if (!player.getEntityWorld().isRemote) {
                    UpdateHealthAttribute(player,1);
            }
        }
    }

    public static void UpdateHealthAttribute(EntityPlayer player)
    {
        UpdateHealthAttribute(player,0);
    }

    public static void UpdateHealthAttribute(EntityPlayer player,int bonus) {
            if (!player.getEntityWorld().isRemote) {
                AttributeModifier mod = GetAttributeModifier(player.experienceLevel + bonus);
                player.getEntityAttribute(
                        SharedMonsterAttributes.maxHealth
                ).removeModifier(mod);
                player.getEntityAttribute(
                        SharedMonsterAttributes.maxHealth
                ).applyModifier(mod);
                player.heal(player.getMaxHealth());
            }
    }
    public static UUID mod_id = UUID.fromString("9a090263-953b-4d9f-947e-d4636cf3cd7e");

    public static AttributeModifier GetAttributeModifier(int level) {
        int hp_boost = 0;
        if(level > Config.EXTRA_HP_MAX_LEVEL )
            level = Config.EXTRA_HP_MAX_LEVEL ;
        if(level >= Config.EXTRA_HP_LEVELS.length)
            level = Config.EXTRA_HP_LEVELS.length-1;
        if(level >=0) {
            hp_boost = Config.EXTRA_HP_LEVELS[level];
        }
        else hp_boost = 0;
        return new AttributeModifier(mod_id, "levelup", hp_boost, 0);
    }
}
