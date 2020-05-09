package eapli.base.gestaomateriasprimas.application;

import eapli.base.definircategoriamaterial.domain.Categoria;
import eapli.base.definircategoriamaterial.domain.CodigoInterno;
import eapli.base.definircategoriamaterial.domain.Material;
import eapli.base.gestaomateriasprimas.repository.MaterialRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.materiaprima.domain.UnidadeDeMedida;

public class AdicionarMaterialCatalogoController {
    MaterialRepository materialRepository;


    public AdicionarMaterialCatalogoController() {
        MaterialRepository materialRepository=PersistenceContext.repositories().material();
    }

    /**
     *
     * @param descricaoMaterial Descricao do material
     * @param nomeMaterial Nome do material
     * @param conteudoFichaTecnica Conteudo da ficha tecnica
     * @param codigoInterno Identificador com que queremos identificar o material
     * @param categoria Categoria onde se encaixa o material
     * @param unidadeDeMedida  Unidade de medida usada para medir o material
     *
     * @Return Vai retornar um objecto do tipo Material
     */
    public Material registarMaterial(String unidadeDeMedida, String descricaoMaterial, String nomeMaterial, String conteudoFichaTecnica, String codigoInterno, Categoria categoria){
        final CodigoInterno codigoInt= new CodigoInterno(codigoInterno);
        final String nomeFicheiro= nomeMaterial +"_fichaTecnica.pdf";
        final Categoria catg = categoria;
        final UnidadeDeMedida uDeMedida = new UnidadeDeMedida(unidadeDeMedida);
        final Material material=new Material(descricaoMaterial,nomeMaterial, conteudoFichaTecnica,codigoInt,catg,uDeMedida);
        return  this.materialRepository.save(material);
    }
}
