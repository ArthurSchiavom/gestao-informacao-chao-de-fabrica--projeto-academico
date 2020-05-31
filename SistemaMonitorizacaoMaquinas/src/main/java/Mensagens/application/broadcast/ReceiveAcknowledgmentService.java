package Mensagens.application.broadcast;

import LinhaProducao.domain.LinhaProducao;
import LinhaProducao.domain.ChaoDeFabrica;
import LinhaProducao.domain.Maquina.IdMaquina;
import Mensagens.domain.BroadcastAcknowledge;

import java.net.DatagramPacket;

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
		//Vai buscar todos os dados necessários á mensagem
		int idLinhaProducao = mensagem.getIdLinhaProducao();
		IdMaquina idMaquina = mensagem.getIdMaquina();
		ChaoDeFabrica chaoDeFabrica = ChaoDeFabrica.getInstance();
		//Primeiro verifica se a lista de produção existe
		if (chaoDeFabrica.verificarQueListaProducaoExiste(idLinhaProducao)) {
			LinhaProducao linhaProd = chaoDeFabrica.procurarPorLinhaProducao(idLinhaProducao);
			//Depois temos de ver se a máquina existe
			if (linhaProd.maquinaExiste(mensagem.getIdMaquina())) {
				//Dizemos á linha de produção para atualizar o estado da máquina
				linhaProd.atualizarMaquina(idMaquina, mensagem.getCodigo());
			} else { //Caso a máquina não exista adiciona-se á linha de produção
				linhaProd.adicionarMaquina(idMaquina, mensagem.getCodigo());
			}
		} else { //Caso a linha de produção não exista, cria-se uma nova linha de produção
			LinhaProducao linhaProd = chaoDeFabrica.adicionarLinhaProducao(idLinhaProducao);
			//Depois disso cria-se a máquina e guarda-se na lista de produção
			linhaProd.adicionarMaquina(idMaquina, mensagem.getCodigo());
		}
	}
}
