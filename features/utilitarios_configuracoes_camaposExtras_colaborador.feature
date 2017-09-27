# language: pt

Funcionalidade: Campos Extras para Colaborador

  @dev
  Cenário: Campos Extras para Colaborador
    Dado que exista a área organizacional "Financeiro"
    Dado que exista o cargo "Contador"
    Dado que exista a faixa salarial "I" no cargo "Contador"

    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "Info. Funcionais > Cadastros > Colaboradores"
    E eu clico no botão "Inserir"
    Então eu não devo ver a aba "EXTRA"

    Quando eu acesso o menu "Utilitários > Configurações > Campos Extras"
    Então eu devo ver o título "Configurações de Campos Extras"
    Quando eu marco "Habilitar campos extras no cadastro de Colaboradores"
    Quando eu marco "configuracaoCampoExtras[1].ativoColaborador"
    E eu preencho "configuracaoCampoExtras[1].titulo" com "Placa do veículo"
    E eu clico no botão "Gravar"
    Então eu devo ver "Essas configurações serão aplicadas para todas as empresas!"
    Quando eu aperto "OK"

    Quando eu acesso o menu "Info. Funcionais > Cadastros > Colaboradores"
    Quando eu clico no botão "Inserir"
    Quando eu preencho "Nome" com "_Zé da Silva"
    E eu preencho "Nome Comercial" com "Zé"
    E eu preencho o campo (JS) "Nascimento" com "01/01/1987"
    E eu preencho o campo (JS) "CPF" com "123.213.623-91"
    E eu preencho "Logradouro" com "Eretides"
    E eu preencho "num" com "11"
    E eu seleciono "CE" de "Estado"
    E eu seleciono "Fortaleza" de "Cidade"
    E eu preencho "E-mail" com "sam@gmai.com"
    E eu preencho "DDD" com "85"
    E eu preencho "Telefone" com "88438383"
    E eu seleciono "Analfabeto, inclusive o que, embora tenha recebido instrução, não se alfabetizou" de "Escolaridade"
    E eu clico "Documentos"
 	E eu preencho o campo (JS) "pis" com "12345678919"
    E eu clico na aba "DADOS FUNCIONAIS"
    E eu preencho o campo (JS) "Admissão" com "21/12/2010"
    E eu saio do campo "Admissão"
    E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
    E eu seleciono (JS) "1" de "areaOrganizacional"
    E eu seleciono "Contador I" de "Cargo/Faixa"
    E eu seleciono "Por valor" de "Salário Proposto"
    E eu preencho "Valor" com "100"
    Quando eu clico na aba "EXTRA"
    E eu preencho "Placa do veículo" com "hoz2549"
    E eu clico no botão "Gravar"
    Quando eu clico em editar "_Zé da Silva"
    Quando eu clico na aba "EXTRA"
    Entao o campo "Placa do veículo" deve conter "hoz2549"

    Quando eu clico no botão "Gravar"
    Entao eu devo ver "_Zé da Silva"

    Quando eu clico em excluir "_Zé da Silva"
    Então eu devo ver "Deseja realmente continuar?"
    Quando eu aperto "OK"
    Então eu devo ver "Colaborador excluído com sucesso."
    E eu não devo ver "_Zé da Silva Barbosa"

    Quando eu acesso o menu "Utilitários > Configurações > Campos Extras"
    Então eu devo ver o título "Configurações de Campos Extras"
    E eu preencho "configuracaoCampoExtras[1].titulo" com ""
    Quando eu desmarco "configuracaoCampoExtras[1].ativoColaborador"
    Quando eu desmarco "Habilitar campos extras no cadastro de Colaboradores"
    E eu clico no botão "Gravar"
    Então eu devo ver "Essas configurações serão aplicadas para todas as empresas!"
    Quando eu aperto "OK"

    Quando eu acesso o menu "Info. Funcionais > Cadastros > Colaboradores"
    E eu clico no botão "Inserir"
    Então eu não devo ver a aba "EXTRA"

