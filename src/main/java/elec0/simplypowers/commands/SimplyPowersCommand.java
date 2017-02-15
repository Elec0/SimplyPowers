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
import net.minecraft.util.text.TextFormatting;

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
		return "/simplypowers [list, regen, setpower, setlevel, setprogression]";
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
			if(args.length == 0)
			{
				sender.addChatMessage(new TextComponentString(getCommandUsage(sender)));
				return;
			}
			
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
			else if(args[0].equalsIgnoreCase("setpower"))
			{
				if(args.length == 5)
				{
					int power = Integer.parseInt(args[1]);
					int type = Integer.parseInt(args[2]);
					int id = Integer.parseInt(args[3]);
					int level = Integer.parseInt(args[4]);
					
					powerData.syncData(); // So we don't lose anything in the other power
					int other = Math.abs(power-1);
					
					int[] types = powerData.getTypes();
					int[] powerIDs = powerData.getPowerIDs();
					int[] levels = powerData.getLevels();
					types[power] = type;
					//types[other] = powerData.getTypes()[other];
					powerIDs[power] = id;
					//powerIDs[other] = powerData.getPowerIDs()[other];
					levels[power] = level;
					//levels[other] = powerData.getLevels()[other];
					
					powerData.setTypes(types);
					powerData.setPowerIDs(powerIDs);
					powerData.setLevels(levels);
					
					powerData.genObjects();
					
					sender.addChatMessage(new TextComponentString(TextFormatting.GREEN + "Power set."));
				}
				else
				{
					sender.addChatMessage(new TextComponentString(TextFormatting.RED + "Incorrect number of args. set power[0/1] powerType[0-10], powerID[0-maxCat], powerLevel[20-100])"));
				}
			}
			else if(args[0].equalsIgnoreCase("setprogression"))
			{
				if(args.length == 4)
				{
					int power = Integer.parseInt(args[1]);
					int progression = Integer.parseInt(args[2]);
					int progressionLvl = Integer.parseInt(args[3]);
					
					powerData.syncData();

					int[] progs = powerData.getProgression();
					int[] progLvls = powerData.getProgressionLevel();
					progs[power] = progression;
					progLvls[power] = progressionLvl;
					
					powerData.setProgression(progs);
					powerData.setProgressionLevel(progLvls);
					
					powerData.genObjects();
					
					sender.addChatMessage(new TextComponentString(TextFormatting.GREEN + "Progression set."));
				}
				else
				{
					sender.addChatMessage(new TextComponentString(TextFormatting.RED + "Incorrect number of args. (power[0/1] progression[0-maxInt] progressionLvl[1-maxInt]"));
				}
			}
		}
	}
	
	@Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) 
	{
        return true;
    }
}
