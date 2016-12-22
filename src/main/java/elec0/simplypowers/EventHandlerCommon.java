package elec0.simplypowers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerCommon 
{
	@SubscribeEvent
	public void onArrowNocked(ArrowNockEvent event)
	{
		System.out.println("Arrow nocked!");
	}
	
	@SubscribeEvent
	public void onLivingJumpEvent(LivingJumpEvent event)
	{
		if (event.getEntity() != null && event.getEntity() instanceof EntityPlayer)
		{
			double addY = 1.0D; // whatever value you want
			// player.setVelocity(player.motionX, player.motionY + addY, player.motionZ);
			// player.addVelocity(0.0D, addY, 0.0D);
			event.getEntity().motionY += addY;
			event.getEntity().velocityChanged = true;
		}
	}
	
	@SubscribeEvent
	public void onEntityJoin(EntityJoinWorldEvent event)
	{
		// Should use a capability for the loading of the power variables
		// Tutorial: http://www.planetminecraft.com/blog/forge-tutorial-capability-system/
		
		if(event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.getEntity();
			World world = event.getWorld();
			
			if(!world.isRemote)
			{				
				System.out.println("\tPlayer Joined");
				NBTTagCompound tag = player.getEntityData();
				
				NBTBase modeTag = tag.getTag("testSave");
				
				if(modeTag != null)
				{
					player.addChatComponentMessage(new TextComponentString("Tag exists. Value: " + tag.getInteger("testSave")), false);
				}
				tag.setInteger("testSave", 150);
			}
		}
	}
}
