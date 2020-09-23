package sample.simple.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import sample.simple.serviceInterface.IProvider;

@Component
public class ProviderService implements IProvider {
	
	private Map<Integer, Double> articlePrice = new HashMap<>();
	private Map<Integer, Integer> articleQuantity = new HashMap<>();
	
	public ProviderService() {
		for(int i = 0; i<4; i++) {
			articlePrice.put(i, (double) (i+10));
			articleQuantity.put(i, i*10);
		}
	}

	@Override
	public double getPrice(int article) {
		return articlePrice.get(article);
	}

	@Override
	public boolean order(int article, int quantity) {
		boolean ordered = false;
		if(articleQuantity.get(article) >= quantity) {
			int newQuantity = this.articleQuantity.get(article) - quantity;
			this.articleQuantity.replace(article, newQuantity);
			ordered = true;
		}
		return ordered;
	}
	
}
