# language: pt

Funcionalidade: Riscos

  Cenário: Cadastro de Riscos
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Riscos"
    Então eu devo ver o título "Riscos"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Risco"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"

    Então eu devo ver o título "Riscos"
    E eu clico no botão "Inserir"
    E eu preencho "Nome" com "choque"
    E eu seleciono "Físico" de "Tipo de Risco"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Riscos"
    Então eu devo ver "choque"

    Entao eu clico em editar "choque"
    E eu devo ver o título "Editar Risco"
    E o campo "Nome" deve conter "choque"
    E eu preencho "Nome" com "queda"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Riscos"
    Então eu devo ver "queda"

    Então eu clico em excluir "queda"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Risco excluído com sucesso."
    Então eu não devo ver na listagem "queda"












