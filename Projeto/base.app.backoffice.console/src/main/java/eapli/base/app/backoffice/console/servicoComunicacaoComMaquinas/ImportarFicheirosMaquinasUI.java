package eapli.base.app.backoffice.console.servicoComunicacaoComMaquinas;

import eapli.base.servicoComunicacaoComMaquinas.ImportarFicheirosMaquinasController;
import eapli.framework.presentation.console.AbstractUI;

import java.time.DateTimeException;
import java.util.NoSuchElementException;


public class ImportarFicheirosMaquinasUI extends AbstractUI {
    @Override
    protected boolean doShow() {
        ImportarFicheirosMaquinasController controller=new ImportarFicheirosMaquinasController();
        try {
            System.out.println("Inicio da importacao");
            controller.iniciarAImportacao();
            System.out.println("Fim da importacao");
        } catch (IllegalArgumentException | NoSuchElementException | DateTimeException e){
            System.out.println("Erro: "+e.getMessage());
        }
        return false;
    }

    @Override
    public String headline() {
        return "Importar mensagens de ficheiros no sistema";
    }
}
