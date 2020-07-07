package eapli.base.gestaoproducao.ordemProducao.application;

import eapli.base.gestaoproducao.ordemProducao.domain.Estado;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.repository.OrdemProducaoRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Controller para consultar ordens de produçao
 */
public class ConsultarOrdensProducaoController {
    private final OrdemProducaoRepository repository = PersistenceContext.repositories().ordemProducao();


    /**
     * Consulta as ordens pela encomenda ID
     */
    public List<OrdemProducaoDTO> getOrdemProducaoPorEncomenda(String idEncomenda) {
        try {
            List<OrdemProducao> ordem = (List<OrdemProducao>) repository.findOrdemProducaoByEncomenda(idEncomenda);

            if (ordem != null) {
                return OrdemProducao.gerarOrdensProducaoDTO(ordem);
            }
        } catch (NoResultException ex) {
        }
        return null;
    }


    /**
     * Consulta as ordens pelo seu estado
     */
    public List<OrdemProducaoDTO> getOrdensProducaoPorEstado(Estado estado) throws IllegalArgumentException {


        List<OrdemProducao> ordens = (List) repository.findOrdemProducaoByEstado(estado);
        return OrdemProducao.gerarOrdensProducaoDTO(ordens);
    }

}
