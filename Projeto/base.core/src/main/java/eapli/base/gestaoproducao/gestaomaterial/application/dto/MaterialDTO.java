package eapli.base.gestaoproducao.gestaomaterial.application.dto;

import com.itextpdf.text.Document;

public class MaterialDTO {
    public final String codigointerno;
    public final String descricao;
    public final String unidadeDeMedida;
    public final Document fichaTecnicaPDF;
    public final String fichaTecnicaPDFPath;
    // TODO - adicionar categoria

    public MaterialDTO(String codigointerno, String descricao, String unidadeDeMedida, Document fichaTecnicaPDF, String fichaTecnicaPDFPath) {
        this.codigointerno = codigointerno;
        this.descricao = descricao;
        this.unidadeDeMedida = unidadeDeMedida;
        this.fichaTecnicaPDF = fichaTecnicaPDF;
        this.fichaTecnicaPDFPath = fichaTecnicaPDFPath;
    }
}
