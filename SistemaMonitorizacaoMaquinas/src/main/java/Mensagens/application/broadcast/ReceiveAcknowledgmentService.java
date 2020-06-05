package Mensagens.application.broadcast;

import LinhaProducao.domain.LinhaProducao;
import LinhaProducao.domain.ChaoDeFabrica;
import LinhaProducao.domain.Maquina.IdMaquina;
import Mensagens.domain.BroadcastAcknowledge;

import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * Está encarregue de receber a mensagem de resposta e de atualizar os estados das máquinas de forma correta
 */
public class ReceiveAcknowledgmentService implements Runnable {
	private final BroadcastAcknowledge mensagem;
	public ReceiveAcknowledgmentService(DatagramPacket packet) {
		this.mensagem = new BroadcastAcknowledge(packet);
	}

	@Override
	public void run() {
		int idLinhaProducao = mensagem.getIdLinhaProducao();
		IdMaquina idMaquina = mensagem.getIdMaquina();
		InetAddress ipMaquina = mensagem.getIp();
		ChaoDeFabrica chaoDeFabrica = ChaoDeFabrica.getInstance();
		if (chaoDeFabrica.verificarQueListaProducaoExiste(idLinhaProducao)) {
			LinhaProducao linhaProd = chaoDeFabrica.procurarPorLinhaProducao(idLinhaProducao);
			if (linhaProd.maquinaExiste(mensagem.getIdMaquina())) {
				linhaProd.atualizarMaquina(idMaquina, mensagem.getCodigo());
				linhaProd.adicionarMaquina(idMaquina, mensagem.getCodigo(), ipMaquina);
			}
		} else { //Caso a linha de produção não exista, cria-se uma nova linha de produção
			LinhaProducao linhaProd = chaoDeFabrica.adicionarLinhaProducao(idLinhaProducao);
			linhaProd.adicionarMaquina(idMaquina, mensagem.getCodigo(), ipMaquina);
		}
	}
}
