# Gestão de Informação de Chão de Fábrica - 2020

## Na secção [Wiki](https://github.com/ArthurSchiavom/gestao-informacao-chao-de-fabrica--projeto-academico/wiki) do GitHub, pode encontrar a documentação deste projeto

## Descrição

Este projeto tem como objetivo processar informação proveniente de uma chão de fábrica, ou seja, de conjuntos de máquinas indrustriais.

Foram desenvolvidas 3 aplicações no total: Sistema Central de Processamento (Java), Sistema de Monitorização de Máquinas (Java) e Simulador de Máquina (C), de acordo com o seguinte diagrama: [Diagrama Ilustrativo dos Módulos do Sistema (PNG)](README_assets/Aplications.png)

## Tecnologias/Frameworks/Ferramentas

O Sistema Central e Sistema de Monitorização de Máquinas foram desenvolvidos em Java. O Sistema Central é suportado por uma base de dados relacional H2, que é acessada através da API JPA. Foi utilizado o JAXB para exportação em formato XML.

Para a implementação de testes unitários foi utilizado JUnit, Mockito e ReflectionUtils (da framework Spring).

Para controlo de versões foi utilizado o Git. A gestão de dependências foi efetuada através do Maven.

Como ferramenta de colaboração de trabalho em equipa foram utilizados os issues do BitBucket.

## Requesitos

### [Requesitos do Sistema a Desenvolver (PDF)](README_assets/LAPR4-SistemaDesenvolver.pdf)

### [Descrição do Protocolo de Comunicação (PDF)](README_assets/LAPR4-ProtocoloComunicacao.pdf)

### [Diagrama Ilustrativo dos Módulos do Sistema (PNG)](README_assets/Aplications.png)

## User Stories - Documentos Microsoft Excel

### [Sprint A](README_assets/US_SprintA.xlsx)

### [Sprint B](README_assets/US_SprintB.xlsx)

### [Sprint C](README_assets/US_SprintC.xlsx)

### [Sprint D](README_assets/US_SprintD.xlsx)

## Como executar o projeto

### 1. Configurar a base de dados H2

1. Fazer download do H2 em https://www.h2database.com/html/download.html.

2. Abrir uma linha de comandos (recomendo a PowerShell) e navegar para o diretório onde o programa foi instalado: (no Windows) Program Files (x86)\H2\bin.

3. Neste diretório podemos executar os seguintes comandos: 

3.1. Iniciar configuração - pressionar enter depois de digitar a informação
* Window: java -cp "h2-1.4.200.jar;%H2DRIVERS%;%CLASSPATH%" org.h2.tools.Shell
* MAC: (subtituir $dir pelo caminho absoluto até ao h2/bin) java -cp "$dir/h2-1.4.200.jar:$H2DRIVERS:$CLASSPATH" org.h2.tools.Shell

3.2. Definir URL: jdbc:h2:~/BaseDados_LAPR4_2020_G3

3.3. Definir Driver: org.h2.driver

3.4. Definir Username: sa

3.5. Definir Password (por segurança, os caracteres não aparecem quando os digita): eapli

4. Agora deve aparecer "Connected" e "sql>", o que significa que a configuração foi concluída com sucesso. Digitem `exit` e pressionem enter.

5. A base de dados foi configurada. Agora é preciso compilar e depois correr a aplicação.

5.1. Para compilar, abram uma janela de consola na pasta inicial da projeto Java (pasta chamada Projeto, que inclui a pasta base.app.backoffice.console).

5.2. Corram o comando rebuild-all.bat (em Windows) ou rebuild-all.sh (em Linux ou MAC)

5.3. Configurem a base de dados com o comando run-bootstrap.bat (em Windows) ou run-bootstrap.sh (em Linux ou MAC)

5.4. Iniciem a aplicação com o comando run-backoffice.bat (em Windows) ou run-backoffice.sh (em Linux ou MAC)

5.5. Façam login com as credenciais (usar letras maiúsculas e minúsculas como indicado):
Utilizador: admin
Password: Password1

A aplicação Java está a correr agora e tem acesso às suas funcionalidades.

O projeto é constituído por mais duas aplicações que funcionam individualmente mas utilizam-se umas às outras:
* SimuladorMaquina - Para o compilar deve utilizar os programas CMake e make, com os comandos:
1. cmake
2. make
3. ./SimuladorMaquina

* SistemaMonitorizacaoMaquinas - Deve ser compilado como um projeto Maven.
