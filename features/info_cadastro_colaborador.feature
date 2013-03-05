# language: pt

Funcionalidade: Cadastrar Colaborador

  @dev
  Cenário: Cadastro de Colaborador
    Dado que exista a área organizacional "Financeiro"
    Dado que exista a área organizacional "Compras", filha de "Financeiro"
    Dado que exista o cargo "Contador" na área organizacional "Compras"
    Dado que exista a faixa salarial "I" no cargo "Contador"
    Dado que exista um conhecimento "testes"
    Dado que exista um conhecimento "java"
    Dado que exista um conhecimento "testes" na area organizacional "Compras"
    Dado que exista um conhecimento "java" na area organizacional "Compras"
    Dado que exista um conhecimento "testes" no cargo "Contador"
    Dado que exista um conhecimento "java" no cargo "Contador"
    Dado que exista um nivel de competencia "ruim" com a ordem 1
    Dado que exista um nivel de competencia "regular" com a ordem 2
    Dado que exista um nivel de competencia "bom" com a ordem 3

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
    Quando eu preencho "Nome" com "Silva"
    E eu preencho "Nome Comercial" com "Zé"
    E eu preencho o campo (JS) "Nascimento" com "01/01/1987"
    E eu preencho o campo (JS) "CPF" com "123.213.623-91"
    E eu preencho "Logradouro" com "Eretides"
    E eu preencho "num" com "11"
    E eu preencho "Complemento" com "apto"
    E eu seleciono "CE" de "Estado"
    E eu seleciono "Fortaleza" de "Cidade"
    E eu preencho "Bairro" com "Nossa Senhora da Apresentação"
    E eu saio do campo "Bairro"
    E eu preencho "E-mail" com "f@fortes.com.br"

    E eu preencho "DDD" com "85"
    E eu preencho "Telefone" com "88438383"
    E eu preencho "Celular" com "88438309"
    E eu seleciono "Sem escolaridade" de "Escolaridade"
    E eu clico "Dados Funcionais"
    E eu preencho o campo (JS) "Admissão" com "21/12/2010"
    E eu saio do campo "Admissão"
    E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
    E eu seleciono "Financeiro" de "Área Organizacional"
    Então eu devo ver o alert "Não é possível alocar colaboradores em áreas que possuem sub-áreas." e clico no ok

    Então eu seleciono "Financeiro > Compras" de "Área Organizacional"
    E eu seleciono "Contador I" de "Cargo/Faixa"
    E eu seleciono "Por valor" de "Salário Proposto"
    E eu preencho "Valor" com "100"
    E eu clico no botão "Gravar"
    E eu devo ver "cadastrado com sucesso"
    E eu devo ver "Colaboradores"
    Então eu devo ver "Silva"

    Quando eu clico em editar "Silva"
    Entao o campo "Nome" deve conter "Silva"

    Dado que exista a área organizacional "Almoxarifado", filha de "Compras"
    Quando eu clico no botão "Gravar"
    Então eu devo ver "Colaborador não pode ser inserido em áreas que possuem sub-áreas."
    
    Quando eu preencho "Nome" com "Silva Barbosa"
    E eu preencho o campo (JS) "CEP" com "60320-104"
    E eu saio do campo "CEP"
    E eu espero o campo "Logradouro" ficar habilitado
    Então o campo "Logradouro" deve conter "Rua Haroldo Torres - de 501/502 a 1"
    E o campo "Estado" deve ter "CE" selecionado
    E o campo "cidade" deve ter "Fortaleza" selecionado
    E o campo "bairroNome" deve conter "São Gerardo"

    Então eu clico "Dados Funcionais"
    E eu seleciono "Financeiro > Compras > Almoxarifado" de "Área Organizacional"
    Quando eu clico no botão "Gravar"
    Então eu devo ver "editado com sucesso"
    E eu devo ver "Silva Barbosa"
    
    Então eu clico na linha "Silva Barbosa" da imagem "Competências"
    E eu devo ver "Competências do Colaborador"
    E eu clico no botão "Inserir"
    E eu devo ver "Competências do Colaborador"
    E eu devo ver "Colaborador: Silva Barbosa"
    E eu marco "java"
    E eu escolho "niveisCompetenciaFaixaSalariais[0].nivelCompetencia.id"
    E eu marco "testes"
    E eu clico no botão "Gravar"
    E eu devo ver o alert "Selecione os níveis para as competências indicadas." e clico no ok
    E eu escolho "niveisCompetenciaFaixaSalariais[1].nivelCompetencia.id"
    E eu clico no botão "Gravar"
    E eu devo ver "Níveis de Competência do Colaborador salvos com sucesso."
    E eu clico no botão "Voltar"
    E eu devo ver "Competências do Colaborador"
    E eu clico no botão "Voltar"
    E eu devo ver "Colaboradores"

    Quando eu clico em excluir "Silva Barbosa"
    Então eu devo ver "Confirma exclusão?"
    Quando eu aperto "OK"
    Então eu devo ver "Colaborador excluído com sucesso."
    E eu não devo ver "Silva Barbosa"
    
    Então eu clico no botão "ListagemColaborador"
    E eu devo ver o título "Listagem de Colaboradores"
    E eu seleciono "Empresa Padrão" de "Empresa"
    E eu marco "Empresa Padrão - Estabelecimento Padrão"
    E eu marco "Empresa Padrão - Financeiro"
    E eu marco "Empresa Padrão - Financeiro > Compras"
    E eu clico no botão "Relatorio"
    Então eu devo ver o alert "Por favor selecione os campos para impressão." e clico no ok
    
    Então eu preencho "Título" com "colaboradores"
    E eu seleciono "Nome" de "from_colunas"
    E eu aperto "b_to_colunas"
    E eu seleciono "Matrícula" de "from_colunas"
    E eu aperto "b_to_colunas"
    E eu seleciono "Área Organizacional" de "from_colunas"
    E eu aperto "b_to_colunas"
    E eu seleciono "Data Admissão" de "from_colunas"
    E eu aperto "b_to_colunas"
    E eu seleciono "Cargo Atual" de "from_colunas"
    E eu aperto "b_to_colunas"
    E eu seleciono "Estado Civil" de "from_colunas"
    E eu aperto "b_to_colunas"
    E eu seleciono "Nome da Mãe" de "from_colunas"
    E eu aperto "b_to_colunas"
    E eu devo ver "Limite de campos para o relatório em PDF foi excedido."
    E eu seleciono "Nome do Pai" de "from_colunas"
    E eu aperto "b_to_colunas"

    Então eu clico na imagem com o título "Salvar campo do relatório"
    E eu devo ver o alert "Layout do relatório salvo com sucesso." e clico no ok


