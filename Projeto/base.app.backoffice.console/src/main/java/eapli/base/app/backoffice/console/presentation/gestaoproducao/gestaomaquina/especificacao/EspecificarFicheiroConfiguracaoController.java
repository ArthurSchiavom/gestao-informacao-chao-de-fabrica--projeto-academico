package eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaomaquina.especificacao;

import eapli.base.gestaoproducao.gestaomaquina.aplication.dto.MaquinaDTO;
import eapli.base.gestaoproducao.gestaomaquina.domain.FicheiroConfiguracao;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.gestaomaquina.repository.MaquinaRepository;
import eapli.base.infrastructure.application.DTOUtils;
import eapli.base.infrastructure.application.EntityUtils;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.domain.repositories.TransactionalContext;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EspecificarFicheiroConfiguracaoController {
    private final TransactionalContext transactionalContext;
    private final MaquinaRepository maquinaRepository;
    private final List<MaquinaDTO> maquinasDTO;
    private final List<MaquinaDTO> maquinasSemFicheiroDeConfiguracaoDTO;
    private final Map<String, Maquina> codigoInterno_MaquinaSemFicheiroDeConfiguracao;

    private  Maquina maquinaAlvo;

    public EspecificarFicheiroConfiguracaoController() {
        transactionalContext = PersistenceContext.repositories().newTransactionalContext();
        maquinaRepository = PersistenceContext.repositories().maquinas();
        List<Maquina> maquinas = maquinaRepository.findAllList();
        List<Maquina> maquinasSemFichaDeConfiguracao = maquinaRepository.maquinasSemFicheiroDeConfiguracao();
        maquinasDTO = Collections.unmodifiableList(DTOUtils.toDTOList(maquinas));
        maquinasSemFicheiroDeConfiguracaoDTO = Collections.unmodifiableList(DTOUtils.toDTOList(maquinasSemFichaDeConfiguracao));
        codigoInterno_MaquinaSemFicheiroDeConfiguracao = Collections.unmodifiableMap(EntityUtils.mapIdStringToEntity(maquinasSemFichaDeConfiguracao));
        maquinaAlvo=null;
    }

    /**
     * Retorna  lista de todas as  maquinas
     * @return Lista do tipo MaquinaDTO
     */
    public List<MaquinaDTO> maquinas() {
        return maquinasDTO;
    }

    /**
     * Retorna uma lista de maquinas sem ficheiro de configuracao
     * @return Lista do tipo MaquinaDTO
     */
    public List<MaquinaDTO> maquinasSemFicheiroDeConfiguracao() {
        return maquinasSemFicheiroDeConfiguracaoDTO;
    }

    /**
     * Seleciona uma maquina apartir de o codigo unico fornecido
     * @param codigoUnico Codigo unico da maquina
     * @return verdadeiro ou falsp
     */
     public boolean selecionarMaquina(String codigoUnico) {
         maquinaAlvo = codigoInterno_MaquinaSemFicheiroDeConfiguracao.get(codigoUnico);
         return maquinaAlvo != null;
     }

    /**
     * Associar o ficheiro de configuracao a maquina selecionada
     * @param descricao Conteudo do ficheiro
     * @param nomeFicheiro Nome do ficheiro
     * @throws IOException Exceção
     */
    public void registar(String descricao,String nomeFicheiro) throws IOException ,IllegalArgumentException{
        transactionalContext.beginTransaction();
        FicheiroConfiguracao novoFicheiroConfiguracao = new FicheiroConfiguracao(descricao,nomeFicheiro);
        maquinaAlvo.setFicheiroConfiguracao(novoFicheiroConfiguracao);
        maquinaAlvo = maquinaRepository.save(maquinaAlvo);
        transactionalContext.commit();
    }
}

