package elec0.simplypowers.blocks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {
	public static BlockPowerPortal powerPortal;
	
	public static void init()
	{
		powerPortal = new BlockPowerPortal();
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels()
	{
		
	}
	
	@SideOnly(Side.CLIENT)
	public static void initItemModels()
	{
		
	}
}
