# language: pt

Funcionalidade: Cadastrar Estabelecimento

  Cenário: Empresa
    Dado que eu esteja logado com o usuário "fortes"

    Quando eu acesso o menu "Utilitários > Cadastros > Estabelecimentos"
    Então eu devo ver o título "Estabelecimentos"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Estabelecimento"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do confirmar e clico no ok
	E eu preencho campo pelo id "complementoCnpj" com "0002"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Estabelecimentos"

    E eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Estabelecimento"
    E eu preencho "Nome" com "Fortes"
    E eu preencho campo pelo id "complementoCnpj" com "0002"
    E eu preencho o campo (JS) "CEP" com "60320-104"
    E eu saio do campo "CEP"
    E eu espero 4 segundos
    Então o campo "Logradouro" deve conter "Rua Haroldo Torres - de 501"
    E o campo "Estado" deve ter "CE" selecionado
    E o campo "cidade" deve ter "Fortaleza" selecionado
    E o campo "bairroNome" deve conter "São Gerardo"
    E eu preencho "Nº" com "330"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Estabelecimentos"

    Quando eu clico em editar "Fortes"
    Então o campo "Nome" deve conter "Fortes"
    Quando eu preencho "Nome" com "Ente"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Estabelecimentos"

    Então eu clico em excluir "Ente"
    E eu devo ver o alert "Confirma exclusão?" e clico no ok
    E eu devo ver "Estabelecimento excluído com sucesso."
    Então eu não devo ver na listagem "Ente"
    Então eu acesso o menu "Sair"
    