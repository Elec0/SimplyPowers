package elec0.simplypowers.powers;

import java.util.UUID;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class Powers 
{
	public static final int NUM_TYPES = 1; // 11 for real. 12 with tinkers
	public static final int NUM_MIN_GEN_POWER = 20; // Minimum strength a power can be at generation
	public static final int NUM_MAX_GEN_POWER = 80; // Maximum strength a power can be at generation
	public static final int NUM_MAX_POWER = 100; // Absolute maximum power strength
	
	private static final UUID POWER_1_SPEED_BOOST_ID = UUID.fromString("61b127a6-db97-11e6-bf26-cec0c932ce01");
	private static final UUID POWER_2_SPEED_BOOST_ID = UUID.fromString("61b12c56-db97-11e6-bf26-cec0c932ce01");
	
	public static String[] NAME_TYPES = new String[] {"Mover", "Shaker", "Brute", "Breaker", "Master", "Blaster", "Thinker", "Striker", "Changer", "Trump", "Stranger"};
	
	/***
	 * Get the maximum number of power IDs for a given type/category
	 * @param type
	 * @return
	 */
	public static int getMaxIDs(int type)
	{
		switch(type)
		{
		case 0:
			return Mover.NUM_IDS;
		}
		
		return 1; // Debug
	}
	
	public static IPower initPower(int type)
	{
		IPower power = null;
		switch(type)
		{
		case 0:
			power = new Mover();
			break;
		}
		return power;
	}
	
	
	public static class Mover implements IPower
	{
		// Total number of powers, or IDs, in this type
		public static final int NUM_IDS = 1;
		
		// Non-static fields
		
		private int ID;
		private int level; // Percentage 0-100 of power
		private int active; // If the power is on or not. Sometimes doesn't matter
		
		
		public Mover()
		{
			ID = 0;
			level = 0;
			active = 0;
		}
		public Mover(int ID, int level)
		{
			this.ID = ID;
			this.level = level;
		}
		
		@Override
		public void entityJump(LivingJumpEvent event) 
		{
			switch(ID)
			{
			case 0:
				event.getEntity().motionY *= 1;
				event.getEntity().velocityChanged = true;
				System.out.println("entityJump called.");
				break;
			}
		}
		
		@Override
		public void playerLoggedIn(PlayerLoggedInEvent event)
		{
			
			switch(ID)
			{
			case 0:
				
				AttributeModifier POWER_1_SPEED_BOOST = (new AttributeModifier(POWER_1_SPEED_BOOST_ID, "Power 1 speedboost", level / 100D, 2)).setSaved(false);
				AttributeModifier POWER_2_SPEED_BOOST = (new AttributeModifier(POWER_2_SPEED_BOOST_ID, "Power 2 speedboost", level / 100D, 2)).setSaved(false);
				IAttributeInstance iattributeinstance = event.player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

		        if (iattributeinstance.getModifier(POWER_1_SPEED_BOOST_ID) == null)
		        {
		        	iattributeinstance.applyModifier(POWER_1_SPEED_BOOST);
		        	
		        }
		        
		        if (iattributeinstance.getModifier(POWER_2_SPEED_BOOST_ID) == null)
		        {
		        	iattributeinstance.applyModifier(POWER_2_SPEED_BOOST);
		        }
		        
		        break;
			}
		}
		
		@Override
		public void activate()
		{
			if(active == 0)
				active = 1;
			else
				active = 0;
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
		{this.active = active;}
		@Override
		public int getActive()
		{return active;}
		
		public String toString()
		{
			return "ID: " + ID + ", level: " + level;
		}
	}
	
}
