package elec0.simplypowers.powers;

import elec0.simplypowers.capabilities.PowerData;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;

public class Powers 
{
	public static final int NUM_TYPES = 1; // 11 for real. 12 with tinkers
	public static final int NUM_MIN_GEN_POWER = 20; // Minimum strength a power can be at generation
	public static final int NUM_MAX_GEN_POWER = 80; // Maximum strength a power can be at generation
	public static final int NUM_MAX_POWER = 100; // Absolute maximum power strength
	
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
		
		public Mover()
		{
			ID = 0;
			level = 0;
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
				event.getEntity().motionY += 1;
				event.getEntity().velocityChanged = true;
				System.out.println("entityJump called.");
				break;
			}
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
		
		public String toString()
		{
			return "ID: " + ID + ", level: " + level;
		}
	}
	
}
