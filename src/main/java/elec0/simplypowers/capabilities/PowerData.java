package elec0.simplypowers.capabilities;

import java.util.ArrayList;
import java.util.Random;

import elec0.simplypowers.powers.IPower;
import elec0.simplypowers.powers.Powers;
import net.minecraft.entity.player.EntityPlayer;

public class PowerData implements IPowerData
{
	private int[] types, levels, powerIDs, actives, progression, progressionLvl;
	private int[][] data;
	private IPower[] powers;
	private ArrayList<Integer> keysPressed;
	
	public PowerData()
	{
		// Generate new power
		types = new int[2];
		levels = new int[2];
		powerIDs = new int[2];
		actives = new int[2];
		progression = new int[2];
		progressionLvl = new int[2];
		powers = new IPower[2];
		data = new int[2][Powers.NUM_DATA_MAX];
		keysPressed = new ArrayList();
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
		 * Actives: 0/1
		 * 		If the power is activated or not. It might not be possible
		 * 		to activate the power, in which case the value doesn't matter.
		 * 
		 * Progression: 0-maxInt
		 * 		This is how we're keeping track of when to level a power up by a percent point.
		 * 		Once the progression value gets above a certain level, increment the level.
		 * 
		 * Progression level: 1-maxInt
		 * 		The threshold a progression must pass to increase in power.
		 * 		Base values are defined in Powers.PROGRESSION_LEVEL_BASE in a 2D array
		 */
		
		Random rand = new Random();
		
		int type1 = rand.nextInt(Powers.NUM_TYPES);
		int type2 = rand.nextInt(Powers.NUM_TYPES);
		setTypes(type1, type2);
		
		// Set power IDs to random value between 0 and maximum power ID for that given power type
		int ID1 = rand.nextInt(Powers.getMaxIDs(types[0]));
		int ID2 = rand.nextInt(Powers.getMaxIDs(types[1]));
		setPowerIDs(ID1, ID2);
		
		// Random level between min and max for power to start
		int power1 = rand.nextInt(Powers.NUM_MAX_GEN_POWER - Powers.NUM_MIN_GEN_POWER) + Powers.NUM_MIN_GEN_POWER;
		int power2 = rand.nextInt(Powers.NUM_MAX_GEN_POWER - Powers.NUM_MIN_GEN_POWER) + Powers.NUM_MIN_GEN_POWER;
		setLevels(power1, power2);
		
		// Progression level requirements
		int progLvl1 = Powers.PROGRESSION_LEVEL_BASE[type1][ID1];
		int progLvl2 = Powers.PROGRESSION_LEVEL_BASE[type2][ID2];
		setProgressionLevel(progLvl1, progLvl2);
		
		
		actives[0] = 0;
		actives[0] = 0; 
		progression[0] = 0;
		progression[1] = 0;
		progressionLvl[0] = 1;
		progressionLvl[0] = 1;
		genObjects();
	}
	
	
	@Override
	/**
	 * Use this to ensure all data in PowerData is the same as in Power.
	 * Power makes changes to the power while running.
	 */
	public void syncData()
	{
		for(int i = 0; i < 2; ++i)
		{
			levels[i] = powers[i].getLevel();
			powerIDs[i] = powers[i].getID();
			actives[i] = powers[i].getActive();
			progression[i] = powers[i].getProgression();
			progressionLvl[i] = powers[i].getProgressionLevel();
			data[i] = powers[i].getData();
		}
	}
	
	/***
	 * Method to regenerate the IPower object, since I don't know how to store it in NBT data.
	 */
	@Override
	public void genObjects()
	{
		if(actives.length <= 0)
		{
			actives = new int[2];
			actives[0] = 0;
			actives[1] = 0;
		}
		if(progression.length <= 0)
		{
			progression = new int[2];
			progression[0] = 0;
			progression[1] = 0;
		}
		if(progressionLvl.length <= 0)
		{
			progressionLvl = new int[2];
			progressionLvl[0] = Powers.PROGRESSION_LEVEL_BASE[types[0]][powerIDs[0]];
			progressionLvl[1] = Powers.PROGRESSION_LEVEL_BASE[types[1]][powerIDs[1]];
		}
		if(data.length <= 0)
		{
			data = new int[2][Powers.NUM_DATA_MAX];
		}
		else if(data[0].length == 0)
		{
			data[0] = new int[Powers.NUM_DATA_MAX];
			data[1] = new int[Powers.NUM_DATA_MAX];

		}
		
		for(int i = 0; i < 2; ++i)
		{
			powers[i] = Powers.initPower(types[i]);
			powers[i].setID(powerIDs[i]);
			powers[i].setLevel(levels[i]);
			powers[i].setActive(actives[i]);
			powers[i].setProgression(progression[i]);
			powers[i].setProgressionLevel(progressionLvl[i]);
			powers[i].setData(data[i]);
		}		
	}
	
	/**
	 * Used to update the status of keys being held down from client.
	 * @param keyCode
	 * @param isPressed
	 */
	@Override
	public void keyStatus(int keyCode, boolean isPressed)
	{
		// If the arraylist has the key and it's no longer pressed, remove it from the list
		// else, if it is pressed, add it to the list
		if(keysPressed.contains(keyCode))
		{
			if(!isPressed)
				keysPressed.remove((Integer)keyCode);
		}
		else
		{
			if(isPressed)
				keysPressed.add(keyCode);
		}
		
		// Give powers the data, but keep this method's code unique
		powers[0].setKeyStatus(keysPressed);
		powers[1].setKeyStatus(keysPressed);

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

	@Override
	public void setActives(int primary, int secondary)
	{actives = new int[]{primary, secondary};}
	
	@Override
	public void setActives(int[] actives)
	{this.actives = actives;}
	
	@Override
	public int[] getActives()
	{return actives;}
	
	@Override
	public void setProgression(int primary, int secondary)
	{progression = new int[]{primary, secondary};}
	
	@Override
	public void setProgression(int[] progression)
	{this.progression = progression;}
	
	@Override
	public int[] getProgression()
	{return progression;}
	
	@Override
	public void setProgressionLevel(int primary, int secondary)
	{progressionLvl = new int[]{primary, secondary};}
	
	@Override
	public void setProgressionLevel(int[] progressionLvl)
	{this.progressionLvl = progressionLvl;}
	
	@Override
	public int[] getProgressionLevel()
	{return progressionLvl;}
	
	@Override
	public void setData(int[] primary, int[] secondary)
	{this.data = new int[][]{primary, secondary};}
	
	@Override
	public void setData(int[][] data)
	{this.data = data;}
	
	@Override
	public int[][] getData()
	{return data;}
	
	public String toString()
	{
		String ret = "types: [" + types[0] + ", " + types[1] + "]: [" + Powers.NAME_TYPES[types[0]] + ", " + Powers.NAME_TYPES[types[1]] + "]";
		ret += "\nPowers: 0: [" + powers[0].toString() + "], 1: [" + powers[1].toString() + "]";
		ret += "\nProgression: [" + powers[0].getProgression() + "/" + powers[0].getProgressionLevel() + "], [" + powers[1].getProgression() + "/" + powers[1].getProgressionLevel() + "]";
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
		newData.setActives(origData.getActives());
		newData.setProgression(origData.getProgression());
		newData.setProgressionLevel(origData.getProgressionLevel());
		newData.setData(origData.getData());
		
		newData.genObjects();
	}

}
