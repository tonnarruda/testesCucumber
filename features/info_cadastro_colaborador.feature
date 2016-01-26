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
    Dado que exista uma solicitacao "Solicitacao1" para área "Compras" na faixa "I"
    Dado que exista uma solicitacao "Solicitacao2" para área "Compras" na faixa "I"
    Dado que exista um motivo de desligamento "Porque eu quero"
    Dado que exista uma connfiguracao de nivel de competencia da faixa salarial "I" na data "01/01/2015"
    Dado que exista uma connfiguracao de nivel de competencia "bom" no conhecimento "java" para connfiguracao de nivel de competencia da faixa salarial na data "01/01/2015"
    Dado que exista uma connfiguracao de nivel de competencia "bom" no conhecimento "testes" para connfiguracao de nivel de competencia da faixa salarial na data "01/01/2015"

    Dado que eu esteja logado com o usuário "fortes"

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
    E eu seleciono "Analfabeto, inclusive o que, embora tenha recebido instrução, não se alfabetizou" de "Escolaridade"
    E eu clico "Dados Funcionais"
    E eu preencho o campo (JS) "Admissão" com "21/12/2010"
    E eu saio do campo "Admissão"
    E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
    E eu seleciono (JS) "2" de "areaOrganizacional"
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
    E eu espero 4 segundos
    Então o campo "Logradouro" deve conter "Rua Haroldo Torres - de 501"
    E o campo "Estado" deve ter "CE" selecionado
    E o campo "cidade" deve ter "Fortaleza" selecionado
    E o campo "bairroNome" deve conter "São Gerardo"

    Então eu clico "Dados Funcionais"
    E eu seleciono (JS) "3" de "areaOrganizacional"
    Quando eu clico no botão "Gravar"
    Então eu devo ver "alterado com sucesso."
    E eu devo ver "Silva Barbosa"

    Então eu clico na linha "Silva Barbosa" da imagem "Visualizar Progressão"
    E eu devo ver "Estabelecimento Padrão"
    E eu devo ver "Financeiro > Compras > Almoxarifado"
    E eu devo ver "100,00"
    E eu clico no botão "EditarHistoricos"

    Entao eu devo ver "Editar Situações do Colaborador - Silva Barbosa"
    E eu devo ver "Estabelecimento Padrão"
    E eu devo ver "Financeiro > Compras > Almoxarifado"
    E eu devo ver "100,00"

    E eu clico em editar "21/12/2010"
    E eu preencho "Valor:" com "200,00"
    E eu clico no botão "Gravar"

    Então eu devo ver "Editar Situações do Colaborador - Silva Barbosa"
    E eu devo ver "Estabelecimento Padrão"
    E eu devo ver "Financeiro > Compras > Almoxarifado"
    E eu devo ver "200,00"

    E eu clico em editar "21/12/2010"
    E o campo "Valor:" deve conter "200,00"
    E eu clico no botão "Cancelar"

    Então eu devo ver "Editar Situações do Colaborador - Silva Barbosa"
    E eu devo ver "Estabelecimento Padrão"
    E eu devo ver "Financeiro > Compras > Almoxarifado"
    E eu devo ver "200,00"
    E eu clico no botão "Voltar"

    Entao eu devo ver "Estabelecimento Padrão"
    E eu devo ver "Financeiro > Compras > Almoxarifado"
    E eu devo ver "200,00"
    E eu clico no botão "Voltar"

    Então eu clico na linha "Silva Barbosa" da imagem "Performance Profissional"
    E eu devo ver "Nome: Silva Barbosa"
    E eu devo ver "Admissão: 21/12/2010"
    E eu devo ver "Cargo Atual: Contador"
    E eu devo ver "Email: f@fortes.com.br"
    E eu devo ver "Estado Civil: Casado - Comunhão Parcial"
    E eu devo ver "Escolaridade: Analfabeto, inclusive o que, embora tenha recebido instrução, não se alfabetizou"
    E eu devo ver "Bairro: São Gerardo"
    E eu devo ver "Endereço: Rua Haroldo Torres - de 501"
    E eu devo ver "Cidade/Estado: Fortaleza / CE"
    E eu devo ver "CEP: 60.320-104"
    E eu devo ver "Telefone: (85) 8843-8383"
    E eu devo ver "Celular: (85) 8843-8309"
    E eu devo ver "Deficiência: Sem Deficiência"
    E eu devo ver "Vínculo: Emprego"

    Entao eu clico no botão "ImprimirPdf"
    E eu espero 1 segundos
    Entao eu clico no botão "Voltar"

    Então eu clico na linha "Silva Barbosa" da imagem "Incluir em Solicitação"
    E eu marco "1 - Solicitacao1 - Contador - - Compras"
    E eu marco "2 - Solicitacao2 - Contador - - Compras"
    E eu clico no botão "Inserir"
    Entao eu devo ver "Candidato incluído com sucesso"
    E eu clico no botão "Voltar"

    Então eu clico na linha "Silva Barbosa" da imagem "Documentos do Colaborador"
    Entao eu devo ver "Documentos do Colaborador - Silva Barbosa"

    Entao eu clico no botão "Inserir"
    E eu devo ver "Novo Documento do Colaborador - Silva Barbosa"
    E eu preencho "Descrição" com "Novo documento"
    E eu preencho "Data" com "13/09/2013"
    E eu preencho "Observação" com "Currículo Atualizado"
    E eu clico no botão "Gravar"
    E eu devo ver o alert do confirmar e clico no ok
    E eu clico no botão "Cancelar"
    Entao eu devo ver "Documentos do Colaborador - Silva Barbosa"
    E eu clico no botão "Cancelar"

    Então eu clico na linha "Silva Barbosa" da imagem "Criar Acesso ao Sistema"
    E eu preencho "Nome" com "Silva Barbosa"
    E eu preencho "Login" com "Silva"
    E eu preencho "Senha" com "1234"
    E eu preencho "Confirmar Senha" com "1234"
    E eu clico no botão "Gravar"

    Então eu clico na linha "Silva Barbosa" da imagem "Editar Acesso ao Sistema"
    E o campo "Nome" deve conter "Silva Barbosa"
    E o campo "Login" deve conter "Silva"
    E eu clico no botão "Cancelar"

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
    E eu devo ver o alert "Selecione um avaliador" e clico no ok
    E eu seleciono "Anônimo" de "Avaliador"
    E eu clico no botão "Gravar"
    E eu devo ver "ncia do colaborador salvos com sucesso"
    E eu clico no botão "Voltar"
    E eu devo ver "Colaboradores"

    Então eu clico na linha "Silva Barbosa" da imagem "Desligar colaborador"
    E eu clico no botão "Voltar"

    Então eu clico na linha "Silva Barbosa" da imagem "Desligar colaborador"
    E eu devo ver o título "Desligar Colaborador"
    E eu preencho "Data de Desligamento" com "13/09/2013"
    E eu seleciono "Porque eu quero" de "Motivo do Desligamento"
    E eu preencho "Observações" com "massa"
    E eu clico no botão "DesligarColaborador" 
    E eu devo ver o alert "Confirma desligamento" e clico no ok
    E eu devo ver "Colaborador desligado com sucesso"

    Entao eu clico "linkFiltro"
    E eu seleciono "Todos" de "Situação"
    E eu clico no botão "Pesquisar"

    Então eu clico na linha "Silva Barbosa" da imagem "Colaborador já desligado"
    E eu clico no botão "ImprimirPdf"
    E eu espero 1 segundos
    E eu clico no botão "CancelarDesligamento"
    E eu devo ver o alert "Tem certeza que deseja cancelar o desligamento" e clico no ok
    E eu devo ver "Colaborador religado com sucesso"

    Quando eu clico em excluir "Silva Barbosa"
    Então eu devo ver "Deseja realmente continuar?"
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