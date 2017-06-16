# language: pt

Funcionalidade: Relatório de Colaboradores

Cenário: Emissão de Relatório Listagem de Colaborador
     Dado que exista um colaborador "Theresa May", da area "Financeiro", com o cargo "Contador" e a faixa salarial "I"
     Dado que exista um motivo de desligamento "Redução de Quadro"
     Dado que eu esteja logado com o usuário "SOS"
    Então eu acesso o menu "Info. Funcionais > Relatórios > Listagem de Colaboradores"
        E eu seleciono "Empresa Padrão" de "Empresa"
        E eu marco "Empresa Padrão - Estabelecimento Padrão"
        E eu marco "Empresa Padrão - Financeiro"
        E eu seleciono "Desligado" de "situacao"        
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