package me.modmuss50.fr.client

import mcmultipart.client.multipart.ISmartMultipartModel
import me.modmuss50.fr.FluxedRedstone
import net.minecraft.block.state.IBlockState
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model.*
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.resources.model.IBakedModel
import net.minecraft.client.resources.model.ModelRotation
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.property.ExtendedBlockState
import net.minecraftforge.common.property.IExtendedBlockState
import org.lwjgl.util.vector.Vector3f
import reborncore.common.misc.vecmath.Vecs3dCube
import java.util.*


class PipeModel : ISmartMultipartModel {

    internal var faceBakery = FaceBakery()
    internal var texture: TextureAtlasSprite? = null
    internal var capTexture: TextureAtlasSprite? = null

    var state: IExtendedBlockState? = null

    init {
        texture = Minecraft.getMinecraft().textureMapBlocks.getAtlasSprite("fluxedredstone:blocks/cable")
        capTexture = Minecraft.getMinecraft().textureMapBlocks.getAtlasSprite("fluxedredstone:blocks/cap")
    }

    constructor(state: IExtendedBlockState) {
        this.state = state
    }

    constructor() {

    }

    override fun handlePartState(partSate: IBlockState?): IBakedModel? {
        return PipeModel(partSate as IExtendedBlockState)
    }

    override fun getFaceQuads(p_177551_1_: EnumFacing): List<BakedQuad> {
        return ArrayList()
    }

    override fun getGeneralQuads(): List<BakedQuad> {
        val list = ArrayList<BakedQuad>()
        val uv = BlockFaceUV(floatArrayOf(0.0f, 0.0f, 16.0f, 16.0f), 0)
        val face = BlockPartFace(null, 0, "", uv)
        addCubeToList(Vecs3dCube(4.0, 4.0, 4.0, 12.0, 12.0, 12.0), list, face, ModelRotation.X0_Y0, texture!!)
        if (state != null) {
            if (state!!.getValue(FluxedRedstone.stateHelper.UP)) {
                addCubeToList(Vecs3dCube(4.0, 12.0, 4.0, 12.0, 16.0, 12.0), list, face, ModelRotation.X0_Y0, texture!!)
            }
            if (state!!.getValue(FluxedRedstone.stateHelper.DOWN)) {
                addCubeToList(Vecs3dCube(4.0, 0.0, 4.0, 12.0, 4.0, 12.0), list, face, ModelRotation.X0_Y0, texture!!)
            }
            if (state!!.getValue(FluxedRedstone.stateHelper.NORTH)) {
                addCubeToList(Vecs3dCube(4.0, 4.0, 0.0, 12.0, 12.0, 12.0), list, face, ModelRotation.X0_Y0, texture!!)
            }
            if (state!!.getValue(FluxedRedstone.stateHelper.SOUTH)) {
                addCubeToList(Vecs3dCube(4.0, 4.0, 4.0, 12.0, 12.0, 16.0), list, face, ModelRotation.X0_Y0, texture!!)
            }
            if (state!!.getValue(FluxedRedstone.stateHelper.EAST)) {
                addCubeToList(Vecs3dCube(4.0, 4.0, 4.0, 16.0, 12.0, 12.0), list, face, ModelRotation.X0_Y0, texture!!)
            }
            if (state!!.getValue(FluxedRedstone.stateHelper.WEST)) {
                addCubeToList(Vecs3dCube(0.0, 4.0, 4.0, 12.0, 12.0, 12.0), list, face, ModelRotation.X0_Y0, texture!!)
            }
        }

        return list
    }

    fun addCubeToList(cube: Vecs3dCube, list: ArrayList<BakedQuad>, face: BlockPartFace, modelRotation: ModelRotation, cubeTexture: TextureAtlasSprite) {
        list.add(faceBakery.makeBakedQuad(Vector3f(cube.minX.toFloat(), cube.minY.toFloat(), cube.minZ.toFloat()), Vector3f(cube.maxX.toFloat(), cube.minY.toFloat(), cube.maxZ.toFloat()), face, cubeTexture, EnumFacing.DOWN, modelRotation, null, true, true))//down
        list.add(faceBakery.makeBakedQuad(Vector3f(cube.minX.toFloat(), cube.maxY.toFloat(), cube.minZ.toFloat()), Vector3f(cube.maxX.toFloat(), cube.maxY.toFloat(), cube.maxZ.toFloat()), face, cubeTexture, EnumFacing.UP, modelRotation, null, true, true))//up
        list.add(faceBakery.makeBakedQuad(Vector3f(cube.minX.toFloat(), cube.minY.toFloat(), cube.minZ.toFloat()), Vector3f(cube.maxX.toFloat(), cube.maxY.toFloat(), cube.maxZ.toFloat()), face, cubeTexture, EnumFacing.NORTH, modelRotation, null, true, true))//north
        list.add(faceBakery.makeBakedQuad(Vector3f(cube.minX.toFloat(), cube.minY.toFloat(), cube.maxZ.toFloat()), Vector3f(cube.maxX.toFloat(), cube.maxY.toFloat(), cube.maxZ.toFloat()), face, cubeTexture, EnumFacing.SOUTH, modelRotation, null, true, true))//south
        list.add(faceBakery.makeBakedQuad(Vector3f(cube.maxX.toFloat(), cube.minY.toFloat(), cube.minZ.toFloat()), Vector3f(cube.maxX.toFloat(), cube.maxY.toFloat(), cube.maxZ.toFloat()), face, cubeTexture, EnumFacing.EAST, modelRotation, null, true, true))//east
        list.add(faceBakery.makeBakedQuad(Vector3f(cube.minX.toFloat(), cube.minY.toFloat(), cube.minZ.toFloat()), Vector3f(cube.minX.toFloat(), cube.maxY.toFloat(), cube.maxZ.toFloat()), face, cubeTexture, EnumFacing.WEST, modelRotation, null, true, true))//west
    }

    override fun isAmbientOcclusion(): Boolean {
        return false
    }

    override fun isGui3d(): Boolean {
        return true
    }

    override fun isBuiltInRenderer(): Boolean {
        return false
    }

    override fun getParticleTexture(): TextureAtlasSprite {
        return texture!!
    }

    override fun getItemCameraTransforms(): ItemCameraTransforms? {
        return null
    }
}