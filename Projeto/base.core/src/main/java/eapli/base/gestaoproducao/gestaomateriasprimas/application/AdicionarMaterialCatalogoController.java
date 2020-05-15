package eapli.base.gestaoproducao.gestaomateriasprimas.application;

import eapli.base.gestaoproducao.gestaomaterial.domain.Categoria;
import eapli.base.gestaoproducao.gestaomaterial.domain.CodigoInterno;
import eapli.base.gestaoproducao.gestaomaterial.domain.FichaTecnicaPDF;
import eapli.base.gestaoproducao.gestaomaterial.domain.Material;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.UnidadeDeMedida;
import eapli.base.gestaoproducao.gestaomateriasprimas.repository.MaterialRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;

import java.io.IOException;

public class AdicionarMaterialCatalogoController {
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
     * @Return Vai retornar um objecto do tipo Material
     */
    public Material registarMaterial(String unidadeDeMedida, String descricaoMaterial, String nomeMaterial,String path ,String conteudoFichaTecnica, String codigoInterno, Categoria categoria) throws IOException {
        final CodigoInterno codigoInt= new CodigoInterno(codigoInterno);
        final Categoria catg = categoria;
        final UnidadeDeMedida uDeMedida = new UnidadeDeMedida(unidadeDeMedida);
        final FichaTecnicaPDF fichaTecnicaPDF=new FichaTecnicaPDF(path,nomeMaterial,conteudoFichaTecnica);
        final Material material=new Material(descricaoMaterial, codigoInt, categoria,uDeMedida,fichaTecnicaPDF);
        return  this.materialRepository.save(material);
    }
}
