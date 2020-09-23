package sample.simple.serviceInterface;

public interface ILane extends IStore {

	public void addItemToCart(int article, int quantite);
	
	public boolean pay(int clientAccountNumber);
	
}
