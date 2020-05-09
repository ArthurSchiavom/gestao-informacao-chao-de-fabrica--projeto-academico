package eapli.base.registarmaquina.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.io.IOException;
import java.util.Objects;

@Embeddable
public class NumeroSerie implements ValueObject, Comparable<NumeroSerie> {

    private static final long serialVersionUID = 1L;

    private String numeroSerie;
    private static int maxCharsNumeroSerie;
    private static int minCharsNumeroSerie;

    protected NumeroSerie() {
    }

    public NumeroSerie(String numeroSerie) throws IllegalArgumentException{
        if(numeroSerie.length()<=maxCharsNumeroSerie && numeroSerie.length()>=minCharsNumeroSerie) {
            this.numeroSerie = numeroSerie;
        }else{
            throw new IllegalArgumentException("Número de série não está nos parametros das regras definidas");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumeroSerie that = (NumeroSerie) o;
        return numeroSerie.equals(that.numeroSerie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroSerie);
    }

    @Override
    public String toString() {
        return "NumeroSerie{" +
                "numeroSerie='" + numeroSerie + '\'' +
                '}';
    }

    @Override
    public int compareTo(NumeroSerie obj) {
        return this.numeroSerie.compareTo(obj.numeroSerie);
    }

    /**
     * Define as regras para a length do número de série
     * @param max max chars número série
     * @param min min chars número série
     */
    public static void definirRegrasNumeroSerie(String max, String min) throws IOException{
        try{
            maxCharsNumeroSerie = Integer.valueOf(max);
            minCharsNumeroSerie = Integer.valueOf(min);
            if(maxCharsNumeroSerie < 1 || minCharsNumeroSerie < 1){
                throw new IOException("O número de chars do número de série da máquina não podem ser menores que 1");
            }
        } catch(Exception ex){
            throw new IOException("Regras do número de série da máquina não estão definidos");
        }
    }
}