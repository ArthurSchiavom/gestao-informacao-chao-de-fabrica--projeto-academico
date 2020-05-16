package eapli.base.gestaomateriasprimas.application;

import eapli.base.definircategoriamaterial.domain.Categoria;
import eapli.base.definircategoriamaterial.domain.CodigoInterno;
import eapli.base.definircategoriamaterial.domain.FichaTecnicaPDF;
import eapli.base.definircategoriamaterial.domain.Material;
import eapli.base.gestaomateriasprimas.repository.MaterialRepository;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.materiaprima.domain.UnidadeDeMedida;
import eapli.base.produto.domain.Produto;

import javax.activation.DataHandler;
import java.io.IOException;
import java.util.Optional;

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

    public boolean removerMaterialPorCodigoInterno(String codigoInterno){
            Material antigo = obterMaterialPorCodigoInterno(codigoInterno);
            materialRepository.remove(antigo);
            return true;
    }

    public Material obterMaterialPorCodigoInterno(String codigoInterno){
        return materialRepository.obterMaterialPorCodigoInterno(codigoInterno).get();
    }

}
