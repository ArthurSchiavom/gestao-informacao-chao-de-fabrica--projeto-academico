import ChaoDeFabrica.application.AtualizadorEstadoMaquinasService;
import Mensagens.application.broadcast.BroadcastHelloService;
import UI.MainMenu;

import java.net.UnknownHostException;

class Main {
	public static void main(String[] args) {
		BroadcastHelloService helloService = new BroadcastHelloService();
		Thread broadcastHelloThread = new Thread(helloService);

		AtualizadorEstadoMaquinasService atualizadorEstadoService = new AtualizadorEstadoMaquinasService();
		Thread atualizadorEstadoThread = new Thread(atualizadorEstadoService);

		broadcastHelloThread.start();
		atualizadorEstadoThread.start();

		MainMenu mainMenu = new MainMenu();
		mainMenu.doShow();

		try {
			System.out.println("Encerramento programa inicializado");
			atualizadorEstadoService.stop();
			helloService.stop();
			broadcastHelloThread.interrupt();       //Caso esteja em sleep
			atualizadorEstadoThread.interrupt();    //Caso esteja em sleep
			broadcastHelloThread.join();
			atualizadorEstadoThread.join();
		} catch (InterruptedException e) {
			System.out.println("Erro a encerrar a Serviços");
		}
		System.out.println("Programa encerrado com sucesso");
	}
}