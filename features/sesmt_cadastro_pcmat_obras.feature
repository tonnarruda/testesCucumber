# language: pt

Funcionalidade: Obras

  Cenário: Cadastro de Obras
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > PCMAT > Obras"

    Então eu devo ver o título "Obras"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Obra"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"

    Então eu devo ver o título "Obras"
    E eu clico no botão "Inserir"
    E eu preencho "Nome" com "Caucaia Center"
    E eu preencho "Tipo da obra" com "Shopping"
    E eu preencho "CEP" com "60182-455"
    E eu saio do campo "CEP"
    E eu preencho "Nº" com "571"
    E eu espero 2 segundo
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Obras"
    Então eu devo ver "Caucaia Center"

    Entao eu clico em editar "Caucaia Center"
    E eu devo ver o título "Editar Obra"
    E o campo "Nome" deve conter "Caucaia Center"
    E eu preencho "Nome" com "Caucaia Center 2"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Obras"
    Então eu devo ver "Caucaia Center 2"

    Então eu clico em excluir "Caucaia Center"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Obra excluída com sucesso."
    Então eu não devo ver na listagem "Caucaia Center 2"












