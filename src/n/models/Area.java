package n.models;

public interface Area {
	public boolean getTravel();
	public String getTravelFlag();
	public void setTravel(boolean travel);
	public boolean isAdjacent(Area other);
}
