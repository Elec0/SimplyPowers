package elec0.simplypowers.commands;

import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import elec0.simplypowers.SimplyPowers;
import elec0.simplypowers.capabilities.IPowerData;
import elec0.simplypowers.capabilities.PowerDataProvider;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class SimplyPowersCommand extends CommandBase
{
	public SimplyPowersCommand()
	{
		aliases = Lists.newArrayList(SimplyPowers.MODID, "SIMPLYPOWERS", "SIMPLYPOWERS");
	}
	
	private final List<String> aliases;
	
	@Override
	@Nonnull
	public String getCommandName()
	{
		return "simplypowers";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/simplypowers [list, regen]";
	}
	@Override
    @Nonnull
    public List<String> getCommandAliases() 
	{
        return aliases;
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if(sender instanceof EntityPlayer)
		{				
			IPowerData powerData = ((EntityPlayer) sender).getCapability(PowerDataProvider.POWER_CAP, null);
			if(args[0].equalsIgnoreCase("list"))
			{
				sender.addChatMessage(new TextComponentString("Power debug: " + powerData.toString()));
			}
			else if(args[0].equalsIgnoreCase("regen"))
			{
				powerData.generatePowers();
				sender.addChatMessage(new TextComponentString("Power regenerated."));
				sender.addChatMessage(new TextComponentString("Power debug: " + powerData.toString()));
			}
		}
	}
	
	@Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) 
	{
        return true;
    }
}
