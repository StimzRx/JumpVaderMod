package dev.venomcode.jumpvader;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.IOException;
import java.nio.file.Path;

public class JumpVaderConfig
{

    public boolean getEnabled()
    {
        return rootNode.node(ENABLED_TAG).getBoolean(true);
    }

    public int getMaxVerticalBlocks()
    {
        return rootNode.node(MAX_VERTICAL_BLOCKS_TAG).getInt(128);
    }
    public String getAlternativeBlock()
    {
        return rootNode.node(USE_ALTERNATIVE_BLOCK_TAG).getString("default");
    }

    public void setupConfig() throws SerializationException
    {
        rootNode.node(ENABLED_TAG).comment(ENABLED_TAG_COMMENT).set(getEnabled());
        rootNode.node(MAX_VERTICAL_BLOCKS_TAG).comment(MAX_VERTICAL_BLOCKS_TAG_COMMENT).set(getMaxVerticalBlocks());
        rootNode.node(USE_ALTERNATIVE_BLOCK_TAG).comment(USE_ALTERNATIVE_BLOCK_TAG_COMMENT).set(getAlternativeBlock());

        save();
    }

    // NODE LOCATION TAGS & COMMENTS
    private static final String ENABLED_TAG = "enabled";
    private static final String ENABLED_TAG_COMMENT = "Toggles this entire mod on and off.";
    private static final String MAX_VERTICAL_BLOCKS_TAG = "max_blocks_vertical";
    private static final String MAX_VERTICAL_BLOCKS_TAG_COMMENT = "The maximum amount of vertical blocks to travel when using the jump vader block.";
    private static final String USE_ALTERNATIVE_BLOCK_TAG = "use_alternative_block";
    private static final String USE_ALTERNATIVE_BLOCK_TAG_COMMENT = "Block to use for clients. Allowed Values: default, stained_glass, white_wool";


    public JumpVaderConfig()
    {
        loader = HoconConfigurationLoader.builder()
                .path(Path.of("./config/" + JumpVaderMod.MODID + ".conf"))
                .build();
        try
        {
            rootNode = loader.load();
            setupConfig();
        }
        catch (IOException ex)
        {
            JumpVaderMod.LOGGER.info("Error occurred loading config:" + ex.getMessage());
            if(ex.getCause() != null)
            {
                ex.getCause().printStackTrace();
            }
            rootNode = null;
        }
    }

    private boolean save()
    {
        try
        {
            loader.save(rootNode);
            return true;
        }
        catch (final ConfigurateException ex)
        {
            JumpVaderMod.LOGGER.info("Unable to save config for '" + JumpVaderMod.MODID + "'! Error: " + ex.getMessage());
        }
        return false;
    }

    private CommentedConfigurationNode getRootNode()
    {
        return rootNode;
    }

    private final HoconConfigurationLoader loader;
    private CommentedConfigurationNode rootNode;
}
