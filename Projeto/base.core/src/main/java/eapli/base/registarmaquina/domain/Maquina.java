package eapli.base.registarmaquina.domain;

import eapli.base.gestaolinhasproducao.domain.LinhaProducao;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * "O código interno é aquele que é comumente usado para identificar uma máquina."
 *
 * https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=29197
 *---------------------------------------------------------------------------------------------------------------
 * O ficheiro de configuração vai ser parte de outro UC:
 *
 * "Na definição da máquina (US 3001) não devem considerar essa necessidade.
 *
 * Isso é tipicamente realizado num momento diferente e, será alvo de outra US futuramente."
 *
 * https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=29442
 *
 */
@Entity
public class Maquina implements AggregateRoot<CodigoInterno> {

        @Version
        private Long version;

        @EmbeddedId
        private NumeroSerie numeroSerie;
        private CodigoInterno codigoInterno;
        private OrdemLinhaProducao ordemLinhaProducao;
        private FicheiroConfiguracao ficheiroConfiguracao;
        private IdentificadorProtocoloComunicacao identificadorProtocoloComunicacao;
        public final Date dataInstalacao;
        private String descricao; // might change so it's not final
        private String marca; // might change so it's not final
        private String modelo; // might change so it's not final
        @ManyToOne
        private LinhaProducao linhaProducao;


        protected Maquina(){
                dataInstalacao = null;
        }

        public Maquina(NumeroSerie numeroSerie, CodigoInterno codigoInterno, OrdemLinhaProducao ordemLinhaProducao,
                       IdentificadorProtocoloComunicacao identificadorProtocoloComunicacao, String descricao, String marca,
                       String modelo, LinhaProducao linha) throws IllegalArgumentException{
                try {
                        this.numeroSerie = numeroSerie;
                        this.codigoInterno = codigoInterno;
                        this.ordemLinhaProducao = ordemLinhaProducao;
                        this.identificadorProtocoloComunicacao = identificadorProtocoloComunicacao;
                        this.descricao = descricao;
                        this.marca = marca;
                        this.modelo = modelo;
                        this.linhaProducao = linha;
                        this.dataInstalacao = Calendar.getInstance().getTime(); // get current time
                }catch (IllegalArgumentException ex){
                        throw new IllegalArgumentException("Argumentos incorretos");
                }
        }

        public static String identityAttributeName(){
                return"numeroSerie";
        }

        @Override
        public boolean equals(final Object o){
                return DomainEntities.areEqual(this,o);
        }

        @Override
        public int hashCode(){
                return DomainEntities.hashCode(this);
        }
        @Override
        public boolean sameAs(final Object other){
                return DomainEntities.areEqual(this,other);
        }

        @Override
        public CodigoInterno identity() {
                return this.codigoInterno;
        }
}