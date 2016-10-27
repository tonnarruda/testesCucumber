# language: pt

Funcionalidade: Eleição

  Cenário: Cadastro de Eleição
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > CIPA > Eleições"
    Então eu devo ver o título "Eleições"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Eleição"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Eleições"

    E eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Eleição"
    E eu preencho "Descrição" com "cipa 2011"
    E eu preencho o campo (JS) "Posse" com "20/02/2011"
    E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
    E eu clico no botão "Gravar"

    Entao eu devo ver "Geral" dentro de "#menuEleicao"
    E eu devo ver "Calendário" dentro de "#menuEleicao"
    E eu devo ver "Eleitoral" dentro de "#menuEleicao"
    E eu devo ver "Candidatos" dentro de "#menuEleicao"
    E eu devo ver "Resultado" dentro de "#menuEleicao"
    E eu devo ver "Atas e Comunicados" dentro de "#menuEleicao"
    E eu devo ver "Eleição: cipa 2011"
