package eapli.base.processamentoMensagens.application;

import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.dto.LinhaProducaoDTO;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.gestaomaquina.repository.MaquinaRepository;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.infrastructure.application.DTOUtils;
import eapli.base.infrastructure.application.EntityUtils;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.processamentoMensagens.application.DTO.AgendamentoDeProcessamentoDTO;
import eapli.base.processamentoMensagens.domain.AgendamentoDeProcessamento;
import eapli.base.processamentoMensagens.domain.FinalDeProcessamento;
import eapli.base.processamentoMensagens.domain.InicioDeProcessamento;
import eapli.base.processamentoMensagens.repositories.AgendamentoDeProcessamentoRepository;
import java.time.temporal.ChronoUnit;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

public class ProcessamentoDeMensagensController {
    private final AgendamentoDeProcessamentoRepository agendamentoDeProcessamentoRepository;
    private final MensagemRepository mensagemRepository;
    private final LinhaProducaoRepository linhaProducaoRepository;
    private final MaquinaRepository maquinaRepository;
    private  AgendamentoDeProcessamento agendamentoDeProcessamento;
    private List<AgendamentoDeProcessamento> listaDeAgendamentosBaseDados;
    private List<AgendamentoDeProcessamento> listaDeNovosAgendamentos;
    private List<LinhaProducao> listaLinhasProducao;
    private List<LinhaProducaoDTO> listaLinhaProducaoDTO;
    private List<LinhaProducao> linhaProducaoAlvo;
    private Map<String,LinhaProducao> identificador_LinhaProducao;
    private Thread[] threads;

    public ProcessamentoDeMensagensController() {
        this.agendamentoDeProcessamentoRepository = PersistenceContext.repositories().agendamentoDeProcessamento();
        this.maquinaRepository=PersistenceContext.repositories().maquinas();
        this.mensagemRepository=PersistenceContext.repositories().mensagem();
        this.linhaProducaoRepository=PersistenceContext.repositories().linhasProducao();
        this.listaLinhasProducao=linhaProducaoRepository.findAllList();
        this.listaLinhaProducaoDTO= Collections.unmodifiableList(DTOUtils.toDTOList(listaLinhasProducao));
        this.linhaProducaoAlvo=new ArrayList<>();
        this.listaDeAgendamentosBaseDados=new ArrayList<>();
        this.listaDeNovosAgendamentos=new ArrayList<>();
        this.identificador_LinhaProducao=Collections.unmodifiableMap(EntityUtils.mapIdStringToEntity(listaLinhasProducao));
        threads=new Thread[linhaProducaoAlvo.size()];
    }


    /**
     *
     * @param dataInicio data inicio de agendamento
     * @param dataFinal data final de agendamento
     * @param tempoInicial tempo inicio de agendamento
     * @param tempoFinal tempo final de agendamento
     * @return retorna true caso o que foi inserido é valido
     * @throws ParseException
     */
    public boolean validarInput(String dataInicio, String dataFinal, String tempoInicial, String tempoFinal) throws ParseException {
        int diferencaDias=validarDatas(dataInicio,dataFinal);
        validarTempo(tempoInicial,tempoFinal,diferencaDias);
        for (LinhaProducao linhaProducao:linhaProducaoAlvo){
            AgendamentoDeProcessamento agendamentoDeProcessamento=new AgendamentoDeProcessamento(new InicioDeProcessamento(dataInicio, tempoInicial),new FinalDeProcessamento(dataFinal, tempoFinal));
            agendamentoDeProcessamento.setLinhasProducao(linhaProducao);
            listaDeNovosAgendamentos.add(agendamentoDeProcessamento);
        }
        InicioDeProcessamento inicioDeProcessamento = new InicioDeProcessamento(dataInicio, tempoInicial);
        FinalDeProcessamento finalDeProcessamento = new FinalDeProcessamento(dataFinal, tempoFinal);
        this.agendamentoDeProcessamento = new AgendamentoDeProcessamento(inicioDeProcessamento, finalDeProcessamento);
        return true;
    }

    /**
     * Retorna uma lista de linhas de producao
     * @return
     */
    public List<LinhaProducaoDTO> listaDeLinhasDeProducao(){
        return listaLinhaProducaoDTO;
    }

    /**
     * Seleciona linha de producao que prentede agendar o processamento
     * @param idsLinhaProducao Lista do tipo String
     */
    public void selecionarLinhaProducao(List<String> idsLinhaProducao){
        for (String alvos:idsLinhaProducao){
            LinhaProducao alvo=identificador_LinhaProducao.get(alvos.trim());
            linhaProducaoAlvo.add(alvo);
        }
    }

    /**
     * Regista um novo agendamento de processamento na base de dados
     */
    public void registar(){
        for (AgendamentoDeProcessamento agendamentoDeProcessamento:listaDeNovosAgendamentos){
            agendamentoDeProcessamentoRepository.save(agendamentoDeProcessamento);
        }
    }


    public  void iniciarProcessamento() throws InterruptedException {
        int i;
        Map<IdentificadorLinhaProducao,List<Mensagem>> listaDeMensagensDeCadaLinhaDeProducao=new HashMap<>();
        for (i=0;i<linhaProducaoAlvo.size();i++){
            listaDeMensagensDeCadaLinhaDeProducao.put(linhaProducaoAlvo.get(i).identifier,new ArrayList<>());
        }
        List<Mensagem> listaMensagensDentroDosLimites=new ArrayList<>();
        List<Mensagem> listaMensagensNaoProcessadasBaseDados= mensagemRepository.obterListaMensagensNaoProcessadas();
        if (listaMensagensNaoProcessadasBaseDados.isEmpty())
            throw new IllegalArgumentException("Sem mensagens registadas na base de dados");
        Date dataInicio=agendamentoDeProcessamento.inicioDeProcessamento.dataTempoInicio;
        Date dataFinal=agendamentoDeProcessamento.finalDeProcessamento.dataTempoFinal;
        for (Mensagem mensagem:listaMensagensNaoProcessadasBaseDados){
            Date dataEmissao=mensagem.mensagemID.tempoEmissao.timestamp;
            System.out.println(dataEmissao);
            if ((dataInicio.compareTo(dataEmissao)<=0) && (dataFinal.compareTo(dataEmissao)>=0)){ //Verificar se esta entre as duas datas selecionadas
                listaMensagensDentroDosLimites.add(mensagem);
            }
        }
        if (listaMensagensDentroDosLimites.size()==0){
            throw new IllegalArgumentException("Não há mensagens para processar para o periodo proposto!");
        }
        for (Mensagem mensagem:listaMensagensDentroDosLimites){
            CodigoInternoMaquina codigoInternoMaquina=mensagem.mensagemID.codigoInternoMaquina;
            Optional<Maquina> opt=maquinaRepository.findByIdentifier(codigoInternoMaquina);
            Maquina maquina=opt.get();
            if (listaDeMensagensDeCadaLinhaDeProducao.containsKey(maquina.getLinhaProducao()))
                listaDeMensagensDeCadaLinhaDeProducao.get(maquina.getLinhaProducao()).add(mensagem);
        }
        System.out.println("BORING");
        //Criacao das THREADS
        for (i=0;i<linhaProducaoAlvo.size();i++){
            List<Mensagem> lista=listaDeMensagensDeCadaLinhaDeProducao.get(i);
            LinhaProducao linhaProducao=linhaProducaoAlvo.get(i);
            ProcessamentoDeMensagensThread processamentoDeMensagensThread=new ProcessamentoDeMensagensThread(lista,linhaProducao,mensagemRepository,linhaProducaoRepository);
            threads[i]=new Thread(processamentoDeMensagensThread);
            threads[i].start();
        }
        //Esperar que terminem
        for (Thread thread:threads){
            thread.join();
        }
    }

    /**
     * Verifica se a data inicio é antes da data final
     * @param dataInicio Data inicio em formato String
     * @param dataFinal Data final em formato String
     * @return Retorna a diferenca de dias entre a data final e inicial
     */
    private int validarDatas(String dataInicio,String dataFinal){
        String dataInicioSeparar[]=dataInicio.trim().split("-");
        String dataFinalSeparar[]=dataFinal.trim().split("-");
        if (dataInicioSeparar.length!=3 || dataFinalSeparar.length!=3)
            throw new IllegalArgumentException("Formato Errado! (YYYY-MM-DD)");
        LocalDate inicio=LocalDate.of(Integer.parseInt(dataInicioSeparar[0]),Integer.parseInt(dataInicioSeparar[1]),Integer.parseInt(dataInicioSeparar[2]));
        LocalDate fim= LocalDate.of(Integer.parseInt(dataFinalSeparar[0]),Integer.parseInt(dataFinalSeparar[1]),Integer.parseInt(dataFinalSeparar[2]));
        int diferencaDias=(int)ChronoUnit.DAYS.between(inicio,fim);
        if (diferencaDias>=0) {
                return diferencaDias;
        }
        throw new IllegalArgumentException("Data final não pode ser antes da Data inicial!");
    }


    /**
     * Verifica se a diferenca do tempo final com inicial é positiva
     * @param tempoInicial Tempo inicial em formato String
     * @param tempoFinal Tempo final em formato String
     * @return True caso seja valido, false caso seja invalido
     */
    private void validarTempo(String tempoInicial,String tempoFinal,int diferenca){
        String tempoInicioSeparar[]=tempoInicial.trim().split(":");
        String tempoFinalSeparar[]=tempoFinal.trim().split(":");
        if (tempoInicioSeparar.length!=2 || tempoFinalSeparar.length!=2)
            throw new IllegalArgumentException("Formato errado (HH:MM)");
        if (diferenca==0) {
            int inicio = Integer.parseInt(tempoInicioSeparar[0]) * 60 + Integer.parseInt(tempoInicioSeparar[1]);
            int fim = Integer.parseInt(tempoFinalSeparar[0]) * 60 + Integer.parseInt(tempoFinalSeparar[1]);
            if (fim - inicio < 0)
            throw new IllegalArgumentException("Tempo final não pode ser antes do Tempo inicial do mesmo dia!");
        }
    }
}
