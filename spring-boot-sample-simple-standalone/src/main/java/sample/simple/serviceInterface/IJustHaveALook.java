package sample.simple.serviceInterface;

public interface IJustHaveALook extends IStore {

	public double getPrice(int article);

	public boolean isAvailable(int article, int quantite);
	
}
