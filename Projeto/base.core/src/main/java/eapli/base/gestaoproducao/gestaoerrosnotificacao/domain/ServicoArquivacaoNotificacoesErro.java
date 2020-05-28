package eapli.base.gestaoproducao.gestaoerrosnotificacao.domain;

import eapli.base.gestaoproducao.gestaoerrosnotificacao.repository.NotificacaoErroRepository;

public class ServicoArquivacaoNotificacoesErro {

	private final NotificacaoErroRepository repo;

	public ServicoArquivacaoNotificacoesErro(NotificacaoErroRepository repo) {
		this.repo = repo;
	}

	/**
	 * Arquiva uma notificação de erro e retorna uma referência para a notificação atualizada
	 *
	 * @param idNotifErro o id da notificação de erro que pretendemos atualizar
	 * @return Uma referência para uma notificação de erro arquivada
	 */
	public NotificacaoErro arquivar(Long idNotifErro) {
		if (idNotifErro == null) {
			throw new IllegalArgumentException("ID de Notificação de erro submetido era inválida");
		}
		NotificacaoErro notifErro = repo.ofIdentity(idNotifErro).orElse(null);
		if (notifErro == null) {
			throw new IllegalArgumentException("ID de Notificação de erro sumetido era inexistente");
		}
		if (!notifErro.arquivar()) {
			throw new IllegalArgumentException("Notificação de erro submetida já estava arquivada");
		}
		return repo.save(notifErro);
	}
}
