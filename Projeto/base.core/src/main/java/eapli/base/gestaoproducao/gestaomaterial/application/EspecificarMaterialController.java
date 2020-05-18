package eapli.base.gestaoproducao.gestaomaterial.application;

import eapli.base.gestaoproducao.gestaomaterial.domain.Categoria;
import eapli.base.gestaoproducao.gestaomaterial.domain.CodigoInterno;
import eapli.base.gestaoproducao.gestaomaterial.domain.FichaTecnicaPDF;
import eapli.base.gestaoproducao.gestaomaterial.domain.Material;
import eapli.base.gestaoproducao.gestaomaterial.repository.MaterialRepository;
import eapli.base.gestaoproducao.medicao.UnidadeDeMedida;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.persistence.PersistenceContext;

import java.io.IOException;

public class EspecificarMaterialController {
    MaterialRepository materialRepository=PersistenceContext.repositories().material();

    /**
     *
     * @param descricaoMaterial Descricao do material
     * @param nomeMaterial Nome do material
     * @param conteudoFichaTecnica Conteudo da ficha tecnicaPUBLIC
     * @param codigoInterno Identificador com que queremos identificar o material
     * @param categoria Categoria onde se encaixa o material
     * @param unidadeDeMedida  Unidade de medida usada para medir o material
     *
     * @Return Vai retornar um objecto do tipo Material e vai registar na base de dados
     */
    public Material registarMaterial(String unidadeDeMedida, String descricaoMaterial, String nomeMaterial,String path ,String conteudoFichaTecnica, String codigoInterno, Categoria categoria) throws IOException, IllegalDomainValueException {
        final CodigoInterno codigoInt= new CodigoInterno(codigoInterno);
        final Categoria catg = categoria;
        final UnidadeDeMedida uDeMedida = UnidadeDeMedida.actualValueOf(unidadeDeMedida);
        final FichaTecnicaPDF fichaTecnicaPDF=new FichaTecnicaPDF(path.trim(),nomeMaterial,conteudoFichaTecnica);
        final Material material=new Material(descricaoMaterial, codigoInt, categoria,uDeMedida,fichaTecnicaPDF);
        return  this.materialRepository.save(material);
    }

    /**
     * Remove um Material da base de dados
     * @param codigoInterno Codigo interno em String
     * @return
     */
    public boolean removerMaterialPorCodigoInterno(String codigoInterno){
        Material antigo = obterMaterialPorCodigoInterno(codigoInterno);
        materialRepository.remove(antigo);
        return true;
    }

    /**
     * Atraves do codigo interno (identificador) consegue obter o Material associado
     * @param codigoInterno Codigo interno em String
     * @return Retorna um Material
     */
    public Material obterMaterialPorCodigoInterno(String codigoInterno) {
        return materialRepository.obterMaterialPorCodigoInterno(codigoInterno).get();
    }
}

