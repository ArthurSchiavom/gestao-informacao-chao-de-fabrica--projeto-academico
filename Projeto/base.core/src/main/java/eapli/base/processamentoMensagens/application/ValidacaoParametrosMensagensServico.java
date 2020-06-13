package eapli.base.processamentoMensagens.application;

import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.gestaomaquina.repository.MaquinaRepository;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.MateriaPrima;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.QuantidadeDeMateriaPrima;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.gestaoproducao.gestaoproduto.domain.CodigoUnico;
import eapli.base.gestaoproducao.gestaoproduto.domain.FichaDeProducao;
import eapli.base.gestaoproducao.gestaoproduto.domain.Produto;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.gestaoproducao.ordemProducao.domain.IdentificadorOrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.domain.QuantidadeAProduzir;
import eapli.base.gestaoproducao.ordemProducao.repository.OrdemProducaoRepository;
import eapli.base.indicarUsoDeMaquina.domain.UsoDeMaquina;
import eapli.base.infrastructure.persistence.PersistenceContext;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ValidacaoParametrosMensagensServico {
    private OrdemProducaoRepository ordemProducaoRepository;
    private ProdutoRepository produtoRepository;
    private MaquinaRepository maquinaRepository;
    private LinhaProducaoRepository linhaProducaoRepository;

    public ValidacaoParametrosMensagensServico() {
        this.produtoRepository=PersistenceContext.repositories().produto() ;
        this.ordemProducaoRepository= PersistenceContext.repositories().ordemProducao();
        this.maquinaRepository= PersistenceContext.repositories().maquinas();
        this.linhaProducaoRepository=PersistenceContext.repositories().linhasProducao();
    }

    public boolean validarData(Date date){
        if (date.compareTo(new Date())>0)
            return false;
        return true;
    }

    public boolean validarQuantidade(double quantidade){
        return quantidade>0;
    }

    public Produto getProdutoPorCodigoUnico(CodigoUnico codigoUnico){
        Optional<Produto> produto;
        produto=produtoRepository.produtoDeCodigoUnico(codigoUnico.codigoUnicoValor);
        if (!produto.isPresent())
            return null;
        return produto.get();
    }

    public OrdemProducao getOrdemDeProducaoPorIdentificador(IdentificadorOrdemProducao identificador){
        Optional<OrdemProducao> ordemProducao;
        ordemProducao=ordemProducaoRepository.findByIdentifier(identificador);
        if (!ordemProducao.isPresent())
            return null;
        return ordemProducao.get();
    }

    public MateriaPrima validarEObterMateriaPrima(String idMateriaPrima, IdentificadorOrdemProducao identificador){
        Produto produto;
        OrdemProducao ordemProducao=getOrdemDeProducaoPorIdentificador(identificador);
        if (ordemProducao!=null) {
            produto = getProdutoPorCodigoUnico(ordemProducao.produto);
            FichaDeProducao fichaDeProducao = produto.fichaDeProducao;
            MateriaPrima materiaPrima=fichaDeProducao.obterMateriaPrimaPorID(idMateriaPrima);
            return materiaPrima;
        }
        return null;
    }

    /**
     * Retorna uma maquina registada obtendo a por codigoInterno
     * @param codigoInternoMaquina
     * @return
     */
    public Maquina getMaquinaPorIdentificador(CodigoInternoMaquina codigoInternoMaquina){
        Optional<Maquina> maquina;
        maquina=maquinaRepository.findByIdentifier(codigoInternoMaquina);
        if (!maquina.isPresent())
            return null;
        return maquina.get();
    }


    public LinhaProducao getLinhaDeProducaoPorIdentificador(IdentificadorLinhaProducao identificadorLinhaProducao){
        Optional<LinhaProducao> linhaProducao;
        linhaProducao=linhaProducaoRepository.findByIdentifier(identificadorLinhaProducao);
        if(!linhaProducao.isPresent())
            return null;
        return linhaProducao.get();
    }


    public UsoDeMaquina verificarExistenciaDeUsoMaquina(List<UsoDeMaquina> lista, CodigoInternoMaquina codigoInternoMaquina){
        for (UsoDeMaquina usoDeMaquina:lista){
            if (usoDeMaquina.usoDeMaquinaID.codigoInternoMaquina.equals(codigoInternoMaquina)){
                return usoDeMaquina;
            }
        }
        return null;
    }






}