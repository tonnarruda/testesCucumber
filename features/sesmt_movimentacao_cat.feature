# language: pt

Funcionalidade: CATs (Acidentes de Trabalho)

  Cenário: Cadastro de CATs
    Dado que exista um colaborador "geraldo", da area "administracao", com o cargo "desenvolvedor" e a faixa salarial "I"

    Dado que eu esteja logado
    Quando eu acesso o menu "SESMT > Movimentações > CATs (Acidentes de Trabalho)"
    Então eu devo ver o título "CAT - Comunicação de Acidente de Trabalho"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Comunicação de Acidente de Trabalho"
    E eu preencho "Nome" com "Gera"
    E eu clico no botão "Pesquisar"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "CAT - Comunicação de Acidente de Trabalho"

    Então eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Comunicação de Acidente de Trabalho"
    E eu preencho "Nome" com "Gera"
    E eu clico no botão "Pesquisar"
    E eu seleciono "geraldo" de "Colaborador"
    E eu preencho o campo (JS) "data" com "29/07/2011"
    E eu preencho "Número" com "123456"
    E eu preencho "Observação" com "socorrido pelo seguranca"
    E eu marco "Gerou Afastamento (informação utilizada no PCMSO)"
    E eu clico no botão "Gravar"
    E eu devo ver o título "CAT - Comunicação de Acidente de Trabalho"
    E eu devo ver "geraldo"

    Entao eu clico em editar "geraldo"
    E eu devo ver o título "Editar Comunicação de Acidente de Trabalho"
    E eu devo ver "Colaborador: geraldo"
    E o campo "data" deve conter "29/07/2011"
    E eu preencho o campo (JS) "data" com "27/07/2011"
    E eu preencho "Número" com "456879"
    E eu clico no botão "Gravar"
    E eu devo ver o título "CAT - Comunicação de Acidente de Trabalho"

    Então eu clico em excluir "geraldo"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu não devo ver "geraldo"

