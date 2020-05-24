package eapli.base.infrastructure.application.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CsvFileScanner implements FileScanner<String[]> {
    private final String SEPARATOR;
    private final Scanner scanner;

    public CsvFileScanner(String separator, String filePath, String charsetName) throws FileNotFoundException {
        this.SEPARATOR = separator;
        try {
            scanner = new Scanner(new FileInputStream(new File(filePath)), charsetName);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Ficheiro não encontrado");
        }
    }

    public CsvFileScanner(String separator, String filePath, String charsetName, String... expectedHeaderValues) throws FileNotFoundException, InvalidHeaderException, EmptyFileException {
        this.SEPARATOR = separator;
        try {
            scanner = new Scanner(new FileInputStream(new File(filePath)), charsetName);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Ficheiro não encontrado");
        }

        if (!this.hasNext()) {
            throw new EmptyFileException("O ficheiro está vazio.");
        }

        if (!headerEValido(expectedHeaderValues)) {
            throw new InvalidHeaderException("O cabeçalho do ficheiro é diferente do esperado.");
        }
    }

    @Override
    public String[] next() {
        return scanner.nextLine().split(SEPARATOR);
    }

    @Override
    public boolean hasNext() {
        return scanner.hasNextLine();
    }

    private boolean headerEValido(String... expectedHeaderValues) {
        return validateNextAndSkip(expectedHeaderValues);
    }

    public boolean validateNextAndSkip(String... expectedValues) {
        String[] actualValues = this.next();
        if (actualValues.length != expectedValues.length) {
            return false;
        }
        for (int i = 0; i < actualValues.length; i++) {
            if (!actualValues[i].trim().equals(expectedValues[i].trim())) {
                return false;
            }
        }
        return true;
    }
}
