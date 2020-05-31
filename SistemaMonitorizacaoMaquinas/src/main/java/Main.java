import LinhaProducao.application.AtualizadorEstadoMaquinasService;
import Mensagens.application.broadcast.BroadcastHelloService;

class Main {
	public static void main(String[] args) {
		BroadcastHelloService helloService = new BroadcastHelloService();
		Thread broadcastHelloThread = new Thread(helloService);
		AtualizadorEstadoMaquinasService atualizadorEstadoService = new AtualizadorEstadoMaquinasService();
		Thread atualizadorEstadoThread = new Thread(atualizadorEstadoService);
		broadcastHelloThread.start();
		atualizadorEstadoThread.start();
		System.out.println("Pressione CTRL+C para encerrar o SMM");
		try {
			broadcastHelloThread.join();
			atualizadorEstadoThread.join();
		} catch (InterruptedException e) {
			System.out.println("Erro a encerrar a hello thread");
		}
	}
}