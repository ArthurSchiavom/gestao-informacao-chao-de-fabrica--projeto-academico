package eapli.base.gestaoproducao.gestaoproduto.application.consulta;

import eapli.base.gestaoproducao.gestaoproduto.application.dto.ProdutoDTO;
import eapli.base.gestaoproducao.gestaoproduto.domain.Produto;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
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
