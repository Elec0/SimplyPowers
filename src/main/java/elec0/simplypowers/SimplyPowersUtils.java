package elec0.simplypowers;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class SimplyPowersUtils 
{

	/**
	 * Does source block have line of sight to dest block
	 * @param world
	 * @param source
	 * @param dest
	 * @return true if can see, false if not
	 */
	public static boolean canBlockSeeBlock(World world, Vec3d vecSource, Vec3d vecDest)
	{
		// The raytrace is one block short, so if we add the direction of the vector from source->dest, it pushes the raytrace over the lip to give the right answer
		Vec3d direction = vecDest.subtract(vecSource);
		direction = direction.normalize(); // Also normalize so we don't go looking past the block we want. It still works, but it's messy
		RayTraceResult res = world.rayTraceBlocks(vecSource, vecDest.add(direction), false, true, false);
		
		BlockPos dest = new BlockPos(vecDest.xCoord, vecDest.yCoord, vecDest.zCoord); // We can't compare Vec3d and Vec3i/BlockPos.
		
		if(res != null && res.getBlockPos().equals(dest))
			return true;
		else
			return false;
	}
}
