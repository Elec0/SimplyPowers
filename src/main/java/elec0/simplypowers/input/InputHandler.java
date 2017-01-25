package elec0.simplypowers.input;

import elec0.simplypowers.network.PacketHandler;
import elec0.simplypowers.network.PacketSendKey;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class InputHandler 
{
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event)
	{
		if(KeyBindings.powerKey1.isPressed())
		{
			PacketHandler.INSTANCE.sendToServer(new PacketSendKey(1));
		}
		if(KeyBindings.powerKey2.isPressed())
		{
			PacketHandler.INSTANCE.sendToServer(new PacketSendKey(2));
		}
	}
}
