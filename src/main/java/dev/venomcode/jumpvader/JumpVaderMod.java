package dev.venomcode.jumpvader;

import dev.venomcode.jumpvader.blocks.JumpVaderBlock;
import eu.pb4.polymer.api.item.PolymerBlockItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
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

		Registry.register( Registry.BLOCK, new Identifier(JumpVaderMod.MODID, "jumpvader_block"), JUMP_VADER_BLOCK);

		Registry.register( Registry.ITEM, new Identifier(JumpVaderMod.MODID, "jumpvader_block"), new PolymerBlockItem( JUMP_VADER_BLOCK, new FabricItemSettings(), Items.ORANGE_STAINED_GLASS ) );
	}
	public static JumpVaderConfig getConfig()
	{
		return config;
	}
	public static final JumpVaderBlock JUMP_VADER_BLOCK = new JumpVaderBlock(FabricBlockSettings.of(Material.GLASS), Blocks.ORANGE_STAINED_GLASS);
}
