package eapli.base.gestaoproducao.gestaomaquina.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

/**
 * Não está completo, esta Classe vai ser usada noutro UC
 * é para completar quando for precisso ou houver tempo
 */
@Embeddable
public class FicheiroConfiguracao implements ValueObject, Comparable<FicheiroConfiguracao> {

    private static final long serialVersionUID = 1L;

    @XmlValue
    public final String descricao;

    @XmlTransient
    @Lob
    public File file;

    protected FicheiroConfiguracao(){
        this.descricao=null;
        this.file=null;
    }

    public FicheiroConfiguracao(String descricao, String pathFicheiro) throws IOException {
        if ((descricao.trim().length()==0 || pathFicheiro.trim().length()==0)){
            throw new IllegalArgumentException("Campo nao pode ser vazio!");
        }
        this.descricao = descricao;
    }

    public File carregarFicheiro(String pathFicheiro) throws FileNotFoundException {
        File file=new File(pathFicheiro);
        if (file.exists()) {
           this.file=file;
           return file;
        }else{
            throw new FileNotFoundException("Ficheiro nao encontrado!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FicheiroConfiguracao that = (FicheiroConfiguracao) o;
        return Objects.equals(descricao, that.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descricao);
    }

    @Override
    public String toString() {
        return "FicheiroConfiguracao{" +
                "descricao='" + descricao + '\'' +
                '}';
    }

    @Override
    public int compareTo(FicheiroConfiguracao obj) {
        return this.descricao.compareTo(obj.descricao);
    }
}