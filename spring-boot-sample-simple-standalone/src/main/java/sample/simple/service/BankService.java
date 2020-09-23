package sample.simple.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import sample.simple.serviceInterface.IBank;

@Component
public class BankService implements IBank {
	
	private Map<Integer, Double> accountMoney = new HashMap<>();
	
	public BankService(ArrayList<Integer> accountNumbers) {
		accountNumbers.forEach(accountNumber -> this.accountMoney.put(accountNumber, 500.00));
	}

	@Override
	public boolean transfert(int clientAccountNumber, int storeAccountNumber, double totalPrice) {
		boolean transfered = false;
		System.out.println("Début transfert, montant compte client : " + this.accountMoney.get(clientAccountNumber));
		System.out.println("Début transfert, montant compte store : " + this.accountMoney.get(storeAccountNumber));

		if(this.accountMoney.get(clientAccountNumber) >= totalPrice) {
			double newClientMoney = this.accountMoney.get(clientAccountNumber) - totalPrice;
			this.accountMoney.replace(clientAccountNumber, newClientMoney);
			double newStoreMoney = this.accountMoney.get(storeAccountNumber) + totalPrice;
			this.accountMoney.replace(storeAccountNumber, newStoreMoney);
			transfered = true;
		} 
		System.out.println("Fin transfert, montant compte client : " + this.accountMoney.get(clientAccountNumber));
		System.out.println("Fin transfert, montant compte store : " + this.accountMoney.get(storeAccountNumber));

		return transfered;
	}

}
