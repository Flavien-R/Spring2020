package sample.simple.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import sample.simple.serviceInterface.IBank;
import sample.simple.serviceInterface.IFastLane;
import sample.simple.serviceInterface.IJustHaveALook;
import sample.simple.serviceInterface.ILane;
import sample.simple.serviceInterface.IProvider;
import sample.simple.serviceInterface.IStore;

@Component
public class StoreService implements IStore, IFastLane, IJustHaveALook, ILane {
	
	private Map<Integer, Double> articlePrice = new HashMap<>();
	private Map<Integer, Integer> articleQuantity = new HashMap<>();
	private Map<Integer, Integer> articleOrdered = new HashMap<>();
	
	private IBank bank;
	private IProvider provider;
	
	@Value("${storeAccountNumber:0}")
	private int storeAccountNumber;
	
	public StoreService(IBank bank, IProvider provider) {
		this.bank = bank;
		this.provider = provider;
		System.out.println("numéro de compte store : " + storeAccountNumber);

	}

	@Override
	public void addItemToCart(int article, int quantity) {
		if(this.isAvailable(article, quantity)) {
			System.out.println("Articles Available");
			this.articleOrdered.put(article, quantity);
		} else {
			System.out.println("Articles non Available");
			this.provider.order(article, quantity);
			System.out.println("Articles Ordered");
			this.addArticleQuantity(article, quantity);
			this.addArticlePrice(article, this.provider.getPrice(article));
			this.articleOrdered.put(article, quantity);
		}
	}

	@Override
	public boolean pay(int clientAccountNumber) {
		
		double totalPrice = 0;
		for(int article : this.articleOrdered.keySet()) {
			totalPrice += this.articlePrice.get(article) * this.articleOrdered.get(article);
		}
		
		System.out.println("Calcul du prix : " + totalPrice);

		
		if(this.bank.transfert(clientAccountNumber, this.storeAccountNumber, totalPrice)) {
			int newQuantity;
			for(int article : this.articleOrdered.keySet()) {
				newQuantity = this.articleQuantity.get(article) - this.articleOrdered.get(article);
				this.articleQuantity.replace(article, newQuantity);
			}
			this.articleOrdered = new HashMap<Integer, Integer>();
			System.out.println("Transfert effecté, panier réinitialisé");
			return true;
		} else {
			System.out.println("Transfert refusé, panier réinitialisé");
			this.articleOrdered = new HashMap<Integer, Integer>();
			return false;
		}
	}

	@Override
	public double getPrice(int article) {
		return this.articlePrice.get(article);
	}

	@Override
	public boolean isAvailable(int article, int quantity) {
		return this.articleQuantity.get(article) != null && this.articleQuantity.get(article) >= quantity;
	}

	@Override
	public boolean oneShotOrder(int article, int quantity, int clientAccountNumber) {
		System.out.println("oneShotOrder commence");
		this.addItemToCart(article, quantity);
		this.pay(clientAccountNumber);
		System.out.println("oneShotOrder passé");
		return true;
	}
	
	private void addArticleQuantity(int article, int quantity) {
		if(this.articleQuantity.containsKey(article)) {
			int newQuantity = this.articleQuantity.get(article) + quantity;
			this.articleQuantity.replace(article, newQuantity);
		} else {
			this.articleQuantity.put(article, quantity);
		}
	}
	
	private void addArticlePrice(int article, double price) {
		if(!this.articlePrice.containsKey(article)) {
			double newPrice = price + price*20/100;
			this.articlePrice.put(article, newPrice);
		}
	}
	
}
