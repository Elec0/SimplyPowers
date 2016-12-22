package elec0.simplypowers;

import elec0.simplypowers.items.ModItems;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = SimplyPowers.MODID, name = SimplyPowers.MODNAME, version =  SimplyPowers.MODVERSION, dependencies = "required-after:forge@[13.19.1.2197,)", useMetadata = true)
public class SimplyPowers 
{
	public static final String MODID = "simplypowers";
	public static final String MODNAME = "SimplyPowers";
	public static final String MODVERSION = "0.0.1";
	
	
	@SidedProxy
	public static CommonProxy proxy;
	
	@Mod.Instance
	public static SimplyPowers instance;
	
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit(event);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init(event);
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}
	
	
	public static class CommonProxy
	{
		public void preInit(FMLPreInitializationEvent e)
		{
			ModItems.init();
			
			OBJLoader.INSTANCE.addDomain(MODID);
		}
		
		 public void init(FMLInitializationEvent e) 
		 {	
			 MinecraftForge.EVENT_BUS.register(new EventHandlerCommon());
			 System.out.println("Event registered.");
			 
		 }

        public void postInit(FMLPostInitializationEvent e) 
        {

        }
	}
	
	public static class ClientProxy extends CommonProxy
	{
		@Override 
		public void preInit(FMLPreInitializationEvent e)
		{
			super.preInit(e);
			
			// Init models
			ModItems.initModels();
		}
		
		@Override
		public void init(FMLInitializationEvent e)
		{
			super.init(e);
			
			// Init keybinder here, later
			
		}
	}
	
	public static class ServerProxy extends CommonProxy
	{
		@Override
		public void init(FMLInitializationEvent e)
		{
			
		}
	}

}
