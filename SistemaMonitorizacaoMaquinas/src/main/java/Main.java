import Mensagens.application.broadcast.BroadcastHelloService;

class Main {
	public static void main(String[] args) {
		BroadcastHelloService helloService = new BroadcastHelloService();
		Thread broadcastHelloThread = new Thread(helloService);
		broadcastHelloThread.start();
		System.out.println("Pressione CTRL+C para encerrar o SMM");
		try {
			broadcastHelloThread.join();
		} catch (InterruptedException e) {
			System.out.println("Erro a encerrar a hello thread");
		}
	}
}