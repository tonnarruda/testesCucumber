# language: pt

Funcionalidade: CATs (Acidentes de Trabalho)

  Cenário: Cadastro de CATs
    Dado que exista um colaborador "Geraldo", da area "administracao", com o cargo "desenvolvedor" e a faixa salarial "I"
    Dado que exista uma funcao "desenvolver" no cargo "desenvolvedor"
    Dado que exista um novo historico para o colaborador "Geraldo", na area "administracao", na faixa salarial "I", na funcao "desenvolver"

    Dado que eu esteja logado com o usuário "fortes"
    Quando eu acesso o menu "SESMT > Movimentações > Gerenciamento de Ordem de Serviço"
    Então eu devo ver o título "Gerenciamento de Ordem de Serviço"
    Então eu devo ver "Geraldo"

    E eu clico "linkFiltro"
    E eu seleciono "Todos" de "Situação"
    E eu preencho "Colaborador" com "artur"
    E eu clico no botão "Pesquisar"
    Então eu não devo ver "Geraldo"
    E eu clico "linkFiltro"
    E eu preencho "Colaborador" com "ger"
    E eu clico no botão "Pesquisar"
    Então eu devo ver "Geraldo"

    Então eu clico na ação "Ordens de Serviço(OS)" de "Geraldo"
    E eu devo ver o título "Ordens de Serviço - Geraldo"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Ordem de Serviço"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Ordens de Serviço - Geraldo"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Ordem de Serviço"
    E eu preencho o campo (JS) "dataOS" com "01/08/2016"
    E eu saio do campo "dataOS"
    E eu espero 2 segundo
    E eu preencho o campo (JS) "codigoCBOOS" com "808090"
    E eu preencho o campo (JS) "atividadesOS" com "ATIVIDADES DESENVOLVIDAS"
    E eu preencho o campo (JS) "riscosOS" com "RISCO DA OPERAÇÃO"
    E eu preencho o campo (JS) "episOS" com "EQUIPAMENTOS DE PROTEÇAO INDIVIDUAL"
    E eu preencho o campo (JS) "medidasPreventivasOS" com "MEDIDAS PREVENTIVAS"
    E eu preencho o campo (JS) "treinamentosOS" com "TREINAMENTO(S) NECESSÁRIO(S)"
    E eu preencho o campo (JS) "normasInternasOS" com "NORMAS INTERNAS"
    E eu preencho o campo (JS) "procedimentoEmCasoDeAcidenteOS" com "PROCEDIMENTO EM CASO DE ACIDENTE DE TRABALHO"
    E eu preencho o campo (JS) "informacoesAdicionaisOS" com "INFORMAÇÕES ADICIONAIS"
    E eu preencho o campo (JS) "termoDeResponsabilidadeOS" com "TERMO DE RESPONSABILIDADE"
    E eu clico no botão "Gravar"

    Então eu devo ver "desenvolver"
    E eu clico na ação "Visualizar" de "1"
    E eu devo ver o título "Ordem de Serviço"
    E eu devo ver "808090"
    E eu devo ver "ATIVIDADES DESENVOLVIDAS"
    E eu devo ver "RISCO DA OPERAÇÃO"
    E eu devo ver "EQUIPAMENTOS DE PROTEÇAO INDIVIDUAL"
    E eu devo ver "MEDIDAS PREVENTIVAS"
    E eu devo ver "TREINAMENTO(S) NECESSÁRIO(S)"
    E eu devo ver "NORMAS INTERNAS"
    E eu devo ver "PROCEDIMENTO EM CASO DE ACIDENTE DE TRABALHO"
    E eu devo ver "INFORMAÇÕES ADICIONAIS"
    E eu devo ver "TERMO DE RESPONSABILIDADE"
    E eu clico no botão "Voltar"

    Então eu devo ver "desenvolver"
    E eu clico na ação "Editar" de "1"
    E eu devo ver o título "Ordem de Serviço"
    E eu devo ver "808090"
    E eu devo ver "ATIVIDADES DESENVOLVIDAS"
    E eu devo ver "RISCO DA OPERAÇÃO"
    E eu devo ver "EQUIPAMENTOS DE PROTEÇAO INDIVIDUAL"
    E eu devo ver "MEDIDAS PREVENTIVAS"
    E eu devo ver "TREINAMENTO(S) NECESSÁRIO(S)"
    E eu devo ver "NORMAS INTERNAS"
    E eu devo ver "PROCEDIMENTO EM CASO DE ACIDENTE DE TRABALHO"
    E eu devo ver "INFORMAÇÕES ADICIONAIS"
    E eu devo ver "TERMO DE RESPONSABILIDADE"
    E eu clico no botão "Voltar"

    E eu clico na ação "Imprimir" de "1"
    E eu espero 1 segundo
    E eu devo ver o alert "Após a impressão não será permidito editar ou excluír esta ordem de serviço" e clico no Imprimir
    E eu espero 1 segundo

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Ordem de Serviço"
    E eu preencho o campo (JS) "dataOS" com "02/06/2016"
    E eu clico no botão "CarregarOrdemDeServico"
    E eu clico no botão "Gravar"

    Então eu devo ver o título "Ordens de Serviço - Geraldo"
    E eu clico em excluir "2"
    E eu devo ver o alert do confirmar e clico no ok
    E eu devo ver "Ordem de serviço excluída com sucesso"




