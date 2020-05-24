package eapli.base.gestaoproducao.gestaomaterial.application;

import eapli.base.gestaoproducao.gestaomaterial.domain.Categoria;
import eapli.base.gestaoproducao.gestaomaterial.domain.CodigoAlfanumericoCategoria;
import eapli.base.gestaoproducao.gestaomaterial.repository.CategoriaRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;

public class EspecificarCategoriaMaterialController {
    //TODO add authz maybe
    private final CategoriaRepository repository = PersistenceContext.repositories().categoria();

    /**
     * Regista uma nova categoria de material
     * @param identifier o identificador com que queremos identificar a linha de produção
     * @return a linha de produção instanciada e guardada no repositório
     */
    public Categoria registarCategoriaMaterial(final String identifier, final String descricao) throws IllegalArgumentException{
        try {
            final CodigoAlfanumericoCategoria codigo = new CodigoAlfanumericoCategoria(identifier);
            final Categoria cat = new Categoria(codigo, descricao);
            return this.repository.save(cat);
        }catch(IllegalArgumentException ex){
            throw ex;
        }
    }
}
