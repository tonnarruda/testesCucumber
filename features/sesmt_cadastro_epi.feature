# language: pt

Funcionalidade: EPI

  Cenário: Cadastro de EPI
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > EPI"
    Então eu devo ver o título "EPIs (Equipamentos de Proteção Individual)"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir EPI"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"

    Então eu devo ver o título "EPIs (Equipamentos de Proteção Individual)"
    E eu clico no botão "Inserir"
    E eu preencho "Nome" com "bota"
#Tem que terminar, tem uma gambi no cadastro