package eapli.base.infrastructure.application.files;

public interface FileScanner<T> {
    T next();
    boolean hasNext();
    boolean validateNextAndSkip(T expected);
}
