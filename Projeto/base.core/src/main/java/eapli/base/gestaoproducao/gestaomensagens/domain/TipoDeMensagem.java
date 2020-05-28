package eapli.base.gestaoproducao.gestaomensagens.domain;

import javax.persistence.DiscriminatorColumn;

@DiscriminatorColumn(name = "tipo")
public enum TipoDeMensagem {
    INICIO_DE_ATIVIDADE(Values.INICIO_DE_ATIVIDADE), FIM_DE_ATIVIDADE(Values.FIM_DE_ATIVIDADE),
    PARAGEM_FORCADA(Values.PARAGEM_FORCADA), RETOMA_ATIVIDADE(Values.RETOMA_ATIVIDADE), CONSUMO(Values.CONSUMO),
    PRODUCAO(Values.PRODUCAO),ENTREGA_DE_PRODUCAO(Values.ENTREGA_DE_PRODUCAO),ESTORNO(Values.ESTORNO);

    private final String value;

    private TipoDeMensagem(String value) {
        this.value = value;
    }

    public static class Values {
        public static final String INICIO_DE_ATIVIDADE = "INICIO_ATIVIDADE";
        public static final String FIM_DE_ATIVIDADE = "FIM_ATIVIDADE";
        public static final String PARAGEM_FORCADA = "PARAGEM_FORCADA";
        public static final String RETOMA_ATIVIDADE = "RETOMA_ATIVIDADE";
        public static final String CONSUMO = "CONSUMO";
        public static final String PRODUCAO = "PRODUCAO";
        public static final String ENTREGA_DE_PRODUCAO = "ENTREGA_DE_PRODUCAO";
        public static final String ESTORNO = "ESTORNO";
    }
}
