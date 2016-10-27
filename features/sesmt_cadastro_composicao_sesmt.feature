# language: pt

Funcionalidade: Composição do SESMT

  Cenário: Cadastro de Composição do SESMT
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "SESMT > Cadastros > Composição do SESMT"
    Então eu devo ver o título "Composição do SESMT"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Composição do SESMT"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"

    Então eu devo ver o título "Composição do SESMT"
    E eu clico no botão "Inserir"
    E eu preencho o campo (JS) "A partir de" com "20/01/2012"
    E eu preencho "Qtd. de Técnicos de Segurança:" com "1"
    E eu preencho "Qtd. de Engenheiros de Segurança:" com "1"
    E eu preencho "Qtd. de Auxiliares de Enfermagem:" com "1"
    E eu preencho "Qtd. de Enfermeiros:" com "1"
    E eu preencho "Qtd. de Médicos:" com "1"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Composição do SESMT"
    Então eu devo ver "20/01/2012"

    Entao eu clico em editar "20/01/2012"
    E eu devo ver o título "Editar Composição do SESMT"
    E o campo "A partir de" deve conter "20/01/2012"
    E eu preencho o campo (JS) "A partir de" com "21/01/2012"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Composição do SESMT"
    Então eu devo ver "21/01/2012"

    Então eu clico em excluir "21/01/2012"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Composição do SESMT excluído com sucesso."
    Então eu não devo ver na listagem "21/01/2012"

