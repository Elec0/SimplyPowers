package elec0.simplypowers.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class PowerDataStorage implements IStorage<IPowerData>
{

	@Override
	public NBTBase writeNBT(Capability<IPowerData> capability, IPowerData instance, EnumFacing side) 
	{
		return new NBTTagInt(instance.getPowerLevel());
	}

	@Override
	public void readNBT(Capability<IPowerData> capability, IPowerData instance, EnumFacing side, NBTBase nbt) 
	{
		instance.setPowerLevel(((NBTPrimitive) nbt).getInt());
	}
	
}
