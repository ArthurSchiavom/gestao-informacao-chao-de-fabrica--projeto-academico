package eapli.base.definircategoriamaterial.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import eapli.framework.domain.model.ValueObject;
import org.apache.commons.io.IOUtils;

import javax.persistence.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


@Embeddable
public class FichaTecnicaPDF implements ValueObject, Comparable<FichaTecnicaPDF> {

    private static final long serialVersionUID = 1L;

    @Transient
    private Document FICHA_TECNICA;
    @Transient
    private String path;

    @Lob
    @Basic (fetch = FetchType.LAZY)
    private byte[]file;

    protected FichaTecnicaPDF() {
    }

    /**
     * https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=29484
     * "É indicado pelo utilizador qual é o ficheiro (nome e path) e o sistema deve guardar o ficheiro de forma a, se for necessário, posteriormente indicar qual é e mostra-lo ao utilizador."
     *
     * @param path caminho do ficheiro
     * @param name nome do ficheiro
     */
    public FichaTecnicaPDF(String path, String name,String conteudoFichaTecnica) throws IOException {
        this.path = path + '/' + name +".pdf";
        this.FICHA_TECNICA=new Document();
        criarFicheiro(this.FICHA_TECNICA,this.path,conteudoFichaTecnica);
        Path caminho=Paths.get(this.path);
        this.file=Files.readAllBytes(caminho);
    }

    private void criarFicheiro(Document document,String nameFile,String conteudoParaFichaTecnica) {
      try {
          PdfWriter.getInstance(document, new FileOutputStream(nameFile));
          document.open();
          document.add(new Paragraph(conteudoParaFichaTecnica));
          document.close();
      } catch (FileNotFoundException e) {
          System.out.println("Ficheiro nao encontrado");
      } catch (DocumentException e) {
          e.printStackTrace();
      }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FichaTecnicaPDF that = (FichaTecnicaPDF) o;
        return FICHA_TECNICA.equals(that.FICHA_TECNICA);
    }


    @Override
    public int hashCode() {
        return Objects.hash(FICHA_TECNICA);
    }

    @Override
    public String toString() {
        return "FichaTecnicaPDF{" +
                "FICHA_TECNICA=" + FICHA_TECNICA +
                '}';
    }
    public String path(){
        return path;
    }

    @Override
    public int compareTo(FichaTecnicaPDF obj) {
        return 0;
    }
}