package eapli.base.solicitarConfiguracaoMaquina.application;

import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaomaquina.domain.FicheiroConfiguracao;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.gestaomaquina.repository.MaquinaRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.solicitarConfiguracaoMaquina.tcp.EnviarConfiguracaoMaquinaTcp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class SolicitarConfiguracaoMaquinaController {

    MaquinaRepository repo = PersistenceContext.repositories().maquinas();

    public boolean enviarConfigPorTcp(String caminho, String codInterno, int configEscolhido) throws IllegalArgumentException {

        CodigoInternoMaquina cod = new CodigoInternoMaquina(codInterno);
        Maquina maq;
        try {
            maq = repo.findByIdentifier(cod).get();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Máquina não existe.");
        }

        String data;

        if (configEscolhido == 0 && caminho != null) {
            try {
                Scanner in = new Scanner(new File(caminho));
                data = getFicheiro(in);
            } catch (FileNotFoundException e) {
                throw new IllegalArgumentException("Ficheiro não encontrado.");
            }
        } else {
            try {
                Scanner in =
                        new Scanner(repo.findByIdentifier(new CodigoInternoMaquina(codInterno)).get().ficheiroConfiguracao.get(configEscolhido).file);
                data = getFicheiro(in);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        }

        return EnviarConfiguracaoMaquinaTcp.enviarConfiguracaoMaquinaTcp(data, maq);
    }


    /**
     * Load do ficheiro config
     *
     * @throws FileNotFoundException caso o ficheiro nao exista
     */
    private String getFicheiro(Scanner in) {

        StringBuilder data = new StringBuilder();

        if (in.hasNextLine()) {
            data.append(in.nextLine());
        }
        while (in.hasNextLine()) {
            data.append("\n" + in.nextLine());
        }
        in.close();
        return data.toString();
    }

    /**
     * Verifica se a maquina tem ficheiros de config
     */
    public boolean temConfi(String idMaquina) {
        CodigoInternoMaquina cod = new CodigoInternoMaquina(idMaquina);

        Optional<Maquina> maq = repo.findByIdentifier(cod);

        if (maq.isPresent()) {
            maq.get().hasConfig();
            return true;
        }
        return false;
    }

    public List<String> ficheirosConfig(String idMaquina) {
        CodigoInternoMaquina cod = new CodigoInternoMaquina(idMaquina);

        Optional<Maquina> maq = repo.findByIdentifier(cod);

        List<String> configs = new ArrayList<>();
        if (maq.isPresent()) {
            for (FicheiroConfiguracao fich : maq.get().getFicheiroConfiguracao()) {
                configs.add(fich.descricao);
            }
            return configs;
        }
        return null;
    }
}
