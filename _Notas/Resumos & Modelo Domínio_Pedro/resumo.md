# Smart Shop Floor Management

* Especializada na área de gestão e controlo de produção industrial.
* Distintas áreas de negócio(e.g. cortiças, calçado, cutelaria).

Tem de poder fazer:

* Controlo das ordens de produção e execução
* Gestão do tempo de atividade e paragem de máquinas
* Gestão de consumos de matérias-primas
* Registo de quantidades produzidas efetivamente
* Disponibilizar informação relevante a sistemas externos
* Ler informação proveniente de sistemas externos

## Resumo

### Unidade Industrial

* Grupo de uma ou mais [fábricas](#Fábrica).

### Fábrica

    *PARA ESTE PROJETO SÓ VAI EXISTIR UMA FÁBRICA*

* Dedica-se exclusivamente á produção de um [catálogo de produtos](#Catálogo%20de%20Produtos).
* Possui um conjunto diversificado de [linhas de produção](#Linha%20de%20Produção).

### Linha de Produção

* Organização sequencial de [máquinas](#Máquina).
* Apenas uma [Ordem de Produção](#Ordem%20de%20Produção) está a correr por cada linha de produção.

### Máquina

* Organizadas sequencialmente em [linhas de produção](#Linha%20de%20Produção).
* Capacidades de comunicação e tipo de [mensagem](#Mensagem)/informação constante depende da [máquina](#máquina) para máquina.
* Disponibilizam ao sistema [mensagens](#Mensagem) através de ficheiros de texto em vários formatos(e.g. CSV, JSON, XML) e com diferentes estruturas de dados.
* Mais tarde as máquinas vão conseguir comunicar *(com adaptadores próprios)* através de um protocolo simples.
* Transmitem [mensagens brutas](#Mensagem%20Bruta) em ordem cronológica.

### Mensagem

* Formas de comunicação entre o sistema e as [máquinas](#Máquina).
* Ordem e sequência de todas as mensagens não está garantida.
  * Por causa de problemas tais como:
  1. Falhas *momentárias* de rede.
  2. Atrasos no envio das mensagens (modos de funcionamento distintos) .
* São distinguidos por:
  * Data/Hora de **geração** da mensagem
    * Atribuido pela máquina automaticamente.
  * Data/Hora de **envio/receção** para/no sistema.
* Todas as mensagens geradas por uma [máquina](#Máquina) estão garantidas de estar no sistema desde que tenham sido emitidas antes da última mensagem relativa á máquina presente no sistema.

#### Mensagem Bruta

* Mensagens tais e quais como são enviadas pela [máquina](#Máquina).
* *Normalmente* não conteem toda a informação necessária ao seu processamento.
* Precisam de um [serviço de *validação* e *enriquecimento*](#Serviços%20Necessários)

#### Falhas

* Podem ter várias causas.
* **POR AGORA** devem focar-se em falhas de **falta de informação no sistema**
  * Falta de informação sobre [ordem de produção](#Ordem%20de%20Produção).
  * Falta de informação sobre [produto](#Produto).
  * Falta de informação sobre [matéria prima](#Matéria%20Prima).
  * etc...
* Falhas devem ser notificadas aos [utilizadores](#Atores) para que estes corrijam o erro.
* As mensagens devem poder ser posteriormente reprocessadas.
* Falhas ocorrem numa única [linha de produção](#Linha%20de%20Produção) e não afetam outras linhas na fábrica.

### Ordem de Produção

* O Controlo da execução das ordens é **automatizado** pelo sistema.
  * O sistema utiliza as [mensagens](#Mensagem) para automatizar as ordens de produção.
* É sempre referente a um [produto](#Produto) e a uma ou mais [encomendas](#Encomenda).
* A informação é ***gerida*** em [sistemas externos](#Sistemas%20Externos) e vai ser disponibilizada ao sistema conforme necessário.
* A informação *pode* ser inserida **manualmente**.
* É **importante** saber se:
  * A ordem se encontra em execução.
  * Quando se iniciou a sua execução.
  * Quando se concluiu a sua execução.
  * Em que [linha de produção](#Linha%20de%20Produção) se decorreu.
  * Em que [máquinas](#Máquina) se decorreu.
  * Tempo bruto de execução.
  * Tempo efetivo de execução.(Sem contar com pausas devido a falhas).
  * Detalhe de tempos **por [máquina](#Máquina)**:
    * Tempo Bruto.
    * Tempo Efetivo.
  * Consumos reais das [matérias primas](#Matéria%20Prima):
    * Do Produto em Causa.
    * Desvios do que está na [ficha de produção](#Ficha%20de%20Produção).
  * [Lotes](#Lote) e respetivas quantidades de [produto](#Produto) resultante.

### Catálogo de Produtos

* Conjunto de [produtos](#Produto) que são produzidos por uma [fábrica](#Fábrica)

### Produto

* Corresponde a um item que uma [fábrica](#Fábrica) é capaz de produzir. Nalguns casos, um produto pode ser utilizado como [matéria-prima](#Matéria%20Prima) para a produção de outro produto.

### Encomenda

* A informação é *controlada* em [sistemas externos](#Sistemas%20Externos) e vai ser disponibilizada ao sistema conforme necessário.

### Matéria Prima

* Corresponde a um material e/ou produto usado no processo de fabrico de um ou
mais [produtos](#Produto).
* Uma matéria prima pode ser um [produto](#Produto).

### Ficha de Produção

* Possui uma lista de [matérias primas](#Matéria%20Prima) para a produção de um [produto](#Produto).

### Lote

* Corresponde a uma característica atribuída a um conjunto de exemplares de um [produto](#Produto).

## Sistemas Externos

Cada linha descreve um sistema mencionado nos requisitos:

* *Gere* a informação relativamente a [ordens de produção](#Ordem%20de%20Produção) e disponibiliza ao sistema conforme o necessário.
* *Controla* a informação relativamente a [encomendas](#Encomenda) e disponibiliza ao sistema conforme o necessário.
* *Alimenta* o sistema com informação sobre: 
  * [Produtos finais](#Produto).
  * [Ordens de produção](#Ordem%20de%20Produção).
  * Entre outros...
  * Formas de disponibilização:
    * Serviços Web.
    * CSV, JSON, XML

## Serviços Necessários

* Serviço de *validação* e *enriquecimento* de [mensagens](#Mensagem).
  * É importante tomar conta aos casos relacionados com:
  1. [Linhas de produção.](#Linha%20de%20Produção)
      * Informação deve estar diretamente no sistema.
  2. [Ordem de produção](#Ordem%20de%20Produção).
      * As [máquinas](#Máquina) *podem* não saber identificar em que [ordem de produção](#Ordem%20de%20Produção) estão a operar.
          * Será necessário inferir a [ordem de produção](#Ordem%20de%20Produção) através de mensagens disponibilizadas **pela primeira** [máquina](#Máquina) na mesma [linha de produção](#Linha%20de%20Produção).
  * Têm de ser resiliente a [falhas](#Falhas) e facilitar uma resolução.

## Atores

### Gestor de Produção

* Responsável tanto pela manutenção da informação relativa a produtos e
matérias-primas usados como pelo controlo e gestão de informação associada às ordens de
produção.

### Gestor de Chão de Fábrica

* Responsável pela especificação das linhas de produção da fábrica e
respetivas máquinas.

### Administrador

* Responsável pela gestão (e.g. criar, desativar) dos diversos utilizadores do sistema
e respetivas permissões bem como pela configuração geral do sistema.

## Questões Abertas

* Nas [Ordens de Produção](#Ordem%20de%20Produção) os consumos reais é por produto ou para a ordem toda?  

* A primeira [máquina](#Máquina) sabe sempre em que [ordem de produção](#Ordem%20de%20Produção) se encontra?
