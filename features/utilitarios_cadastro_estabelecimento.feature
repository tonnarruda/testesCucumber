# language: pt

Funcionalidade: Cadastrar Estabelecimento

  Cenário: Cadastro de Estabelecimento
    Dado que eu esteja logado

    Quando eu acesso o menu "Utilitários > Cadastros > Estabelecimentos"
    Então eu devo ver o título "Cadastro de Estabelecimentos"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Estabelecimento"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert "Complemento do CNPJ deve ter 4 dígitos." e clico no ok
    E eu clico no botão "Cancelar"

    Então eu devo ver o título "Cadastro de Estabelecimentos"
    E eu clico no botão "Inserir"
    E eu preencho "Nome" com "matriz"
    E eu preencho "complementoCnpj" com "0001"
    E eu preencho o campo (JS) "CEP" com "60140-140"
    E eu saio do campo "CEP"
    E eu espero 5 segundos
    E eu preencho "num" com "123"
    E eu preencho "Complemento" com "sala 1001"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Cadastro de Estabelecimentos"
    Então eu devo ver "matriz"

    Entao eu clico em editar "matriz"
    E eu devo ver o título "Editar Estabelecimento"
    E o campo "Nome" deve conter "matriz"
    E eu preencho "Nome" com "filial"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Cadastro de Estabelecimentos"
    Então eu devo ver "filial"

    Então eu clico em excluir "filial"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Estabelecimento excluído com sucesso."
    Então eu não devo ver na listagem "filial"