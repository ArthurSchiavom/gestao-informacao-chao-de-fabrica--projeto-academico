package ChaoDeFabrica.application;

import ChaoDeFabrica.domain.ChaoDeFabrica;

import java.util.Date;

public class AtualizadorEstadoMaquinasService implements Runnable {
	private static final int TEMPO_VERIFICACAO = 45; //tempo para verificar, em segundos
	private static final int TEMPO_INATIVIDADE = 60; //tempo para ser considerado inativo, em segundos
	private boolean canKeepGoing = true;

	@Override
	public void run() {
		ChaoDeFabrica chaoDeFabrica = ChaoDeFabrica.getInstance();
		while (canKeepGoing) {
			chaoDeFabrica.verificarInatividade(new Date(), TEMPO_INATIVIDADE);
			try {
				Thread.sleep(TEMPO_VERIFICACAO * 1000);
			} catch (InterruptedException ignored) {
				//Só está aqui para o caso do programa ser encerrado enquanto a thread está a dormir
			}
		}
		System.out.println("Serviço de verificação de inatividade das máquinas encerrado");
	}

	public void stop() {
		if(!canKeepGoing) {
			throw new RuntimeException("Can't stop a already stopped thread");
		}
		canKeepGoing = false;
	}
}
