package sample.simple.serviceInterface;

public interface IFastLane extends IStore {
	
	public boolean oneShotOrder(int article, int quantite, int clientAccountNumber);
	
}
