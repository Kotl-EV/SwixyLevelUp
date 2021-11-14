package ru.swixy.levelup;

import net.minecraftforge.common.config.Configuration;

public class Config {
    public static int EXTRA_HP_MAX_LEVEL = 200;
    public static int[] EXTRA_HP_LEVELS = new int[0];
    public Config(Configuration config) {
        config.load();
        EXTRA_HP_MAX_LEVEL = config.get("ExtraHP","MaxLevel",200).getInt();
        if(EXTRA_HP_MAX_LEVEL<0) EXTRA_HP_MAX_LEVEL = 0;
        EXTRA_HP_LEVELS = new int[EXTRA_HP_MAX_LEVEL+1];
        for(int i=0; i<=EXTRA_HP_MAX_LEVEL; i++)
            EXTRA_HP_LEVELS[i] = config.get("ExtraHP","Level_" + String.format("%04d", i),
                    (int)(Math.log(i/10.0 + 1) * 20 / Math.log(EXTRA_HP_MAX_LEVEL/10.0 +1))
            ).getInt();
        if (config.hasChanged())
            config.save();
    }
}
