package eapli.base.gestaoproducao.gestaomaquina.aplication.especificacao;

import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomaquina.aplication.dto.LinhaProducaoDTO;
import eapli.base.gestaoproducao.gestaomaquina.aplication.dto.LinhasProducaoTransformer;
import eapli.base.gestaoproducao.gestaomaquina.domain.*;
import eapli.base.gestaoproducao.gestaomaquina.repository.MaquinaRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;

import javax.persistence.RollbackException;
import java.util.List;

public class EspecificarMaquinaController {
    private final LinhaProducaoRepository repositoryLinhasProducao = PersistenceContext.repositories().linhasProducao();
    private final MaquinaRepository repositoryMaquinas = PersistenceContext.repositories().maquinas();
    private List<LinhaProducao> linhas = null;

    /**
     * @return uma lista de LinhasProduçao, usando o padrão Data Transfer Object
     */
    public List<LinhaProducaoDTO> getLinhasDTO() {
        linhas = (List) repositoryLinhasProducao.findAll();
        return LinhasProducaoTransformer.gerarLinhasDTO(linhas);
    }

    /**
     * Regista uma nov a máquina
     *
     * @param escolha                           1 linha de produçao
     * @param ordem                             ordem na linha de produçao
     * @param codigoInterno                     codigo interno da máquina
     * @param numero                            numeroSerie da máquina
     * @param descricao                         descricao da máquina
     * @param marca                             marca da máquina
     * @param modelo                            modelo da máquina
     * @param identificadorProtocoloComunicacao identificadorProtocoloComunicacao da máquina
     * @return máquina caso seja guardada no repositorio com sucesso
     */
    public Maquina registarMaquina(int escolha, int ordem, String codigoInterno, String numero, String descricao,
                                   String marca,
                                   String modelo, int identificadorProtocoloComunicacao,
                                   boolean existeMaquinaNaPosicao) throws IllegalArgumentException, RollbackException {

        if (linhas == null) {
            linhas = (List) repositoryLinhasProducao.findAll(); // for the bootstrap
        }
        try {

            LinhaProducao linha = linhas.get(escolha - 1);
            final OrdemLinhaProducao ordemLinhaProducao = new OrdemLinhaProducao(ordem);
            final CodigoInternoMaquina codInterno = new CodigoInternoMaquina(codigoInterno);
            final NumeroSerie numeroSerie = new NumeroSerie(numero);
            final IdentificadorProtocoloComunicacao identificadorProtocoloCom =
                    new IdentificadorProtocoloComunicacao(identificadorProtocoloComunicacao);

            if (existeMaquinaNaPosicao && !repositoryMaquinas.findByIdentifier(codInterno).isPresent()) {
                incrementarMaquinasComOrdemIgualOuSuperior(escolha, ordem);
            }
            return repositoryMaquinas.save(new Maquina(numeroSerie, codInterno, ordemLinhaProducao,
                    identificadorProtocoloCom, descricao, marca, modelo, linha.identifier));


        } catch (IllegalArgumentException | RollbackException ex) {
            throw ex;
        }
    }

    /**
     * Incrementaa a posição de todas as máquinas na linha onde a posição é igual ou maior a ordemNaLinha da maquina
     * atual a ser registada
     *
     * @param opcaoLinhaProducao 1 linha de produçao
     * @param ordemNaLinha       ordem nessa linha de produçao
     */
    private void incrementarMaquinasComOrdemIgualOuSuperior(int opcaoLinhaProducao, int ordemNaLinha) {
        LinhaProducao linhaProducao = linhas.get(opcaoLinhaProducao - 1);
        for (Maquina maq : repositoryMaquinas.findByLinhaProducao(linhaProducao.identifier)) {
            if (maq.getOrdemLinhaProducao().ordemLinhaProducao >= ordemNaLinha) {
                OrdemLinhaProducao novaOrdem =
                        new OrdemLinhaProducao(maq.getOrdemLinhaProducao().ordemLinhaProducao + 1);
                maq.setOrdemLinhaProducao(novaOrdem);
                repositoryMaquinas.save(maq);
            }
        }
    }

    /**
     * Verifica se uma máquina já existe na posição escolhida
     *
     * @param opcaoLinhaProducao 1 linha de produçao
     * @param ordemNaLinha       ordem nessa linha de produçao
     * @return true se já existir uma máquina nessa posiçao
     */
    public boolean existeMaquinaEmLinhaProducaoNaOrdem(int opcaoLinhaProducao, int ordemNaLinha) {
        LinhaProducao linhaProducao = linhas.get(opcaoLinhaProducao - 1);
        for (Maquina maq : repositoryMaquinas.findByLinhaProducao(linhaProducao.identifier)) {
            if (maq.getOrdemLinhaProducao().ordemLinhaProducao == ordemNaLinha) {
                return true;
            }
        }
        return false;
    }
}
