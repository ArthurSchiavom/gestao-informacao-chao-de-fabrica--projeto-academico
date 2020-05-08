package eapli.base.produto.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.produto.domain.Produto;
import eapli.base.produto.persistence.ProdutoRepository;

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
