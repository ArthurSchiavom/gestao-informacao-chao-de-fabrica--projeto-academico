package eapli.base.registarmaquina.aplication;

import eapli.base.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.registarmaquina.domain.*;
import eapli.base.registarmaquina.repository.MaquinaRepository;
import javax.persistence.RollbackException;
import java.util.List;

public class RegistarMaquinaController {
    private final LinhaProducaoRepository repositoryLinhasProducao = PersistenceContext.repositories().linhasProducao();
    private final MaquinaRepository repositoryMaquinas = PersistenceContext.repositories().maquinas();
    private List<LinhaProducao> linhas = null;

    /**
     *
     * @return uma lista de LinhasProduçao, usando o padrão Data Transfer Object
     */
    public List<LinhaProducaoDTO> getLinhasDTO(){
        linhas = (List) repositoryLinhasProducao.findAll();
        return LinhasProducaoTransformer.gerarLinhasDTO(linhas);
    }

    /**
     * Regista uma nov a máquina
     * @param escolha 1 linha de produçao
     * @param ordem ordem na linha de produçao
     * @param codigoInterno codigo interno da máquina
     * @param numero numeroSerie da máquina
     * @param descricao descricao da máquina
     * @param marca marca da máquina
     * @param modelo modelo da máquina
     * @param identificadorProtocoloComunicacao identificadorProtocoloComunicacao da máquina
     * @return máquina caso seja guardada no repositorio com sucesso
     */
    public Maquina registarMaquina(int escolha, int ordem, String codigoInterno, String numero, String descricao, String marca, String modelo,String identificadorProtocoloComunicacao) throws IllegalArgumentException,RollbackException{
        if(linhas == null){
            linhas = (List) repositoryLinhasProducao.findAll(); // for the bootstrap
        }
        try {
            LinhaProducao linha = linhas.get(escolha-1);
            final OrdemLinhaProducao ordemLinhaProducao = new OrdemLinhaProducao(ordem);
            System.out.println("ordem");
            final CodigoInterno codInterno = new CodigoInterno(codigoInterno);
            System.out.println("codigo");
            final NumeroSerie numeroSerie = new NumeroSerie(numero);
            System.out.println("numero");
            final IdentificadorProtocoloComunicacao identificadorProtocoloCom = new IdentificadorProtocoloComunicacao(identificadorProtocoloComunicacao);
            System.out.println("protocolo");
            return repositoryMaquinas.save(new Maquina(numeroSerie,codInterno,ordemLinhaProducao,identificadorProtocoloCom,descricao,marca,modelo,linha));

        } catch (IllegalArgumentException| RollbackException ex) {
            throw ex;
        }
    }
}
