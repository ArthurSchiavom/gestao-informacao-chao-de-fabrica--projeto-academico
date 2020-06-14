package eapli.base.processamentoMensagens.application.processadormensagens;

import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;

import java.util.List;

public class BootstrapProcessamento {
    public static void boot() {
        LinhaProducaoRepository linhaProducaoRepository = PersistenceContext.repositories().linhasProducao();
        List<LinhaProducao> linhas = linhaProducaoRepository.findAllList();
        for (LinhaProducao linha : linhas) {
            new Thread(new ProcessadorLinhaProducao(linha)).start();
        }
    }
}
