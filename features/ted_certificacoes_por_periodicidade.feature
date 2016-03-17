# language: pt

Funcionalidade: Cadastrar Certificações dos Cursos

  Cenário: Certificações dos Cursos
    Dado que exista um curso "tdd"
    Dado que eu esteja logado com o usuário "fortes"
    Dado que exista um colaborador "Jose", da area "area1", com o cargo "cargo1" e a faixa salarial "faixa1"

    Quando eu acesso o menu "Utilitários > Cadastros > Empresas"
    Entao eu clico em editar "Empresa Padrão"
    E eu preencho "Email Resp. RH" com "sl@teste.com"
    E eu seleciono "CE" de "Estado"
    E eu seleciono "Periodicidade da certificação" de "Controlar vencimento da certificação por"
    E eu clico no botão "Gravar"

    Quando eu acesso o menu "T&D > Cadastros > Avaliação Prática"
    Então eu devo ver o título "Avaliação Prática"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Avaliação Prática"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"

    Então eu devo ver o título "Avaliação Prática"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Avaliação Prática"
    E eu preencho "Título" com "Pular Sorda"
    E eu preencho "Nota Mínima para Aprovação" com "5"
    E eu clico no botão "Gravar"

    Então eu devo ver o título "Avaliação Prática"
    E eu clico em editar "Pular Sorda"
    E o campo "Título" deve conter "Pular Sorda"
    E o campo "Nota Mínima para Aprovação" deve conter "5"
    E eu preencho "Título" com "Pular Corda"
    E eu preencho "Nota Mínima para Aprovação" com "8"
    E eu clico no botão "Gravar"

    Então eu devo ver o título "Avaliação Prática"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Avaliação Prática"
    E eu preencho "Título" com "Av Pratica Excluir"
    E eu preencho "Nota Mínima para Aprovação" com "0"
    E eu clico no botão "Gravar"

    Então eu devo ver o título "Avaliação Prática"
    E eu clico em excluir "Av Pratica Excluir"
    E eu devo ver o alert do confirmar e clico no ok
    E eu devo ver "Avaliação prática excluída com sucesso"

    Quando eu acesso o menu "T&D > Cadastros > Certificações"
    Então eu devo ver o título "Certificações"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Certificação"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Certificações"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Certificação"
    E eu preencho "Periodicidade em meses" com "3"
    E eu devo ver "Avaliações Práticas"
    E eu marco "Pular Corda"
    E eu preencho "Nome" com "cbts"
    E eu marco "tdd"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Certificações"
    E eu devo ver "cbts"

    Entao eu clico em editar "cbts"
    E eu devo ver o título "Editar Certificação"
    E o campo "Nome" deve conter "cbts"
    E o campo "Periodicidade em meses" deve conter "3"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Certificações"

    Entao eu acesso o menu "T&D > Cadastros > Cursos/Treinamentos"
    E eu clico na linha "tdd" da imagem "Turmas"
    E eu devo ver o título "Turmas do curso tdd"
    E eu clico no botão "Inserir"
    E eu seleciono "tdd" de "Curso"
    E eu preencho "Descrição" com "tdd automatizados"
    E eu preencho "Horário" com "08:00"
    E eu preencho "Nome" com "Jose"
    E eu preencho "Custo (R$)" com "50"
    E eu saio do campo "Custo (R$)"
    E eu preencho "Instituição" com "Fortes"
    E eu preencho "Qtd. Prevista de Participantes" com "30"
    E eu seleciono "Sim" de "Realizada"
    E eu preencho o campo (JS) "prevIni" com "13/06/2012"
    E eu preencho o campo (JS) "prevFim" com "17/06/2012"
    E eu saio do campo "prevFim"
    E eu marco "13062012D"
    E eu marco "14062012D"
    E eu marco "15062012D"
    E eu clico no botão "Gravar"
    E eu clico no botão "Pesquisar"
    E eu clico no botão "InserirSelecionados"
    E eu clico no botão "Voltar"
    E eu devo ver o título "Turmas do curso tdd"

    Entao eu acesso o menu "T&D > Movimentações > Avaliação Prática > Notas Individuais"
    E eu devo ver o título "Avaliação Prática - Notas Individuais"
    E eu seleciono "cbts" de "Certificações com avaliações práticas"
    E eu espero 1 segundo
    E eu seleciono "Jose" de "Colaborador"

    Entao eu devo ver "Pular Corda"
    E eu devo ver "tdd"
    E eu devo ver "13/06/2012 - 17/06/2012"
    E eu preencho o campo (JS) "data-0" com "04/03/2016"
    E eu preencho o campo (JS) "nota-0" com "10"
    E eu clico no botão "Gravar"

    Entao eu não devo ver "tdd"
    E eu não devo ver "13/06/2012 - 17/06/2012"
    E eu seleciono "04/03/2016" de "Certificações em que o colaborador foi aprovado"
    E eu devo ver "tdd"
    E eu devo ver "13/06/2012 - 17/06/2012"
    E eu preencho o campo (JS) "data-0" com "01/03/2016"
    E eu clico no botão "Gravar"
    E eu devo ver "Avaliação gravada com sucesso"

    Entao eu não devo ver "tdd"
    E eu não devo ver "13/06/2012 - 17/06/2012"
    E eu seleciono "01/03/2016" de "Certificações em que o colaborador foi aprovado"
    E eu devo ver "tdd"
    E eu devo ver "13/06/2012 - 17/06/2012"

    Entao eu acesso o menu "T&D > Movimentações > Avaliação Prática > Notas em Lote"
    E eu devo ver o título "Avaliação Prática - Notas em Lote"
    E eu não devo ver "Jose"
    E eu seleciono "cbts" de "Certificações com avaliações práticas"
    E eu espero 1 segundo
    E eu seleciono "Pular Corda" de "Avaliação prática"

    Entao eu devo ver "Jose"
    E eu seleciono (JS) "Nova nota" de "avPraticas-0"
    E eu espero 1 segundo
    E eu preencho o campo (JS) "data-0" com "01/02/2016"
    E eu saio do campo "data-0"
    E eu devo ver o alert "Não é possível inserir uma data igual ou inferior a data da última avaliação" e clico no ok
    E eu preencho o campo (JS) "nota-0" com "10"
    E eu espero 1 segundo
    E eu clico no botão "Gravar"
    E eu devo ver "Avaliação gravada com sucesso"

    Entao eu acesso o menu "T&D > Relatórios > Colaboradores x Certificações"
    E eu devo ver o título "Colaboradores x Certificações"
    E eu marco "cbts"
    E eu marco "Não certificados"
    E eu não devo ver "Jose"
    E eu marco "Certificados"
    E eu devo ver "Jose"
    E eu marco "Empresa Padrão - Estabelecimento Padrão"
    E eu marco "area1 (Ativa)"
    E eu espero 1 segundo
    E eu marco "Jose"
    E eu preencho o campo (JS) "dataIni" com "01/03/2016"
    E eu preencho o campo (JS) "dataFim" com "05/03/2016"
    E eu preencho o campo (JS) "meses" com "0"
    E eu clico no botão "Relatorio"
    E eu clico no botão "RelatorioExportar"

    Entao eu seleciono "Certificação" de "Agrupar por"
    E eu clico no botão "Relatorio"
    E eu clico no botão "RelatorioExportar"
    E eu devo ver o título "Colaboradores x Certificações"

    Entao eu acesso o menu "Utilitários > Cadastros > Empresas"
    Entao eu clico em editar "Empresa Padrão"
    E eu seleciono "Periodicidade do curso" de "Controlar vencimento da certificação por"
    E eu clico no botão "Gravar"






    
    













