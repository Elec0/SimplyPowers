package elec0.simplypowers;

import elec0.simplypowers.capabilities.IPowerData;
import elec0.simplypowers.capabilities.PowerData;
import elec0.simplypowers.capabilities.PowerDataProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

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
	public void onPlayerLogsIn(PlayerLoggedInEvent event)
	{
		EntityPlayer player = event.player;
		
		if(player.worldObj.isRemote) // Only server, apparently
			return;
		IPowerData powerData = player.getCapability(PowerDataProvider.POWER_CAP, null);
		player.addChatMessage(new TextComponentString("Your power level is " + powerData.getPowerLevel()));
	
	}
	
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event)
	{
		boolean wasDead = event.isWasDeath();
		if(wasDead == true) // Don't copy data when returning from the End
			PowerData.copyPowerData(event.getOriginal(), event.getEntityPlayer());
	}
	
	@SubscribeEvent
	public void onEntityJoin(EntityJoinWorldEvent event)
	{
		// Should use a capability for the loading of the power variables
		// Tutorial: http://www.planetminecraft.com/blog/forge-tutorial-capability-system/
		
		/*if(event.getEntity() instanceof EntityPlayer)
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
		}*/
	}
}
