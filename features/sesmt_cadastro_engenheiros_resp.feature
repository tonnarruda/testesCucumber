# language: pt

Funcionalidade: Engenheiros Responsáveis do Trabalho

  Cenário: Cadastro de Engenheiros Responsáveis do Trabalho
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Engenheiros Responsáveis"
    Então eu devo ver o título "Engenheiros Responsáveis do Trabalho"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Engenheiro do Trabalho"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"

    Então eu devo ver o título "Engenheiros Responsáveis do Trabalho"
    E eu clico no botão "Inserir"
    E eu preencho "Nome" com "_jose"
    E eu preencho o campo (JS) "inicio" com "01/01/2011"
    E eu preencho o campo (JS) "fim" com "01/07/2011"
    E eu preencho "CREA" com "123"
    E eu preencho "NIT" com "1234567891234560123456"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Engenheiros Responsáveis do Trabalho"
    Então eu devo ver "_jose"

    Entao eu clico em editar "_jose"
    E eu devo ver o título "Editar Engenheiro do Trabalho"
    E o campo "Nome" deve conter "_jose"
    E o campo "NIT" deve conter "123456789123456"
    E eu preencho "Nome" com "_joao"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Engenheiros Responsáveis do Trabalho"
    Então eu devo ver "_joao"

    Então eu clico em excluir "_joao"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Engenheiro responsável excluído com sucesso"
    Então eu não devo ver na listagem "_joao"












