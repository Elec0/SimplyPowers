package elec0.simplypowers.powers;

import elec0.simplypowers.capabilities.PowerData;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

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
	
	public void activate();
	
	// Getters/Setters
	public void setID(int ID);
	public void setLevel(int level);
	public void setActive(int active);

	public int getID();
	public int getLevel();
	public int getActive();
}
