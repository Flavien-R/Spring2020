package sample.simple.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import sample.simple.serviceInterface.IBank;
import sample.simple.serviceInterface.IClient;
import sample.simple.serviceInterface.IProvider;
import sample.simple.serviceInterface.IRun;
import sample.simple.serviceInterface.IStore;

@Component
public class ClientService implements IRun, IClient {
			
	private StoreService store;
	
	@Value("${accountNumber:0}")
	private int accountNumber;

	@Override
	public void run() {
		System.out.println("numéro de compte client : " + this.accountNumber);
		
		ArrayList<Integer> accountNumbers = new ArrayList<Integer>();
		accountNumbers.add(accountNumber);
		accountNumbers.add(0);
		
		IProvider provider = new ProviderService();
		IBank bank = new BankService(accountNumbers);
		this.store = new StoreService(bank, provider);
		
		//Scénario 1
		this.store.oneShotOrder(1, 20, accountNumber);
		
		//Scénario 2
		this.store.addItemToCart(1, 5);
		this.store.addItemToCart(2, 5);
		this.store.addItemToCart(3, 5);
		this.store.pay(accountNumber);
		
	}
	
}
