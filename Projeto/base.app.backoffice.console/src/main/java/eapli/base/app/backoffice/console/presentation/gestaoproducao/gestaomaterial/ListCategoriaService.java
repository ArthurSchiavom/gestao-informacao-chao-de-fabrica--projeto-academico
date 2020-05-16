package eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaomaterial;

import eapli.base.gestaoproducao.gestaomaterial.domain.Categoria;
import eapli.base.gestaoproducao.gestaomaterial.repository.CategoriaRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class ListCategoriaService {
    private final AuthorizationService authz= AuthzRegistry.authorizationService();
    private final CategoriaRepository categoriaRepository= PersistenceContext.repositories().categoria();


    public Iterable<Categoria> todasCategorias(){
        //authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.GESTOR_DE_PRODUCAO);
        return this.categoriaRepository.findAll();
    }

}
