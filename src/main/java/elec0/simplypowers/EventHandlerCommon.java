package elec0.simplypowers;

import elec0.simplypowers.capabilities.IPowerData;
import elec0.simplypowers.capabilities.PowerData;
import elec0.simplypowers.capabilities.PowerDataProvider;
import elec0.simplypowers.input.KeyBindings;
import elec0.simplypowers.network.PacketHandler;
import elec0.simplypowers.network.PacketSendKeyHold;
import elec0.simplypowers.powers.IPower;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

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
				System.err.println("onLivingJumpEvent: Powers 0 or 1 are null. This shouldn't happen.");
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event)
	{
		// Want to keep the number of times this actually runs down, since there are always a shitload of entities
		/*if(event.getEntity() instanceof EntityPlayer)
		{
			if(event.getEntity() != null && !event.getEntity().worldObj.isRemote)
			{
								
			}
		}*/
	}
    
	private boolean jumpPressed, power1Pressed, power2Pressed;
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{
		if(event.player != null && !event.player.worldObj.isRemote)
		{
			IPowerData powerData = event.player.getCapability(PowerDataProvider.POWER_CAP, null);
			IPower[] powers = powerData.getPowers();
			if(powers[0] != null && powers[1] != null)
			{
				powers[0].playerTick(event);
				powers[1].playerTick(event);
			}
			else
			{
				System.err.println("onPlayerTick: Powers 0 or 1 are null. This shouldn't happen.");
			}
		}
		// Running this on the client
		else if(event.player != null && event.player.worldObj.isRemote)
		{	
			if(Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown()){
				// Only update if jump wasn't pressed last tick
				if(!jumpPressed){
					jumpPressed = true;
					PacketHandler.INSTANCE.sendToServer(new PacketSendKeyHold(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode(), jumpPressed));
				}
			}
			else{
				if(jumpPressed){
					jumpPressed = false;
					PacketHandler.INSTANCE.sendToServer(new PacketSendKeyHold(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode(), jumpPressed));
				}
			}
			// Do the same thing for power key 1 and 2.
			if(KeyBindings.powerKey1.isKeyDown()){
				if(!power1Pressed){
					power1Pressed = true;
					PacketHandler.INSTANCE.sendToServer(new PacketSendKeyHold(KeyBindings.powerKey1.getKeyCode(), power1Pressed));
				}
			}
			else{
				if(power1Pressed){
					power1Pressed = false;
					PacketHandler.INSTANCE.sendToServer(new PacketSendKeyHold(KeyBindings.powerKey1.getKeyCode(), power1Pressed));
				}
			}
			if(KeyBindings.powerKey2.isKeyDown()){
				if(!power2Pressed){
					power2Pressed = true;
					PacketHandler.INSTANCE.sendToServer(new PacketSendKeyHold(KeyBindings.powerKey2.getKeyCode(), power2Pressed));
				}
			}
			else{
				if(power2Pressed){
					power2Pressed = false;
					PacketHandler.INSTANCE.sendToServer(new PacketSendKeyHold(KeyBindings.powerKey2.getKeyCode(), power2Pressed));
				}
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
		
		IPower[] powers = powerData.getPowers();
		if(powers[0] != null && powers[1] != null)
		{
			powers[0].playerLoggedIn(event);
			powers[1].playerLoggedIn(event);
		}
		else
		{
			System.err.println("onPlayerLogsIn: Powers 0 or 1 are null. This shouldn't happen.");
		}
	}
	
	@SubscribeEvent
	public void onPlayerFalls(LivingFallEvent event)
	{
		if(event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.getEntity();
			
			if(player.worldObj.isRemote)
				return;
			IPowerData powerData = player.getCapability(PowerDataProvider.POWER_CAP, null);
			
			IPower[] powers = powerData.getPowers();
			if(powers[0] != null && powers[1] != null)
			{
				powers[0].playerFalls(event);
				powers[1].playerFalls(event);
			}
			else
			{
				System.err.println("onPlayerFalls: Powers 0 or 1 are null. This shouldn't happen.");
			}
		}
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
