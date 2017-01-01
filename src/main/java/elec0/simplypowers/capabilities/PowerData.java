package elec0.simplypowers.capabilities;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

public class PowerData implements IPowerData
{

	private int powerLevel;
	
	public PowerData()
	{
		Random rand = new Random();
		setPowerLevel(rand.nextInt(256));
	}
	
	@Override
	public int getPowerLevel() {
		return powerLevel;
	}

	@Override
	public void setPowerLevel(int level) {
		powerLevel = level;
	}

	/***
	 * Static method to handle copying data from one player to another.
	 * Used in PlayerEvent.Clone when respawning.
	 * @param originalPlayer
	 * @param newPlayer
	 */
	public static void copyPowerData(EntityPlayer originalPlayer, EntityPlayer newPlayer) 
	{
		IPowerData origData = originalPlayer.getCapability(PowerDataProvider.POWER_CAP, null);
		IPowerData newData = newPlayer.getCapability(PowerDataProvider.POWER_CAP, null);
		
		// Copy data
		newData.setPowerLevel(origData.getPowerLevel());
	}

}
