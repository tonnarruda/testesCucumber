# language: pt

Funcionalidade: Agenda

  Cenário: Cadastro de Agenda
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista o evento "Semana da Saude"

    Quando eu acesso o menu "SESMT > Cadastros > PCMSO > Agenda"
    Então eu devo ver o título "Agenda"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Agenda"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    Quando eu clico no botão "Voltar"
    Então eu devo ver o título "Agenda"

    Quando eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Agenda"
    E eu preencho o campo (JS) "Mês/Ano" com "01/2013"
    E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
    E eu seleciono "Semana da Saude" de "Evento"
    E eu clico no botão "Gravar"

    Então eu devo ver o título "Agenda"
    Então eu clico "Exibir Filtro"
    E eu preencho "dataMesAnoIni" com "01/2013"
    E eu clico no botão "Pesquisar"
    E eu devo ver "Semana da Saude"
    E eu devo ver "01/2013"

    Então eu clico em excluir "Semana da Saude"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Agenda excluída com sucesso."
    Então eu não devo ver na listagem "Semana da Saude"