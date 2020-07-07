package eapli.base.servicoComunicacaoComMaquinas;


import eapli.base.gestaoproducao.gestaodeposito.repository.DepositoRepository;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


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
        Mensagem mensagem;
        fileScanner.validarCampos();
        for (String[] vec : fileScanner.listaDeMensagens) {
            try {
                mensagem = messageFactory.getMessageType(vec);
                if (mensagem != null) {
                    mensagemRepository.save(mensagem);
                }
            } catch (Exception e) {
            }
        }
        Path from = Paths.get("test_material\\Mensagens\\" + file.getName());
        Path to = Paths.get("test_material\\MensagensProcessadas\\" + file.getName());
        try {
            Path temp = Files.move(from, to);
        } catch (IOException e) {
        }
    }
}