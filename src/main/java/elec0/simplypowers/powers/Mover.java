package elec0.simplypowers.powers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class Mover implements IPower
{
	// Total number of powers, or IDs, in this type
	public static final int NUM_IDS = 2;
	
	// Power progressions required to level up, as it were
	public static final int POWER_0_PROGRESSION = 100;
	public static final int POWER_1_PROGRESSION = 10;
	
	// Non-static fields		
	private int ID;
	private int level; // Percentage 0-100 of power
	private boolean active; // If the power is on or not. Sometimes doesn't matter. This is easier to have internally as a bool. Saving is easier as an int/byte.
	private boolean prevActive; // The previous value of active last tick. Isn't saved.
	private int progression;
	private int[] data; // Power-specific data storage
	private List<Integer> keysPressed;
	
	private BlockPos lastPos; // For use in calculating distance walked. Don't need to save, will lose at most 1 progression point.
	private long tickVal, lastTick;
	
	public Mover()
	{
		ID = 0;
		level = 0;
		active = false;
		progression = 0;
		keysPressed = new ArrayList<Integer>(); // Just to make sure we don't have a null variable hanging around
	}
	public Mover(int ID, int level)
	{
		this();
		this.ID = ID;
		this.level = level;
	}
	
	@Override
	public void entityJump(LivingJumpEvent event)
	{
		if(!active)
			return;
		
		switch(getID())
		{
		case 1:
			// Default jump motion is 0.42D
			double maxJump = 0.42D * 3 - 0.42D; // Addition of default jump distance
			double val = maxJump * (level / 100f);
			event.getEntity().motionY += val;
			event.getEntity().velocityChanged = true;
			addProgression(1);
			System.out.println("entityJump called. " + val); // This is a helpful debug tool for now.
			break;
		}
	}
	
	@Override
	public void playerLoggedIn(PlayerLoggedInEvent event)
	{
		
		switch(getID())
		{
		case 0:
			applySpeedBoost(active, event.player);
	        
	        break;
		}
	}
	
	private void applySpeedBoost(boolean apply, EntityPlayer player)
	{
		// I don't like this. It should be able to be defined elsewhere, but I need access to the AttributeModifiers in other methods than just
		// 		the playerLoggedIn. Might need to add another switch case in here for modifying the power formulas.
		//		If so, remove the switch from playerLoggedIn and just call this method to handle it.
		
		// The point of this is because the other power might also have a speed boost, but there should not be more than 2 speed boosts.
		AttributeModifier power1Speed = (new AttributeModifier(Powers.POWER_1_SPEED_BOOST_ID, "Power 1 speedboost", level / 100D, 2)).setSaved(false);
		AttributeModifier power2Speed = (new AttributeModifier(Powers.POWER_2_SPEED_BOOST_ID, "Power 2 speedboost", level / 100D, 2)).setSaved(false);
		IAttributeInstance iattributeinstance = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
		
		if(apply)
		{
			// If the boost isn't active, apply it. 
			// If another power activates a speed boost first then activate the second ID for this one
	        if (iattributeinstance.getModifier(Powers.POWER_1_SPEED_BOOST_ID) == null)
	        {
	        	iattributeinstance.applyModifier(power1Speed);
	        }
	        else
	        {
		        if (iattributeinstance.getModifier(Powers.POWER_2_SPEED_BOOST_ID) == null)
		        {
		        	iattributeinstance.applyModifier(power2Speed);
		        }
	        }
		}
		else
		{
			// This..is probably not a good idea, because it will always disable the first boost no matter which power is deactivated
			// This needs to be rethought. For now it will work.
			if (iattributeinstance.getModifier(Powers.POWER_1_SPEED_BOOST_ID) != null)
	        {
	        	iattributeinstance.removeModifier(power1Speed);
	        }
	        else
	        {
		        if (iattributeinstance.getModifier(Powers.POWER_2_SPEED_BOOST_ID) != null)
		        {
		        	iattributeinstance.removeModifier(power2Speed);
		        }
	        }
		}
	}
	
	@Override
	public void playerTick(PlayerTickEvent event)
	{
		EntityPlayer player = event.player;
		++tickVal;
		checkProgression();
		
		switch(getID())
		{
		case 0:
			if(prevActive != active)
			{
				applySpeedBoost(active, player);
			}
			
			if(lastPos != null)
			{
				// If the player has moved blocks
				if(!lastPos.equals(player.getPosition()))
				{
					// Get horizontal distance only. Falling or jumping shouldn't apply to progression for super speed
					double dist = Math.sqrt(Math.pow(lastPos.getX() - player.getPosition().getX(), 2) + Math.pow(lastPos.getZ() - player.getPosition().getZ(), 2));
					if(dist > 0 && dist < 10) // If they're moving more than 10 blocks a tick something interesting is happening
					{
						addProgression((int)dist);
						lastPos = player.getPosition();
					}
				}
			}
			else // lastPos is null
			{
				lastPos = event.player.getPosition();
			}
			break;
		case 1:
			if(prevActive != active)
			{
				if(progression > 50) // If power has progressed enough to allow flight TODO: Change.
				{
					player.capabilities.allowFlying = active;
					// Make the player stop flying if they deactivated the power
					if(!player.capabilities.allowFlying && player.capabilities.isFlying)
						player.capabilities.isFlying = false;
					player.sendPlayerAbilities();
				}
			}
			if(active)
			{
				if(keysPressed.contains(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode()))
				{
					// Hover or fall slowly
					player.fallDistance = 0;
					// Prevent falling motion only if they have negative falling motion
					// In other words, don't prevent jumping
					if(player.motionY < 0)
					{
						player.motionY = 0;
						player.velocityChanged = true;
					}
				}
			}
			break;
		}
		prevActive = active;
	}
	
	/**
	 * Use this method to check if the progression level exceeded the level up point.
	 * If that's the case, increment the power level.
	 * Check every 10 ticks.
	 */
	public void checkProgression()
	{		
		// Don't calculate progression if the power isn't active
		if(!active)
			return;
		if(tickVal - lastTick < 10)
			return;
		lastTick = tickVal;

		boolean progressed = false;
		
		switch(getID())
		{
		case 0:
			if(getProgression() > POWER_0_PROGRESSION)
			{
				setLevel(getLevel() + 1);
				setProgression(0);
				progressed = true;
			}
			break;
		case 1:
			if(getProgression() > POWER_1_PROGRESSION)
			{
				setLevel(getLevel() + 1);
				setProgression(0);
				progressed = true;
			}
		}
		if(progressed)
		{
			
			System.out.println("Power " + getID() + " has progressed to " + getLevel());
		}
	}
	
	
	@Override
	/**
	 * Toggle active status
	 * @returns new status
	 */
	public int activate()
	{
		if(active == false)
		{
			active = true;
		}
		else
		{
			active = false;
		}
		return (active ? 1: 0);
	}

	@Override
	public void setID(int ID) 
	{this.ID = ID;}
	@Override
	public int getID() 
	{return ID;}
	@Override
	public void setLevel(int level) 
	{this.level = level;}
	@Override
	public int getLevel() 
	{return level;}
	@Override
	public void setActive(int active)
	{this.active = (active == 1 ? true : false);}
	@Override
	public int getActive()
	{return (active ? 1 : 0);}
	@Override
	public void setProgression(int progression)
	{this.progression = progression;}
	@Override
	public void addProgression(int progression)
	{this.progression += progression;}
	@Override
	public int getProgression()
	{return progression;}
	@Override
	public void setData(int[] data)
	{this.data = data;}
	@Override
	public int[] getData()
	{return data;}
	@Override
	public void setKeyStatus(List<Integer> keysPressed) 
	{this.keysPressed = keysPressed;}
	
	@Override
	public String toString()
	{
		return "ID: " + ID + ", level: " + level + ", active: " + active;
	}
	
}