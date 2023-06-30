package dev.venomcode.jumpvader;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.IOException;
import java.nio.file.Path;

@ConfigSerializable
public class JumpVaderConfig
{

    public boolean getEnabled()
    {
        return enabled;
    }

    public int getMaxVerticalBlocks()
    {
        return maxVerticleBlocks;
    }
    public String getAlternativeBlock()
    {
        return placeholderBlockRaw;
    }

    @Setting("mod enabled")
    @Comment("Toggles the entire mod on/off. Doesnt delete blocks/items if set to off!")
    private boolean enabled = true;

    @Setting("max vertical blocks")
    @Comment("The maximum amount of blocks a player can move vertically using the jump vader")
    private int maxVerticleBlocks = 128;

    @Setting("display block")
    @Comment("Sets the 'fake' block to display as a placeholder for the jump vader block.")
    private String placeholderBlockRaw = "minecraft:orange_wool";
}
