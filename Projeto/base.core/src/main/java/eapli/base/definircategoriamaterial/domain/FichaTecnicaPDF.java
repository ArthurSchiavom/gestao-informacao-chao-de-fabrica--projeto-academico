package eapli.base.definircategoriamaterial.domain;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import eapli.framework.domain.model.ValueObject;

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
    public final Document fichaTecnica;
    @Transient
    public final String path;

    @Lob
    @Basic (fetch = FetchType.LAZY)
    private byte[]file;

    protected FichaTecnicaPDF() {
        fichaTecnica = null;
        path = null;
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
        this.fichaTecnica =new Document();
        criarFicheiro(this.fichaTecnica,this.path,conteudoFichaTecnica);
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
        return fichaTecnica.equals(that.fichaTecnica);
    }


    @Override
    public int hashCode() {
        return Objects.hash(fichaTecnica);
    }

    @Override
    public String toString() {
        return "FichaTecnicaPDF{" +
                "FICHA_TECNICA=" + fichaTecnica +
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