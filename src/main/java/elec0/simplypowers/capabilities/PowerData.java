package elec0.simplypowers.capabilities;

import java.util.Random;

import elec0.simplypowers.powers.IPower;
import elec0.simplypowers.powers.Powers;
import net.minecraft.entity.player.EntityPlayer;

public class PowerData implements IPowerData
{
	private int[] types, levels, powerIDs;
	private IPower[] powers;
	
	public PowerData()
	{
		// Generate new power
		types = new int[2];
		levels = new int[2];
		powerIDs = new int[2];
		powers = new IPower[2];
		generatePowers();
	}
	
	public void generatePowers()
	{
		/*
		 * Types: 0-10(11)
		 * 		Mover - Stranger (excepting Tinker as #11 for now)
		 * Powers: 0-X
		 * 		Number of powers created. X = Powers.getList(type)
		 * Levels: 20-100
		 * 		Strength of power. rand(80) + 20
		 * 		Min of 20, max of 100(?)
		 * 
		 */
		
		Random rand = new Random();
		setTypes(rand.nextInt(Powers.NUM_TYPES), rand.nextInt(Powers.NUM_TYPES));
		// Set power IDs to random value between 0 and maximum power ID for that given power type
		setPowerIDs(rand.nextInt(Powers.getMaxIDs(types[0])), rand.nextInt(Powers.getMaxIDs(types[1])));
		// Random level between min and max for power to start
		setLevels(rand.nextInt(Powers.NUM_MAX_GEN_POWER - Powers.NUM_MIN_GEN_POWER) + Powers.NUM_MIN_GEN_POWER, rand.nextInt(Powers.NUM_MAX_GEN_POWER - Powers.NUM_MIN_GEN_POWER) + Powers.NUM_MIN_GEN_POWER);
		
		genObjects();
	}
	
	/***
	 * Method to regenerate the IPower object, since I don't know how to store it in NBT data.
	 */
	@Override
	public void genObjects()
	{
		powers[0] = Powers.initPower(types[0]);
		powers[0].setID(powerIDs[0]);
		powers[0].setLevel(levels[0]);
		powers[1] = Powers.initPower(types[1]);
		powers[1].setID(powerIDs[1]);
		powers[1].setLevel(levels[1]);
	}
	
	@Override
	public void setTypes(int primary, int secondary) 
	{types = new int[] {primary, secondary};}

	@Override
	public void setTypes(int[] types) 
	{this.types = types;}

	@Override
	public void setPowers(IPower primary, IPower secondary) 
	{powers = new IPower[] {primary, secondary};}

	@Override
	public void setPowers(IPower[] powers) 
	{this.powers = powers;}

	@Override
	public void setLevels(int primary, int secondary) 
	{levels = new int[] {primary, secondary};}

	@Override
	public void setLevels(int[] levels) 
	{this.levels = levels;}

	@Override
	public int[] getTypes() 
	{return types;}

	@Override
	public IPower[] getPowers() 
	{return powers;}

	@Override
	public int[] getLevels() 
	{return levels;}

	@Override
	public void setPowerIDs(int primary, int secondary) 
	{powerIDs = new int[] {primary, secondary};}

	@Override
	public void setPowerIDs(int[] powerIDs) 
	{this.powerIDs = powerIDs;}

	@Override
	public int[] getPowerIDs() 
	{return powerIDs;}

	
	public String toString()
	{
		String ret = "types: " + types[0] + ", " + types[1] + " powersIDs: " + powerIDs[0] + ", " + powerIDs[1] + " levels: " + levels[0] + ", " + levels[1];
		ret += "\nPowers: 0: " + powers[0].toString() + ", 1: " + powers[1].toString();
		return ret;
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
		newData.setTypes(origData.getTypes());
		newData.setPowers(origData.getPowers());
		newData.setLevels(origData.getLevels());
	}

}
