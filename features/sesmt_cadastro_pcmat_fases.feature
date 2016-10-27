# language: pt

Funcionalidade: Fases

  Cenário: Cadastro de Fases
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > PCMAT > Fases"

    Então eu devo ver o título "Fases"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Fase"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"

    Então eu devo ver o título "Fases"
    E eu clico no botão "Inserir"
    E eu preencho "Descrição" com "Escavação"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Fases"
    Então eu devo ver "Escavação"

    Entao eu clico em editar "Escavação"
    E eu devo ver o título "Editar Fase"
    E o campo "Descrição" deve conter "Escavação"
    E eu preencho "Descrição" com "Terraplanagem"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Fases"
    Então eu devo ver "Terraplanagem"

    Então eu clico em excluir "Terraplanagem"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Fase excluída com sucesso."
    Então eu não devo ver na listagem "Terraplanagem"












