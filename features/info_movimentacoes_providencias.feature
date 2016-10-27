# language: pt

Funcionalidade: Providências

  Cenário: Cadastro de providencia Ocorrências

    Dado que exista um colaborador "Rombona", da area "Financeiro", com o cargo "Contador" e a faixa salarial "I"
    Dado que haja uma ocorrencia com id 1, descricao "Falta", pontuacao 1, integraac "false", empresa_id 1
    Dado que haja uma providencia com id 1, descricao "Não Faltar", empresa_id 1
    Dado que haja uma colaboradorocorrencia com id 1, dataIni "2013-11-05", dataFim "2013-11-06", colaborador_id 1, ocorrencia_id 1

    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Movimentações > Providências"
    Então eu devo ver o título "Providências"
    E eu devo ver "Rombona"
    E eu devo ver "Falta"
    E eu não devo ver "Não Faltar"
    E eu clico na ação "Inserir" de "Rombona"

    Então eu devo ver o título "Inserir Providência"
    E eu clico no botão "Cancelar"

    Então eu devo ver o título "Providências"
    E eu clico na ação "Inserir" de "Rombona"
    Então eu devo ver o título "Inserir Providência"
    E eu devo ver "Colaborador: Rombona"
    E eu devo ver "Ocorrencia: Falta"
    E eu devo ver "Data de Início: 05/11/2013"
    E eu devo ver "Data de Término: 06/11/2013"
    E eu seleciono (JS) "1" de "providencia"
    E eu clico no botão "Gravar"

    Então eu devo ver o título "Providências"
    E eu clico na ação "Editar" de "Rombona"
    Então eu devo ver o título "Editar Providência"
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Providências"

    Então eu clico "Exibir Filtro"
   	E eu preencho "Colaborador" com "ro"
   	E eu preencho "Ocorrência" com "Falt"
    E eu seleciono (JS) "C" de "formBusca_comProvidencia"
    E eu clico no botão "Pesquisar"
    E eu devo ver "Rombona"
    E eu devo ver "Falta"

    Então eu preencho "Colaborador" com ""
   	E eu preencho "Ocorrência" com ""
    E eu seleciono (JS) "S" de "formBusca_comProvidencia"
    E eu clico no botão "Pesquisar"
    E eu não devo ver "Rombona"
    E eu não devo ver "Falta"

    E eu seleciono (JS) "T" de "formBusca_comProvidencia"
    E eu clico no botão "Pesquisar"
    E eu devo ver "Rombona"
    E eu devo ver "Falta"
















