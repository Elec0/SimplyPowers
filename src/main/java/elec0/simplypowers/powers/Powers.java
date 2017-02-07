package elec0.simplypowers.powers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class Powers 
{
	public static final int NUM_TYPES = 1; // 11 for real. 12 with tinkers
	public static final int NUM_MIN_GEN_POWER = 20; // Minimum strength a power can be at generation
	public static final int NUM_MAX_GEN_POWER = 80; // Maximum strength a power can be at generation
	public static final int NUM_MAX_POWER = 100; // Absolute maximum power strength
	
	public static final int NUM_DATA_MAX = 10;
	
	public static final UUID POWER_1_SPEED_BOOST_ID = UUID.fromString("61b127a6-db97-11e6-bf26-cec0c932ce01");
	public static final UUID POWER_2_SPEED_BOOST_ID = UUID.fromString("61b12c56-db97-11e6-bf26-cec0c932ce01");
	
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
	
}
