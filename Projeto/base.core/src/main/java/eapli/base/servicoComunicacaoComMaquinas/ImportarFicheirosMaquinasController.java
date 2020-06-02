package eapli.base.servicoComunicacaoComMaquinas;

import eapli.base.infrastructure.application.files.EmptyFileException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ImportarFicheirosMaquinasController {
    private static final String SEPARADOR = ";";
    private final String CHARSET_NAME = "UTF-8";
    private FileScanner fileScanner;
    private final File folder;
    private final List<File> files;
    private Thread[] threads;

    public ImportarFicheirosMaquinasController() {
        this.folder=new File("test_material\\Mensagens"); //CAMINHO DAS MENSAGENS
        this.files=new ArrayList<>();
        listFilesForFolder();
        threads=new Thread[(int)files.size()];
    }

    private void listFilesForFolder() {
        for (final File fileEntry : folder.listFiles()) {
            files.add(fileEntry);
        }
    }

    public void iniciarAImportacao() throws InterruptedException, FileNotFoundException, EmptyFileException {
        int i;
        if (files.size()==0)
            throw new IllegalArgumentException("A pasta esta vazia!");
        for (i=0;i<files.size();i++){
            ImportarFicheirosMaquinasThread importarFicheirosMaquinasThread=new ImportarFicheirosMaquinasThread(new FileScanner(SEPARADOR,files.get(i),CHARSET_NAME), files.get(i));
            threads[i]=new Thread(importarFicheirosMaquinasThread);
            threads[i].start();
        }
        for (Thread thread:threads){
            thread.join();
        }
    }

}
