# Regras e técnicas


## Packages

Vertical slicing.


## Issues
* Atribuir o issue a quem o vai executar.
* Alterar o estado do issue ao longo do progresso da sua execução.


## Nomenclatura de commits

Cada commit deve estar associado a um issue.
Formato: Tipo-Fase-Identificador

Exemplos:
"UC-D-002: (...)" (use case)
"IS-D-002: (...)" (issue não relacionado com use cases específicos)


### Análise

UC - Use Case
IS - Issue


### Tipos

#### A - Análise
  Conversar com o cliente para perceber os seus requisitos e o seu
vocabulário. Construir glossário de conceitos. Perceber o contexto do caso de
uso e quais os invariantes de domínio. Que tipo de interacção existirá com o
utilizador? Que tipo de utilizador pode executar este caso de uso?

#### De - Design
  Criar testes unitários que capturem os invariantes de domínio. Decidir
que classes criar, onde as colocar, que responsabilides lhe atribuir, quais as
interações existentes entre objetos para realizar o caso de uso.

#### P - Planeamento de testes
  Que casos de testes devem ser efetuados para validar
a funcionalidade a entregar ao cliente (funcionalidade end-to-end; testes
funcionais manuais)? Que dados de entrada são necessários para os testes? Que
(dados de) resultados são esperados em cada caso de teste de acordo com os
dados de entrada?

#### DT - Desenvolvimento e testes
  Codificação de acordo com o design e execução de
testes de desenvolvimento (unitários e funcionais).

#### I - Integração
  Junção do código na base de código comum do repositório,
execução de testes unitários a todo o sistema. Execução dos testes funcionais
principais.


### Identificador

Número do issue no BitBucket.


## Tasks

Apenas fazer assign pós terminar o anterior.


## Wiki

### Requisitos
Não devem repetir o caso de uso descrito no caderno de encargos, nem devem inventar o texto de caso de uso. devem indicar aqui a informação adicional que obtiveram do cliente sobre esse requisito. se conversaram com o cliente e elaboraram com o cliente o caso de uso, aí sim, deverão colocar o caso de uso nesta secção.

### Análise
Inclui SSD e descrição.

Nesta secção importa essencialmente indicar se o modelo de dominio dá ou não respotsa ao requisito do caso de uso ou se há necessidade de estender o modelo de dominio 

### Design
Inclui SD + CD + descrição dos testes.

Um caso de uso "Listagem de X" será praticamente igual a todos os outros casos de uso "Listagem ...", pelo que não faz sentido repetir em cada um um diagrama de sequencia, diagrama de classes, etc. É preferivel que tenham uma area global onde descrevem genericamente os casos de uso de listagem e em cada user story concreta apenas indiquem as especificidades desse caso de uso se existirem. Devem indicar que testes unitários estão pensados que capturem os requisitos e invariantes de negócio. Testes planeiam-se antes da implementação.


## Desenvolvimento

### Nomenclatura no código

* Classes, enums e interfaces: CamelCase com a primeira letra maiúscula.
* Instâncias de classes: CamelCase com a primeira letra minúscula.
* Utilizam os nomes definidos no glossário sempre que possível.

### Implementação
* Documentar funções e classes sempre, mas comentar apenas se realmente necessário. Quem lê o código sabe programar e deve entendê-lo sem necessitar de comentários.
* Identificar dependências entre tarefas e mitigar essas dependências. Exemplo: decidir o nome das classes que são partilhadas entre user stories e fazer um commit inicial apenas com o esqueleto dessa classe.
* Deixar o projeto sempre num estado estável, ou seja, em qualquer momento que se faça pull deve ser possível compilar sem erros e executar todos os testes automáticos a passar.
* Fazer commmits frequentes para minimizar problemas de integração.
