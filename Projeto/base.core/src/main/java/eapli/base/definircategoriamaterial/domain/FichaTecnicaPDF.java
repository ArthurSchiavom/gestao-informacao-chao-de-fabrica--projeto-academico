package eapli.base.definircategoriamaterial.domain;


import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.io.File;
import java.util.Objects;


@Embeddable
public class FichaTecnicaPDF implements ValueObject, Comparable<FichaTecnicaPDF> {

    private static final long serialVersionUID = 1L;

    private File FICHA_TECNICA;
    private String path;

    protected FichaTecnicaPDF() {
    }

    /**
     * https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=29484
     * "É indicado pelo utilizador qual é o ficheiro (nome e path) e o sistema deve guardar o ficheiro de forma a, se for necessário, posteriormente indicar qual é e mostra-lo ao utilizador."
     *
     * @param path caminho do ficheiro
     * @param name nome do ficheiro
     */
    protected FichaTecnicaPDF(String path, String name) {
        this.path = path + '/' + name;
        FICHA_TECNICA = new File(this.path);
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

    @Override
    public int compareTo(FichaTecnicaPDF obj) {
        return this.FICHA_TECNICA.compareTo(obj.FICHA_TECNICA);
    }
}