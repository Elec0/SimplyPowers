package elec0.simplypowers.powers;

import elec0.simplypowers.capabilities.PowerData;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;

/***
 * Interface for all powers.
 * Contains methods to be called on the stored <code>power</code> variable in <code>PowerData</code> for elegance.
 * @author Elec0
 */
public interface IPower 
{
	// Event methods
	public void entityJump(LivingJumpEvent event);
	
	
	// Getters/Setters
	public void setID(int ID);
	public void setLevel(int level);

	public int getID();
	public int getLevel();
}
