# language: pt

Funcionalidade: CATs (Acidentes de Trabalho)

  Cenário: Cadastro de CATs
    Dado que exista um colaborador "geraldo", da area "administracao", com o cargo "desenvolvedor" e a faixa salarial "I"

    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Movimentações > Ficha de Investigação de Acidente(CAT)"
    Então eu devo ver o título "Ficha de Investigação de Acidente (CAT)"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Ficha de Investigação de Acidente (CAT)"
    E eu preencho "Nome" com "Gera"
    E eu clico no botão "Pesquisar"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Ficha de Investigação de Acidente (CAT)"

    Então eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Ficha de Investigação de Acidente (CAT)"
    E eu preencho "Nome" com "Gera"
    E eu clico no botão "Pesquisar"
    E eu seleciono "geraldo - 123.213.623-91" de "Colaborador"
    E eu preencho o campo (JS) "data" com "29/07/2011"
    E eu preencho "horario" com "0800"
    E eu marco "gerouAfastamento"
    E eu preencho "qtdDiasAfastado" com "30"
    E eu marco "emitiuCAT"
    E eu preencho "numero" com "123456"
    E eu preencho "Descrição do Acidente:" com "socorrido pelo seguranca"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Ficha de Investigação de Acidente (CAT)"
    E eu devo ver "geraldo"

    Entao eu clico em editar "geraldo"
    E eu devo ver o título "Editar Ficha de Investigação de Acidente (CAT)"
    E eu devo ver "Colaborador: geraldo"
    E o campo "data" deve conter "29/07/2011"
    E eu preencho o campo (JS) "data" com "27/07/2011"
    E eu preencho "numero" com "123456"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Ficha de Investigação de Acidente (CAT)"

    Então eu clico em excluir "geraldo"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu não devo ver "geraldo"

