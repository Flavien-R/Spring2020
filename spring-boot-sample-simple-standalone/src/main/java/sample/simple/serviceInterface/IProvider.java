package sample.simple.serviceInterface;

public interface IProvider {

	public double getPrice(int article);
	
	public boolean order(int article, int quantite);
	
}
