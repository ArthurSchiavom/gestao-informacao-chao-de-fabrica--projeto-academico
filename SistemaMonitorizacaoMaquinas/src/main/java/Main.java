import LinhaProducao.application.AtualizadorEstadoMaquinasService;
import Mensagens.application.broadcast.BroadcastHelloService;
import UI.MainMenu;

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
			broadcastHelloThread.join();
			atualizadorEstadoThread.join();
		} catch (InterruptedException e) {
			System.out.println("Erro a encerrar a hello thread");
		}
	}
}