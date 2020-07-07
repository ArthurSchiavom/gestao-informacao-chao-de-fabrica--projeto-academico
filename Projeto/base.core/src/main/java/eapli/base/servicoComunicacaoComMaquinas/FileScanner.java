package eapli.base.servicoComunicacaoComMaquinas;

import eapli.base.infrastructure.application.files.EmptyFileException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileScanner {
    private final String SEPARATOR;
    private final Scanner scanner;
    public final  List<String[]> listaDeMensagens;

    public FileScanner(String separator, File file, String charsetName) throws FileNotFoundException, EmptyFileException {
        this.SEPARATOR = separator;
        this.listaDeMensagens=new ArrayList<>();
        try {
            scanner = new Scanner(new FileInputStream(file), charsetName);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Ficheiro não encontrado");
        }
        if (!this.hasNext()) {
            throw new EmptyFileException("O ficheiro está vazio.");
        }

    }

    public String[] next() {
        return scanner.nextLine().split(SEPARATOR);
    }

    public boolean hasNext() {
        return scanner.hasNextLine();
    }

    public boolean validarCampos(){
        while (hasNext()){
            String vec[]=next();
            listaDeMensagens.add(vec);
        }
        scanner.close();
        return true;
    }
}
