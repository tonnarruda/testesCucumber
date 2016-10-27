# language: pt

Funcionalidade: Período de Acompanhamento de Experiência

  Cenário: Cadastro de Período de Acompanhamento de Experiência
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Aval. Desempenho > Cadastros > Dias do Acompanhamento do Período de Experiência"
    Então eu devo ver o título "Períodos de Acompanhamento de Experiência"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Período de Acompanhamento de Experiência"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"

    Então eu devo ver o título "Períodos de Acompanhamento de Experiência"
    E eu clico no botão "Inserir"

    Então eu devo ver o título "Inserir Período de Acompanhamento de Experiência"
    E eu preencho "Qtd. de Dias" com "9000"
    E eu preencho "Descrição" com "1ª Avaliação"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Períodos de Acompanhamento de Experiência"
    E eu devo ver "9000"

    Entao eu clico em editar "9000"
    E eu devo ver o título "Editar Período de Acompanhamento de Experiência"
    E o campo "Qtd. de Dias" deve conter "9000"
    E eu preencho "Qtd. de Dias" com "1000"
    E eu preencho "Descrição" com "2ª Avaliação"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Períodos de Acompanhamento de Experiência"
    E eu não devo ver na listagem "9000"
    E eu devo ver "1000"

    Então eu clico em excluir "1000"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Período de Acompanhamento de Experiência excluído com sucesso."
    E eu não devo ver "1000"












