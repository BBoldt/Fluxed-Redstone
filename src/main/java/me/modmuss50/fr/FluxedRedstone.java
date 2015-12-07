package me.modmuss50.fr;

import me.modmuss50.fr.block.BlockPipe;
import me.modmuss50.fr.client.PipeModelBakery;
import me.modmuss50.fr.tile.TilePipe;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import reborncore.api.TextureRegistry;

import java.util.HashMap;

@Mod(modid = "fluxedredstone", name = "FluxedRedstone", version = "@MODVERSION@")
public class FluxedRedstone {

    public static HashMap<PipeTypeEnum, Block> pipeTypeEnumBlockHashMap = new HashMap<>();


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        for(PipeTypeEnum typeEnum : PipeTypeEnum.values()){
            Block blockPipe = new BlockPipe(typeEnum);
            GameRegistry.registerBlock(blockPipe, "FRPipe." + typeEnum.getFriendlyName()).setUnlocalizedName("FRPipe." + typeEnum.getFriendlyName());
            pipeTypeEnumBlockHashMap.put(typeEnum, blockPipe);
        }
        GameRegistry.registerTileEntity(TilePipe.class, "FRTilePipe");
        MinecraftForge.EVENT_BUS.register(new PipeModelBakery());
    }

}
