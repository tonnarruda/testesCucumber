# language: pt

Funcionalidade: Cadastrar Colaborador

  @dev
  Cenário: Cadastro Completo de Colaborador
     Dado que exista a área organizacional "Financeiro"
     Dado que exista a área organizacional "Compras", filha de "Financeiro"
     Dado que exista o cargo "Contador" na área organizacional "Compras"
     Dado que exista a faixa salarial "I" no cargo "Contador"
     Dado que eu esteja logado com o usuário "SOS"
    Então eu acesso o menu "Info. Funcionais > Cadastros > Colaboradores"
        E eu clico no botão "Inserir"
    Então eu preencho "Nome" com "Boris Johnson"
        E eu preencho "Nome Comercial" com "Boris Johnson"
        E eu preencho o campo (JS) "Nascimento" com "01/01/1987"
        E eu preencho o campo (JS) "CPF" com "123.213.623-91"
        E eu preencho "CEP" com "60811-690"
        E eu saio do campo "CEP"
        E eu espero 2 segundos
        E o campo "Logradouro" não deve conter "Rua Desembargador Floriano Benevides Magalhães"
        E o campo "Logradouro" deve conter "Rua Desembargador Floriano Benevides Mag"
        E eu preencho "CEP" com ""
        E eu preencho "Logradouro" com "Avenida João Pessoa"
        E eu preencho "num" com "4901"
        E eu preencho "Complemento" com "Apto 105"
        E eu seleciono "CE" de "Estado"
        E eu seleciono "Fortaleza" de "Cidade"
        E eu preencho "Bairro" com "Damas"
        E eu preencho "E-mail" com "email@teste.com.br"
        E eu preencho "DDD" com "85"
        E eu preencho "Telefone" com "88438383"
        E eu preencho "Celular" com "88438309"
        E eu seleciono "Doutorado completo" de "Escolaridade"
        E eu seleciono "Solteiro" de "Estado Civil"
        E eu seleciono "Reabilitado" de "Deficiência"
        E eu preencho "Nome do Pai" com "Tony Blair"
        E eu preencho "Nome da Mãe" com "Thereza May"
        E eu preencho "Nome do Cônjuge" com "Pilar Nuñes"
        E eu preencho "Qtd. Filhos" com "1"
    Então eu clico "Dados Funcionais"
        E eu preencho o campo (JS) "Admissão" com "21/12/2010"
        E eu saio do campo "Admissão"
        E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
        E eu seleciono (JS) "2" de "areaOrganizacional"
        E eu seleciono "Contador I" de "Cargo/Faixa"
        E eu seleciono "Por valor" de "Salário Proposto"
        E eu preencho "Valor" com "4000,00"
    Então eu clico na aba "FORMAÇÃO ESCOLAR"
        E eu clico no botão de Id "inserirFormacao"
        E eu preencho "formacao.curso" com "Mestrado em Contabilidade Pública"
        E eu preencho "Instituição de ensino" com "Universidade de Fortaleza"
        E eu seleciono "Doutorado" de "Tipo"
        E eu seleciono "Em andamento" de "Situação"
        E eu preencho "Conclusão" com "2017"
        E eu clico no botão de Id "frmFormacao_0"
        E eu clico no botão de Id "inserirIdioma"
        E eu seleciono "Inglês" de "Idioma"
        E eu seleciono "Avançado" de "Nível"
        E eu clico no botão de Id "frmIdioma_0"
        E eu preencho "Curso" com "Contabilidade Geral"
    Então eu clico no botão de Id "avancar"
        #E eu clico no botão de class "btnInserir grayBG"
#       E eu preencho "Empresa" com "Fortes Tecnologia"
#       E eu preencho campo pelo id "contatoEmpresa" com "8540051111"
#       E eu seleciono "Desenvolvedor" de "Cargo"
#       E eu preencho o campo (JS) "Data de Admissão" com "08/09/2008"
#       E eu preencho "Observações" com "Nada a declarar"
#       E eu clico no botão de Id "frmExperiencia_0"
        E eu clico no botão "Gravar"
        E eu devo ver "Colaborador Boris Johnson cadastrado com sucesso."

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Cadastro de Colaborador - Validar Campos Obrigatórios
     Dado que exista a área organizacional "Financeiro"
     Dado que exista a área organizacional "Compras", filha de "Financeiro"
     Dado que exista o cargo "Contador" na área organizacional "Compras"
     Dado que exista a faixa salarial "I" no cargo "Contador"
     Dado que eu esteja logado com o usuário "SOS"
    Então eu acesso o menu "Info. Funcionais > Cadastros > Colaboradores"
        E eu clico no botão "Inserir"
        E eu clico no botão "Gravar"
    Então eu devo ver o alert do confirmar e clico no ok
        E eu preencho "Nome" com "Boris Johnson"
        E eu clico no botão "Gravar"
    Então eu devo ver o alert do confirmar e clico no ok
        E eu preencho o campo (JS) "Nascimento" com "01/01/1987"
        E eu clico no botão "Gravar"
    Então eu devo ver o alert do confirmar e clico no ok
        E eu preencho o campo (JS) "CPF" com "123.213.623-91"
        E eu clico no botão "Gravar"
    Então eu devo ver o alert do confirmar e clico no ok
        E eu preencho "num" com "4901"
        E eu clico no botão "Gravar"
    Então eu devo ver o alert do confirmar e clico no ok
        E eu seleciono "CE" de "Estado"
        E eu seleciono "Fortaleza" de "Cidade"
        E eu clico no botão "Gravar"
    Então eu devo ver o alert do confirmar e clico no ok
        E eu preencho "Telefone" com "88438383"
        E eu preencho "E-mail" com "email@teste.com.br"
        E eu clico no botão "Gravar"
    Então eu devo ver o alert do confirmar e clico no ok
        E eu seleciono "Doutorado completo" de "Escolaridade"
        E eu clico no botão "Gravar"
    Então eu devo ver o alert do confirmar e clico no ok
    Então eu clico "Dados Funcionais"
        E eu preencho o campo (JS) "Admissão" com "21/12/2010"
        E eu saio do campo "Admissão"
        E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
        E eu seleciono (JS) "2" de "areaOrganizacional"
        E eu seleciono "Contador I" de "Cargo/Faixa"
        E eu seleciono "Por valor" de "Salário Proposto"
        E eu preencho "Valor" com "4000,00"
    Então eu clico no botão "Cancelar"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Editar Cadastro de Colaborador
     Dado que exista um colaborador "Theresa May", da area "Financeiro", com o cargo "Contador" e a faixa salarial "I"
     Dado que eu esteja logado com o usuário "SOS"
    Então eu acesso o menu "Info. Funcionais > Cadastros > Colaboradores"
        E eu clico em editar "Theresa May"
        E eu preencho "Nome Comercial" com "Thereza May"
    Então eu clico "Dados Funcionais"
        E eu seleciono "Por valor" de "Salário Proposto"
        E eu preencho "Valor" com "4000,00"
        E eu clico no botão "Gravar"
        E eu devo ver "Colaborador Theresa May alterado com sucesso."

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Editar Histórico de Colaborador
     Dado que exista um colaborador "Theresa May", da area "Financeiro", com o cargo "Contador" e a faixa salarial "I"
     Dado que eu esteja logado com o usuário "SOS"
    Então eu acesso o menu "Info. Funcionais > Cadastros > Colaboradores"
        E eu clico na linha "Theresa May" da imagem "Visualizar Progressão"
        E eu clico no botão "EditarHistoricos"
        E eu clico em editar "01/07/2011"
        E eu preencho "Valor" com "2000,00"
        E eu clico no botão "Gravar"
    Então eu clico no botão "Inserir"
        E eu preencho o campo (JS) "Data" com "01/12/2015"
        E eu preencho "Valor" com "4000,00"
        E eu clico no botão "Gravar"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Excluir Histórico de Colaborador
     Dado que exista um colaborador "Theresa May", da area "Financeiro", com o cargo "Contador" e a faixa salarial "I"
     Dado que eu esteja logado com o usuário "SOS"
    Então eu acesso o menu "Info. Funcionais > Cadastros > Colaboradores"
        E eu clico na linha "Theresa May" da imagem "Visualizar Progressão"
        E eu clico no botão "EditarHistoricos"
    Então eu clico no botão "Inserir"
        E eu preencho o campo (JS) "Data" com "01/12/2015"
        E eu preencho "Valor" com "4000,00"
        E eu clico no botão "Gravar"
        E eu clico em excluir "01/12/2015"
        E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Situação excluída com sucesso."

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Criar Usuário e Senha de Colaborador
     Dado que exista um colaborador "Theresa May", da area "Financeiro", com o cargo "Contador" e a faixa salarial "I"
     Dado que eu esteja logado com o usuário "SOS"
    Então eu acesso o menu "Info. Funcionais > Cadastros > Colaboradores"
        E eu clico na linha "Theresa May" da imagem "Criar Acesso ao Sistema"
        E eu preencho "Nome" com "Theresa May"
        E eu preencho "Login" com "may"
        E eu preencho "Senha" com "1234"
        E eu preencho "Confirmar Senha" com "1234"
        E eu clico no botão "Gravar"
    Então eu acesso o menu "Info. Funcionais > Cadastros > Colaboradores"
        E eu clico na linha "Theresa May" da imagem "Editar Acesso ao Sistema"
        E o campo "Nome" deve conter "Theresa May"
        E o campo "Login" deve conter "may"
        E eu clico no botão "Cancelar"        

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Desligar Colaborador e cancelar desligamento
     Dado que exista um colaborador "Theresa May", da area "Financeiro", com o cargo "Contador" e a faixa salarial "I"
    Dado que exista um motivo de desligamento "Redução de Quadro"
     Dado que eu esteja logado com o usuário "SOS"
    Então eu acesso o menu "Info. Funcionais > Cadastros > Colaboradores"
        E eu clico na linha "Theresa May" da imagem "Desligar colaborador"
        E eu preencho "Data de Desligamento" com "13/09/2016"
        E eu seleciono "Redução de Quadro" de "Motivo do Desligamento"
        E eu preencho "Observações" com "Colaborador com salario muito alto para os padrões da empresa"
        E eu clico no botão "DesligarColaborador"
        E eu devo ver o alert "Confirma desligamento" e clico no ok
        E eu devo ver "Colaborador desligado com sucesso"
    Entao eu clico "linkFiltro"
        E eu seleciono "Todos" de "Situação"
        E eu clico no botão "Pesquisar"
    Então eu clico na linha "Theresa May" da imagem "Colaborador já desligado"
        E eu clico no botão "CancelarDesligamento"
        E eu devo ver o alert "Tem certeza que deseja cancelar o desligamento" e clico no ok
    Então eu devo ver "Colaborador religado com sucesso"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Excluir Colaborador
     Dado que exista um colaborador "Theresa May", da area "Financeiro", com o cargo "Contador" e a faixa salarial "I"
    Dado que exista um motivo de desligamento "Redução de Quadro"
     Dado que eu esteja logado com o usuário "SOS"
    Então eu acesso o menu "Info. Funcionais > Cadastros > Colaboradores"
        E eu clico em excluir "Theresa May"
    Então eu devo ver "Deseja realmente continuar?"
        E eu aperto "OK"
    Então eu devo ver "Colaborador excluído com sucesso."

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Emissão de Relatório Listagem de Colaborador
     Dado que exista um colaborador "Theresa May", da area "Financeiro", com o cargo "Contador" e a faixa salarial "I"
     Dado que exista um motivo de desligamento "Redução de Quadro"
     Dado que eu esteja logado com o usuário "SOS"
    Então eu acesso o menu "Info. Funcionais > Cadastros > Colaboradores"
    Então eu clico no botão "ListagemColaborador"
        E eu devo ver o título "Listagem de Colaboradores"
        E eu seleciono "Empresa Padrão" de "Empresa"
        E eu marco "Empresa Padrão - Estabelecimento Padrão"
        E eu marco "Empresa Padrão - Financeiro"
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

#------------------------------------------------------------------------------------------------------------------------
  @teste
  Cenário: Pesquisar Colaboradores
     Dado que exista um colaborador "Theresa May", da area "Financeiro", com o cargo "Contador" e a faixa salarial "I"
     Dado que exista um colaborador "Tony Blair", da area "Desenvolvimento", com o cargo "Diretor de Produto" e a faixa salarial "II"
     Dado que exista um colaborador "Ellen Pompeo", da area "Suporte", com o cargo "Gerente de Suporte" e a faixa salarial "4F"
     Dado que exista um motivo de desligamento "Redução de Quadro"
     Dado que eu desligue o colaborador "Ellen Pompeo" na data "25/04/2017" com motivo de desligamento "Redução de Quadro"
     Dado que eu esteja logado com o usuário "SOS"
    Então eu acesso o menu "Info. Funcionais > Cadastros > Colaboradores"
        #E eu clico "Exibir Filtro"
        E eu seleciono "Desligado" de "Situação"
        E eu clico no botão "Pesquisar"
        E eu devo ver "Ellen Pompeo"
        E eu preencho "Nome" com "Ellen Pompeo"
        E eu seleciono "Ativo" de "Situação"
        E eu clico no botão "Pesquisar"
        E eu devo ver "Não existem colaboradores a serem listados."
        E eu preencho "Nome" com ""
        E eu seleciono "Ativo" de "Situação"
        E eu seleciono "Financeiro" de "Área Organizacional"
        E eu seleciono "Contador" de "Cargo"
        E eu clico no botão "Pesquisar"
        E eu devo ver "Theresa May"

    
    
    
    
    
    
    
    
    
    
    
    
    


