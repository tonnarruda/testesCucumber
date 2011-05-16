# language: pt

Funcionalidade: Cadastrar Colaborador

  @dev
  Cenário: Cadastro de Colaborador
    Dado que eu esteja logado

    Quando eu acesso o menu "Info. Funcionais > Cadastros > Colaboradores"
    Então eu devo ver o título "Colaboradores"
    Quando eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Colaborador"
    E eu clico no botão "Gravar"
    Quando eu aperto "OK"
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Colaboradores"
    Quando eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Colaborador"
    Quando eu preencho "Nome" com "_Zé da Silva"
    E eu preencho "Nome Comercial" com "Zé"
    E eu preencho "Nascimento" com "01/01/1987"
    E eu preencho "CPF" com "123.213.623-91"
    E eu preencho "Logradouro" com "Eretides"
    E eu preencho "num" com "11"
    E eu preencho "Complemento" com "apto"
    E eu seleciono "CE" de "Estado"
    E eu seleciono "Fortaleza" de "Cidade"
    E eu preencho "Bairro" com "Nossa Senhora da Apresentação"
    E eu saio do campo "Bairro"
    E eu preencho "E-mail" com "franciscobarroso@grupofortes.com.br"

    E eu preencho "DDD" com "85"
    E eu preencho "Telefone" com "88438383"
    E eu preencho "Celular" com "88438309"
    E eu seleciono "Sem escolaridade" de "Escolaridade"
    E eu clico "Dados Funcionais"
    E eu preencho "Admissão" com "21/12/2010"
    E eu preencho "Data" com "21/12/2010"
    E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
    E eu seleciono "Administração > Desenvolvimento" de "Área Organizacional"
    E eu seleciono "Analista Senior" de "Cargo/Faixa"
    E eu seleciono "Por valor" de "Salário Proposto"
    E eu preencho "Valor" com "100"
    E eu clico no botão "Gravar"
    Então eu devo ver "cadastrado com sucesso"
    Então eu devo ver "Colaboradores"
    E eu devo ver "_Zé da Silva"

    Quando eu clico em editar "_Zé da Silva"
    E o campo "Nome" deve conter "_Zé da Silva"
    E eu preencho "Nome" com "_Zé da Silva Barbosa"
    E eu preencho "CEP" com "60320-100"
    E eu saio do campo "CEP"
    E eu espero 4 segundos
    E o campo "Logradouro" deve conter "Rua Haroldo Torres"
    E o campo "Estado" deve ter "CE" selecionado
    E o campo "cidade" deve ter "Fortaleza" selecionado
    E o campo "bairroNome" deve conter "São Gerardo"
    E eu clico no botão "Gravar"
    E eu devo ver "_Zé da Silva Barbosa"

    Quando eu clico em excluir "_Zé da Silva Barbosa"
    Então eu devo ver "Confirma exclusão?"
    Quando eu aperto "OK"
    Então eu devo ver "Colaborador excluído com sucesso."
    E eu não devo ver "_Zé da Silva Barbosa"

