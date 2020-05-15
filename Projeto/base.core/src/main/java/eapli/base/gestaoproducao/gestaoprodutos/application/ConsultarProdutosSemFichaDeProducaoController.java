package eapli.base.gestaoproducao.gestaoprodutos.application;

import eapli.base.gestaoproducao.gestaoprodutos.domain.Produto;
import eapli.base.gestaoproducao.gestaoprodutos.persistence.ProdutoRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

public class ConsultarProdutosSemFichaDeProducaoController {
    public List<ProdutoDTO> iniciar() {
        ProdutoRepository repo = PersistenceContext.repositories().produto();
        List<Produto> resultado = repo.produtosSemFichaDeProducao();
        List<ProdutoDTO> resultadoDTO = new ArrayList<>();
        resultado.forEach(p -> resultadoDTO.add(p.toDTO()));
        return resultadoDTO;
    }
}
