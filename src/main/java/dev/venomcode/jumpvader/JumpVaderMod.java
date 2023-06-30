package dev.venomcode.jumpvader;

import dev.venomcode.jumpvader.blocks.JumpVaderBlock;
import dev.venomcode.serverapi.api.ServerAPI;
import dev.venomcode.serverapi.api.event.SAPIPlayerEvents;
import eu.pb4.polymer.core.api.item.PolymerBlockItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.nio.file.Path;

public class JumpVaderMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MODID = "jumpvader";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize()
	{
		JumpVaderConfig config = getConfig();
		saveConfig();

		Identifier jumpVaderProxyIdent = new Identifier(config.getAlternativeBlock());
		Item jumpVaderProxyItem = Registries.ITEM.get(jumpVaderProxyIdent);

		JUMP_VADER_BLOCK = new JumpVaderBlock(FabricBlockSettings.copyOf(Blocks.BAMBOO_PLANKS), Registries.BLOCK.get(jumpVaderProxyIdent));

		Registry.register(Registries.BLOCK, new Identifier(JumpVaderMod.MODID, "jumpvader_block"), JUMP_VADER_BLOCK);

		Registry.register( Registries.ITEM, new Identifier(JumpVaderMod.MODID, "jumpvader_block"), new PolymerBlockItem( JUMP_VADER_BLOCK, new FabricItemSettings(), jumpVaderProxyItem ) );

		SAPIPlayerEvents.JUMP.register((player -> {
			if(!config.getEnabled())
				return true;
			BlockPos testPos = player.getBlockPos().down();
			if(player.getWorld().getBlockState(testPos).getBlock() instanceof JumpVaderBlock jumpVaderBlock)
			{
				return !jumpVaderBlock.onJump(testPos, player);
			}
			return true;
		}));

		SAPIPlayerEvents.SNEAK.register((player -> {
			if(!config.getEnabled())
				return true;

			BlockPos testPos = player.getBlockPos().down();
			if(player.getWorld().getBlockState(testPos).getBlock() instanceof JumpVaderBlock jumpVaderBlock) {
				jumpVaderBlock.onCrouch(testPos, player);
			}

			return true;
		}));
	}
	public static JumpVaderBlock JUMP_VADER_BLOCK;

	public static JumpVaderConfig getConfig() {
		if(_configCached != null)
			return _configCached;

		try {
			CommentedConfigurationNode node = configLoader.load();

			_configCached = node.get(JumpVaderConfig.class);
		}
		catch (ConfigurateException ex) {
			LOGGER.error(ServerAPI.Logger.Error("[ERROR]Failed to load jump_vader config."));
		}

		return _configCached;
	}

	public static void saveConfig() {
		CommentedConfigurationNode node = CommentedConfigurationNode.root();
		try {
			node.set(JumpVaderConfig.class, _configCached);
			configLoader.save(node);
		}
		catch (ConfigurateException ex) {
			LOGGER.error(ServerAPI.Logger.Error("[ERROR]Failed to save jump_vader config."));
		}
	}

	private static final HoconConfigurationLoader configLoader = HoconConfigurationLoader.builder()
			.path(Path.of(ServerAPI.CONFIG_PATH + "jump_vader.conf"))
			.build();
	private static JumpVaderConfig _configCached = null;
}
