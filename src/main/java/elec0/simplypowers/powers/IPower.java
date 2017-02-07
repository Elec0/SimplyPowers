package elec0.simplypowers.powers;

import java.util.List;

import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

/***
 * Interface for all powers.
 * Contains methods to be called on the stored <code>power</code> variable in <code>PowerData</code> for elegance.
 * @author Elec0
 */
public interface IPower
{
	// Event methods
	public void entityJump(LivingJumpEvent event);
	public void playerLoggedIn(PlayerLoggedInEvent event);
	public void playerTick(PlayerTickEvent event);
	
	public int activate();
	public void checkProgression();
	
	// Getters/Setters
	public void setID(int ID);
	public void setLevel(int level);
	public void setActive(int active);
	public void setProgression(int progression);
	public void addProgression(int progression);
	public void setData(int[] data);
	public void setKeyStatus(List<Integer> keysPressed);

	public int getID();
	public int getLevel();
	public int getActive();
	public int getProgression();
	public int[] getData();
}
