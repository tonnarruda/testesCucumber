# language: pt

Funcionalidade: Medição dos Riscos nos Ambientes

  Cenário: Cadastro de Medição dos Riscos
    Dado que exista um ambiente "laboratorio" com o risco "contaminacao"

    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Movimentações > Medição dos Riscos > Ambiente"
    Então eu devo ver o título "Medição dos Riscos do Ambiente"
    E eu clico no botão "Inserir"

    Então eu devo ver o título "Inserir Medição dos Riscos do Ambiente"
    E eu clico no botão "CarregarRiscos"
    E eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Medição dos Riscos do Ambiente"

    Então eu clico no botão "Inserir"
    E eu preencho o campo (JS) "data" com "28/07/2011"
    E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
    E eu seleciono "laboratorio" de "ambiente"
    Então eu clico no botão "CarregarRiscos"

    Então eu devo ver "contaminacao"
    E eu preencho "intensidadeValues" com "10"
    E eu preencho "tecnicaValues" com "teste_tecnica_utilizada"
    E eu preencho "ppraValues" com "teste_descricao_ppra"
    E eu preencho "ltcatValues" com "teste_descricao_ltcat"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Medição dos Riscos do Ambiente"
    E eu devo ver "28/07/2011"
    
    Então eu clico em editar "28/07/2011"
    E o campo "data" deve conter "28/07/2011"
    E eu preencho o campo (JS) "data" com "29/07/2011"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Medição dos Riscos do Ambiente"
    E eu não devo ver na listagem "28/07/2011"
    E eu devo ver "29/07/2011"

    Então eu clico em excluir "29/07/2011"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Medição excluída com sucesso."
    E eu não devo ver "29/07/2011"

