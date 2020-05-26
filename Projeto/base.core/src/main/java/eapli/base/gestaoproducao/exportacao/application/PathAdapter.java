package eapli.base.gestaoproducao.exportacao.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PathAdapter {
	/**
	 * Converte uma string path (e.g. "test_materials/XML/FicheiroExemplo.xml") num ficheiro e cria todos os
	 * diretórios necessários que sejam introduzidos anteriormente
	 * <br>
	 * Como path separators aceita '/' e '\'
	 *
	 * @param path a path que queremos (e.g. "test_materials/XML/FicheiroExemplo.xml")
	 * @return um ficheiro que podemos manipular
	 * @throws IOException se houver algum erro a criar os diretórios até ao ficheiro
	 */
	public static File makeFileFromPathString(String path) throws IOException {
		List<String> validPathSeparators = new ArrayList<>();
		validPathSeparators.add("/");
		validPathSeparators.add("\\");
		for (String pathSeparator : validPathSeparators) {
			int dirIndex = path.lastIndexOf(pathSeparator);
			if (dirIndex == -1) { //Does not contain this path separator
				continue;
			}
			File dir = new File(path.substring(0, dirIndex));
			if(!dir.exists()) {
				if(!dir.mkdirs()) {
					throw new IOException("Falha ao criar ficheiro no path introduzido");
				}
			}
			return new File(dir, path.substring(dirIndex));
		}
		//Se chegou aqui é porque não tem diretorios
		return new File(path);
	}
}
