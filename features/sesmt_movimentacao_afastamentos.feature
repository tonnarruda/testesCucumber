# language: pt

Funcionalidade: Afastamentos

  Cenário: Cadastro de Afastamentos
    Dado que exista um colaborador "geraldo", da area "administracao", com o cargo "desenvolvedor" e a faixa salarial "I"
    Dado que exista um afastamento "doença"

    Dado que eu esteja logado com o usuário "fortes"
    Quando eu acesso o menu "SESMT > Movimentações > Afastamentos"
    Então eu devo ver o título "Afastamentos"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Afastamento"
    E eu preencho "Nome" com "Gera"
    E eu clico no botão "Pesquisar"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Afastamentos"

    Então eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Afastamento"
    E eu preencho "Nome" com "Gera"
    E eu clico no botão "Pesquisar"
    E eu seleciono "geraldo" de "Colaborador"
    E eu seleciono "doença" de "Motivo"
    E eu preencho o campo (JS) "inicio" com "25/07/2011"
    E eu preencho o campo (JS) "fim" com "29/07/2011"
    E eu preencho "CID" com "A010"
    E eu preencho "Médico" com "fernando"
    E eu preencho "CRM" com "9999"
    E eu preencho "Observações" com "acidentado"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Afastamentos"
    E eu devo ver "geraldo"

    Entao eu clico em editar "geraldo"
    E eu devo ver o título "Editar Afastamento"
    E eu devo ver "Colaborador: geraldo"
    E o campo "inicio" deve conter "25/07/2011"
    E o campo "fim" deve conter "29/07/2011"
    E eu preencho o campo (JS) "inicio" com "27/07/2011"
    E eu preencho o campo (JS) "fim" com "01/08/2011"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Afastamentos"

    Então eu clico em excluir "geraldo"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu não devo ver "geraldo"

