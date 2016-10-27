# language: pt

Funcionalidade: Cadastrar Avaliações dos Cursos

  Cenário: Avaliações dos Cursos
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "T&D > Cadastros > Tipo de Despesa"
    Então eu devo ver o título "Tipo de Despesa"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Tipo de Despesa"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Tipo de Despesa"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Tipo de Despesa"
    E eu preencho "Descrição" com "professores"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Tipo de Despesa"
    E eu devo ver "professores"

    Entao eu clico em editar "professores"
    E eu devo ver o título "Editar Tipo de Despesa"
    E o campo "Descrição" deve conter "professores"
    E eu preencho "Descrição" com "coffe break"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Tipo de Despesa"

    Então eu clico em excluir "coffe break"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Tipo de Despesa excluído com sucesso."
    E eu não devo ver "coffe break"