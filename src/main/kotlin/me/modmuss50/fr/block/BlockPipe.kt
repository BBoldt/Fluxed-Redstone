package me.modmuss50.fr.block

import me.modmuss50.fr.PipeTypeEnum
import me.modmuss50.fr.WorldState
import me.modmuss50.fr.caps.TestCap
import me.modmuss50.fr.raytrace.RayTracer
import me.modmuss50.fr.tile.TilePipe
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.Minecraft
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.*
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import reborncore.common.misc.vecmath.Vecs3dCube
import java.util.*


class BlockPipe(val  type: PipeTypeEnum) : BlockContainer(Material.iron) {

    init {
        this.setCreativeTab(CreativeTabs.tabRedstone)
        defaultState = this.blockState.baseState
        this.setBlockBounds(0.25f, 0.25f, 0.25f, 0.75f, 0.75f, 0.75f)
    }

    override fun onBlockActivated(worldIn: World?, pos: BlockPos?, state: IBlockState?, playerIn: EntityPlayer?, side: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        if(playerIn!!.heldItem != null && playerIn.heldItem.item == Items.apple){
            var pipe = worldIn!!.getTileEntity(pos) as TilePipe;
            if(!pipe.hasCap(side!!)){
                return pipe.addCap(side, TestCap())
            } else {
                return pipe.removeCap(side)
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ)
    }

    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity {
        return TilePipe()
    }

    override fun getRenderType(): Int {
        return 3
    }

    override fun isBlockNormalCube(): Boolean {
        return false
    }

    override fun isOpaqueCube(): Boolean {
        return false
    }

    override fun isFullBlock(): Boolean {
        return false
    }

    override fun isFullCube(): Boolean {
        return false
    }

    override fun isSideSolid(world: IBlockAccess?, pos: BlockPos?, side: EnumFacing?): Boolean {
        return false
    }

    @SideOnly(Side.CLIENT)
    override fun getBlockLayer(): EnumWorldBlockLayer {
        return EnumWorldBlockLayer.CUTOUT
    }

    override fun getExtendedState(state: IBlockState?, world: IBlockAccess?, pos: BlockPos?): IBlockState? {
        return WorldState(world, pos, type);
    }

    override fun addCollisionBoxesToList(worldIn: World?, pos: BlockPos?, state: IBlockState?, mask: AxisAlignedBB?, list: MutableList<AxisAlignedBB>?, collidingEntity: Entity?) {
        list!!.addAll(getAxisis(worldIn, pos)!!)
    }

    fun getAxisis(worldIn: World?, pos: BlockPos?) : List<AxisAlignedBB>?{
        var list = ArrayList<AxisAlignedBB>()
        if(worldIn!!.getTileEntity(pos) is TilePipe){
            var pipe = worldIn.getTileEntity(pos) as TilePipe
            list.add(Vecs3dCube(0.25, 0.25, 0.25, 0.75, 0.75, 0.75).toAABB())

            if (pipe.connects(EnumFacing.UP) || pipe.hasCap(EnumFacing.UP)) {
                list.add(Vecs3dCube(4.0/16, 12.0/16, 4.0/16, 12.0/16, 16.0/16, 12.0 /16).toAABB())
            }
            if (pipe.connects(EnumFacing.DOWN) || pipe.hasCap(EnumFacing.DOWN)) {
                list.add(Vecs3dCube(4.0/16, 0.0/16, 4.0/16, 12.0/16, 4.0/16, 12.0 /16).toAABB())
            }
            if (pipe.connects(EnumFacing.NORTH) || pipe.hasCap(EnumFacing.NORTH)) {
                list.add(Vecs3dCube(4.0/16, 4.0/16, 4.0/16, 12.0/16, 12.0/16, 0.0 /16).toAABB())
            }
            if (pipe.connects(EnumFacing.SOUTH) || pipe.hasCap(EnumFacing.SOUTH)) {
                list.add(Vecs3dCube(4.0/16, 4.0/16, 12.0/16, 12.0/16, 12.0/16, 16.0 /16).toAABB())
            }
            if (pipe.connects(EnumFacing.EAST) || pipe.hasCap(EnumFacing.EAST)) {
                list.add(Vecs3dCube(12.0/16, 4.0/16, 4.0/16, 16.0/16, 12.0/16, 12.0 /16).toAABB())
            }
            if (pipe.connects(EnumFacing.WEST) || pipe.hasCap(EnumFacing.WEST)) {
                list.add(Vecs3dCube(4.0/16, 4.0/16, 4.0/16, 0.0/16, 12.0/16, 12.0 /16).toAABB())
            }
        }
        return list
    }

    override fun getCollisionBoundingBox(worldIn: World?, pos: BlockPos?, state: IBlockState?): AxisAlignedBB? {
        return Vecs3dCube(0.25, 0.25, 0.25, 0.75, 0.75, 0.75).toAABB()
    }

    override fun collisionRayTrace(worldIn: World?, pos: BlockPos?, start: Vec3?, end: Vec3?): MovingObjectPosition? {
        var tracer = RayTracer()
        var result = tracer.getCollision(worldIn!!, pos!!, start!!, end!!, getAxisis(worldIn, pos)!!)
        if(result.valid()){
            return result.hit
        }
        return null
    }

    override fun getSelectedBoundingBox(worldIn: World?, pos: BlockPos?): AxisAlignedBB? {
        var tracer = RayTracer()
        var result = tracer.getCollision(worldIn!!, pos!!, Minecraft.getMinecraft().thePlayer, getAxisis(worldIn, pos)!!)
        if(result.valid()){
            return result.box!!.offset(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble())
        }
        return super.getSelectedBoundingBox(worldIn, pos)
    }
}