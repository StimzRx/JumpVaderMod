package dev.venomcode.jumpvader;

import dev.venomcode.jumpvader.blocks.JumpVaderBlock;
import eu.pb4.polymer.core.api.item.PolymerBlockItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JumpVaderMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MODID = "jumpvader";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	private static JumpVaderConfig config;

	@Override
	public void onInitialize()
	{
		config = new JumpVaderConfig();

		Registry.register(Registries.BLOCK, new Identifier(JumpVaderMod.MODID, "jumpvader_block"), JUMP_VADER_BLOCK);

		Registry.register( Registries.ITEM, new Identifier(JumpVaderMod.MODID, "jumpvader_block"), new PolymerBlockItem( JUMP_VADER_BLOCK, new FabricItemSettings(), Items.ORANGE_STAINED_GLASS ) );
	}
	public static JumpVaderConfig getConfig()
	{
		return config;
	}
	public static final JumpVaderBlock JUMP_VADER_BLOCK = new JumpVaderBlock(FabricBlockSettings.copyOf(Blocks.BAMBOO_PLANKS), Blocks.ORANGE_STAINED_GLASS);
}
