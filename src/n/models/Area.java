package n.models;

/**
 * This interface is used to create the instances of the 
 * North, Central and South classes.
 */
public interface Area {
	public boolean getTravel();
	public String getTravelFlag();
	public void setTravel(boolean travel);
	public boolean isAdjacent(Area other);
}
