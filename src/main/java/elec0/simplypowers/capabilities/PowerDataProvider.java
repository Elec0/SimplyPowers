package elec0.simplypowers.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PowerDataProvider implements ICapabilitySerializable<NBTBase>
{

	@CapabilityInject(IPowerData.class)
	
	public static final Capability<IPowerData> POWER_CAP = null;
	private IPowerData instance = POWER_CAP.getDefaultInstance();
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == POWER_CAP;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return capability == POWER_CAP ? POWER_CAP.<T> cast(this.instance) : null;
	}
	
	@Override
	public NBTBase serializeNBT()
	{
		return POWER_CAP.getStorage().writeNBT(POWER_CAP, this.instance, null);
	}
	
	@Override
	public void deserializeNBT(NBTBase nbt)
	{
	
		POWER_CAP.getStorage().readNBT(POWER_CAP, this.instance, null, nbt);
	}

}