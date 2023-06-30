package dev.venomcode.jumpvader.blocks;

import dev.venomcode.jumpvader.JumpVaderMod;
import dev.venomcode.jumpvader.ifaces.IJumpVaderListener;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.core.api.block.SimplePolymerBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class JumpVaderBlock extends SimplePolymerBlock implements IJumpVaderListener {

    public JumpVaderBlock(Settings settings, Block polymerBlock) {
        super(settings, polymerBlock);
    }

    @Override
    public boolean onJump(BlockPos pos , ServerPlayerEntity player )
    {
        if(!JumpVaderMod.getConfig().getEnabled())
            return false;
        pos = pos.up();
        ServerWorld w = (ServerWorld) player.getWorld();
        int count = 0;

        while(count < JumpVaderMod.getConfig().getMaxVerticalBlocks() && pos.getY() < 316)
        {
            Block blk = w.getBlockState( pos ).getBlock();

            if(blk instanceof JumpVaderBlock)
            {
                final BlockPos tpPos = pos.up();

                if(w.getBlockState( tpPos ).getBlock().equals( Blocks.AIR ) && w.getBlockState( tpPos.up() ).getBlock().equals( Blocks.AIR ))
                {
                    player.networkHandler.requestTeleport( tpPos.getX() + 0.5f, tpPos.getY(), tpPos.getZ() + 0.5f, player.getHeadYaw(), 0f );

                    w.playSound( null, tpPos, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 0.5f, 1.5f );
                    w.spawnParticles( ParticleTypes.POOF, tpPos.getX() + 0.5f, tpPos.getY(), tpPos.getZ() + 0.5f, 5, 0, 0, 0, 0.25f );

                    return true;
                }
            }
            pos = pos.up();
            count++;
        }
        return false;
    }

    @Override
    public void onCrouch( BlockPos pos , ServerPlayerEntity player )
    {
        if(!JumpVaderMod.getConfig().getEnabled())
            return;

        pos = pos.down();
        ServerWorld w = (ServerWorld) player.getWorld();
        int count = 0;

        while(count < JumpVaderMod.getConfig().getMaxVerticalBlocks() && pos.getY() >= -64)
        {
            Block blk = w.getBlockState( pos ).getBlock();

            if(blk instanceof JumpVaderBlock)
            {
                final BlockPos tpPos = pos.up();

                if(w.getBlockState( tpPos ).getBlock().equals( Blocks.AIR ) && w.getBlockState( tpPos.up() ).getBlock().equals( Blocks.AIR ))
                {
                    player.networkHandler.requestTeleport( tpPos.getX() + 0.5f, tpPos.getY(), tpPos.getZ() + 0.5f, player.getHeadYaw(), 0f );

                    w.playSound( null, tpPos, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 0.5f, 1.5f );
                    w.spawnParticles( ParticleTypes.POOF, tpPos.getX() + 0.5f, tpPos.getY(), tpPos.getZ() + 0.5f, 5, 0, 0, 0, 0.25f );

                    return;
                }
            }
            pos = pos.down();
            count++;
        }

    }

    private static final Identifier _identifier = new Identifier( JumpVaderMod.MODID, "jumpvader_block" );
}