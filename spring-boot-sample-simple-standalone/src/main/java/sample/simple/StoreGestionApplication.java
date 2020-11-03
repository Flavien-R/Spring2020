package sample.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sample.simple.service.ClientService;

@SpringBootApplication
public class StoreGestionApplication implements CommandLineRunner {
	
		@Autowired
		private ClientService clientService;
		
		public void run(String... args) {
			System.out.println(this.clientService.getAccNum());
			//this.clientService.run();
		}

		public static void main(String[] args) throws Exception {
			SpringApplication.run(StoreGestionApplication.class, args);
	}
}
