package elec0.simplypowers;

import elec0.simplypowers.capabilities.IPowerData;
import elec0.simplypowers.capabilities.PowerData;
import elec0.simplypowers.capabilities.PowerDataProvider;
import elec0.simplypowers.powers.IPower;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class EventHandlerCommon 
{	
	@SubscribeEvent
	public void onLivingJumpEvent(LivingJumpEvent event)
	{
		if (event.getEntity() != null && event.getEntity() instanceof EntityPlayer && !event.getEntity().worldObj.isRemote)
		{
			IPowerData powerData = event.getEntity().getCapability(PowerDataProvider.POWER_CAP, null);
			IPower[] powers = powerData.getPowers();
			if(powers[0] != null && powers[1] != null)
			{
				powers[0].entityJump(event);
				powers[1].entityJump(event);
			}
			else
			{
				System.out.println("Err: Powers 0 or 1 are null. This shouldn't happen.");
			}
			
			/*double addY = 1.0D; // whatever value you want
			// player.setVelocity(player.motionX, player.motionY + addY, player.motionZ);
			// player.addVelocity(0.0D, addY, 0.0D);
			event.getEntity().motionY += addY;
			event.getEntity().velocityChanged = true;*/
		}
	}
	
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event)
	{
		// Want to keep the number of times this actually runs down, since there are always a shitload of entities
		if(event.getEntity() instanceof EntityPlayer)
		{
			if(event.getEntity() != null && !event.getEntity().worldObj.isRemote)
			{
				// Default walkspeed is 0.1
				EntityPlayer player = (EntityPlayer)event.getEntity();
				System.out.println(player.motionX + ", " + player.motionZ);
				player.motionX *= 1.2f;
				player.motionZ *= 1.2f;
				player.velocityChanged = true;
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerLogsIn(PlayerLoggedInEvent event)
	{
		EntityPlayer player = event.player;
		
		if(player.worldObj.isRemote) // Only server, apparently
			return;
		IPowerData powerData = player.getCapability(PowerDataProvider.POWER_CAP, null);
		player.addChatMessage(new TextComponentString("Power debug: " + powerData.toString()));
	
	}
	
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event)
	{
		if(event.isWasDeath() == true) // Don't copy data when returning from the End
			PowerData.copyPowerData(event.getOriginal(), event.getEntityPlayer());
	}
	
	@SubscribeEvent
	public void onEntityJoin(EntityJoinWorldEvent event)
	{

	}
}
