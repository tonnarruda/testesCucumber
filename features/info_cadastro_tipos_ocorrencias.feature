# language: pt

Funcionalidade: Tipos de Ocorrências

  Cenário: Cadastro de Tipos de Ocorrências
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Cadastros > Tipos de Ocorrência"
    Então eu devo ver o título "Tipos de Ocorrências"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Ocorrência"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"

    Então eu devo ver o título "Tipos de Ocorrências"
    E eu clico no botão "Inserir"
    E eu preencho "Descrição" com "Falta"
    E eu preencho "Pontuação" com "5"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Tipos de Ocorrências"
    Então eu devo ver "Falta"

    Entao eu clico em editar "Falta"
    E eu devo ver o título "Editar Ocorrência"
    E o campo "Descrição" deve conter "Falta"
    E eu preencho "Descrição" com "Acidente"
    E o campo "Pontuação" deve conter "5"
    E eu preencho "Pontuação" com "7"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Tipos de Ocorrências"
    Então eu devo ver "Acidente"

    Então eu clico em excluir "Acidente"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Ocorrência excluída com sucesso."
    Então eu não devo ver na listagem "Acidente"












