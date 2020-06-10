cmake_minimum_required(VERSION 3.16)
project(SimuladorMaquina C)

set(CMAKE_C_STANDARD 90)

add_executable(SimuladorMaquina base/consola/main.c test/udp_client.c test/udp_client.h base/core/comunicacao/comunicar.c base/core/comunicacao/comunicar.h base/core/maquina/informacao_maquina.h base/core/comunicacao/informacao_comunicacao.h base/core/utils/const.h base/core/mensagem/carregamento_mensagem.c base/core/mensagem/carregamento_mensagem.h base/core/boot/boot.c base/core/boot/boot.h base/core/comunicacao/scm/envio_mensagens_carregadas.c base/core/comunicacao/scm/envio_mensagens_carregadas.h base/core/comunicacao/scm/comunicacao_sistema_central.c base/core/comunicacao/scm/comunicacao_sistema_central.h base/core/comunicacao/scm/rececao_packets.c base/core/comunicacao/scm/rececao_packets.h base/core/utils/ficheiros.c base/core/utils/ficheiros.h base/core/comunicacao/udp_server/rececao_pacotes_udp.c base/core/comunicacao/udp_server/rececao_pacotes_udp.h)

TARGET_LINK_LIBRARIES(SimuladorMaquina pthread rt)