package eapli.base.servicoComunicacaoComMaquinas;


import eapli.base.gestaoproducao.gestaodeposito.repository.DepositoRepository;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.persistence.PersistenceContext;

import java.io.File;


public class ImportarFicheirosMaquinasThread implements Runnable {
    private DepositoRepository depositoRepository;
    private FileScanner fileScanner;
    private File file;
    private MensagemRepository mensagemRepository;
    private MessageFactory messageFactory;

    public ImportarFicheirosMaquinasThread(FileScanner fileScanner, File file) {
        this.fileScanner = fileScanner;
        this.file=file;
        this.mensagemRepository=PersistenceContext.repositories().mensagem();
        this.messageFactory=new MessageFactory();
    }

    @Override
    public void run() {
        int i;
        fileScanner.validarCampos();
        for (String[] vec:fileScanner.listaDeMensagens){
            try {
                Mensagem mensagem=messageFactory.getMessageType(vec);
                if (mensagem!=null)
                    mensagemRepository.save(mensagem);
            } catch (IllegalDomainValueException e) {
                e.printStackTrace();
            }
        }

    }
}
