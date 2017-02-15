package elec0.simplypowers.capabilities;

import java.util.Arrays;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class PowerDataStorage implements IStorage<IPowerData>
{

	@Override
	public NBTBase writeNBT(Capability<IPowerData> capability, IPowerData instance, EnumFacing side) 
	{
		NBTTagCompound ret = new NBTTagCompound();
		instance.syncData(); // Update PowerData from Power before saving, or new changes will be lost.
		ret.setIntArray("types", instance.getTypes());
		ret.setIntArray("levels", instance.getLevels());
		ret.setIntArray("ids", instance.getPowerIDs());
		ret.setIntArray("actives", instance.getActives());
		ret.setIntArray("progression", instance.getProgression());
		ret.setIntArray("progressionlvl", instance.getProgressionLevel());
		
		// Multidimensional array, so save it as 'data0','data1', etc. Should only really be 0 and 1
		for(int i = 0; i < instance.getData().length; ++i)
		{
			ret.setIntArray("data" + i, instance.getData()[i]);
		}
		
		return ret;
	}

	@Override
	public void readNBT(Capability<IPowerData> capability, IPowerData instance, EnumFacing side, NBTBase nbt) 
	{
		if(nbt instanceof NBTTagCompound)
		{
			NBTTagCompound tag = (NBTTagCompound)nbt;
			
			instance.setTypes(tag.getIntArray("types"));
			instance.setLevels(tag.getIntArray("levels"));
			instance.setPowerIDs(tag.getIntArray("ids"));
			instance.setActives(tag.getIntArray("actives"));
			instance.setProgression(tag.getIntArray("progression"));
			instance.setProgressionLevel(tag.getIntArray("progressionlvl"));
			int size = 0;
			int[][] data = new int[2][];
			for(int i = 0; i < 2; ++i)
			{
				
				data[i] = tag.getIntArray("data" + i);					
			}
			instance.setData(data);
			
			instance.genObjects();
		}
	}
	
}
