package LinhaProducao.application;

import LinhaProducao.domain.ChaoDeFabrica;

import java.util.Date;

public class AtualizadorEstadoMaquinasService implements Runnable {
	private static final int TEMPO_VERIFICACAO = 45; //tempo para verificar, em segundos
	private static final int TEMPO_INATIVIDADE = 60; //tempo para ser considerado inativo, em segundos

	@Override
	public void run() {
		ChaoDeFabrica chaoDeFabrica = ChaoDeFabrica.getInstance();
		//noinspection InfiniteLoopStatement
		while(true) {
			chaoDeFabrica.verificarInatividade(new Date(), TEMPO_INATIVIDADE);
			try {
				Thread.sleep(45*1000);
			} catch (InterruptedException e) {
				System.out.println("Erro a esperar 45 segundos para verificar inatividade");
			}
		}
	}
}
