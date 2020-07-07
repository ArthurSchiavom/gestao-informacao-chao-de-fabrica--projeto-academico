package eapli.base.gestaoproducao.gestaomaquina.aplication.dto;

public class MaquinaDTO {
    public final String numeroSerie;
    public final String codigoInterno;
    public final String ordemLinhaProducao;
    public final String identificadorProtocoloComunicacao;
    public final String marca;
    public final String modelo;
    public final String descricaoMaquina;
    public final String identificadorLinhaProducao;
    //Adicionar data


    public MaquinaDTO(String numeroSerie, String codigoInterno, String ordemLinhaProducao, String identificadorProtocoloComunicacao, String marca, String modelo, String descricaoMaquina,String identificadorLinhaProducao) {
        this.numeroSerie = numeroSerie;
        this.codigoInterno = codigoInterno;
        this.ordemLinhaProducao = ordemLinhaProducao;
        this.identificadorProtocoloComunicacao =identificadorProtocoloComunicacao;
        this.marca = marca;
        this.modelo = modelo;
        this.descricaoMaquina = descricaoMaquina;
        this.identificadorLinhaProducao=identificadorLinhaProducao;
    }
}
