package eapli.base.gestaoproducao.ordemProducao.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
public class TempoBrutoExecucao implements ValueObject {
    private Date tempoBrutoExecucao;


    public TempoBrutoExecucao() {
        tempoBrutoExecucao=null;
    }


}
