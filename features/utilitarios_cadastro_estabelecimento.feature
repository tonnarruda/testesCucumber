# language: pt

Funcionalidade: Cadastrar Estabelecimento

  @dev
  Cenário: Cadastro de Estabelecimento
    Dado que eu esteja logado

    Quando eu acesso o menu "Utilitários > Cadastros > Estabelecimentos"
    Então eu devo ver o título "Cadastro de Estabelecimentos"
    Quando eu clico no botão "Inserir"
    Então eu devo ver o título "Novo Estabelecimento"
    E eu clico no botão "Gravar"
    Quando eu aperto "OK"
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Cadastro de Estabelecimentos"
    Quando eu clico no botão "Inserir"
    Então eu devo ver o título "Novo Estabelecimento"
    Quando eu preencho "Nome" com "_Estabelecimento de Teste"
    E eu preencho "complementoCnpj" com "0002"
    E eu preencho "Logradouro" com "Eretides"
    E eu preencho "num" com "11"
    E eu preencho "Complemento" com "apt 101"
    E eu seleciono "CE" de "Estado"
    E eu seleciono "Fortaleza" de "Cidade"
    E eu preencho "Bairro" com "Nossa Senhora da Apresentação"
    E eu clico no botão "Gravar"
    Então eu devo ver "Cadastro de Estabelecimentos"
    E eu devo ver "_Estabelecimento de Teste"

    Quando eu clico em editar "_Estabelecimento de Teste"
    E o campo "Nome" deve conter "_Estabelecimento de Teste"
    E eu preencho "Nome" com "_Estabelecimento de Teste 2"
    E eu preencho "CEP" com "60320-100"
    E eu saio do campo "CEP"
    E eu espero o campo "Logradouro" ficar habilitado
    E o campo "Logradouro" deve conter "Rua Haroldo Torres"
    E o campo "Estado" deve ter "CE" selecionado
    E o campo "Cidade" deve ter "Fortaleza" selecionado
    E o campo "Bairro" deve conter "São Gerardo"
    E eu clico no botão "Gravar"
    E eu devo ver "_Estabelecimento de Teste 2"

    Quando eu clico em excluir "_Estabelecimento de Teste 2"
    Então eu devo ver "Confirma exclusão?"
    Quando eu aperto "OK"
    Então eu devo ver "Estabelecimento excluído com sucesso."
    E eu não devo ver "_Estabelecimento de Teste 2"
