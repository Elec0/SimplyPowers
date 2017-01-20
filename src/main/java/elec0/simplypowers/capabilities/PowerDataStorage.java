package elec0.simplypowers.capabilities;

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
			
			instance.genObjects();
		}
	}
	
}
