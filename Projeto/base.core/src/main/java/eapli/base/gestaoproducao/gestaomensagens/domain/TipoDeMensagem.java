package eapli.base.gestaoproducao.gestaomensagens.domain;

import javax.persistence.DiscriminatorColumn;

@DiscriminatorColumn(name = "tipo")
public enum TipoDeMensagem {
    INICIO_DE_ATIVIDADE("Início de atividade"), FIM_DE_ATIVIDADE("Fim de atividade"),
    PARAGEM_FORCADA("Paragem forçada"), RETOMA_ATIVIDADE("Retoma de atividade"), CONSUMO("Consumo"),
    PRODUCAO("Produção"),ENTREGA_DE_PRODUCAO("Entrega de produção"),ESTORNO("Estorno");

    public final String nomeDisplay;

    private TipoDeMensagem(String nomeDisplay) {
        this.nomeDisplay = nomeDisplay;
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
