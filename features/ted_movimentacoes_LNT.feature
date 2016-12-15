# language: pt

Funcionalidade: Levantamento de Necessidade de Treinamento (LNT)

  Cenário: Plano de Treinamento
    Dado que exista um curso "tdd"
    Dado que exista uma turma "a1" para o curso "tdd"
    Dado que exista um colaborador "Samuel", da area "Area A", com o cargo "Cargo w" e a faixa salarial "Faixa I"
    Dado que exista um colaborador "Kamilla", da area "Area B", com o cargo "Cargo z" e a faixa salarial "Faixa II"

    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "T&D > Movimentações > Levantamento de Necessidade de Treinamento (LNT)"
    Então eu devo ver o título "Levantamentos de Necessidade de Treinamento (LNT)"
    E eu clico no botão novo "Inserir"
    
    Então eu devo ver o título "Inserir LNT"
    E eu clico no botão novo "Gravar"
    E eu devo ver o alert "Preencha os campos indicados" e clico no ok
    E eu clico no botão novo "Voltar"

    Então eu devo ver o título "Levantamentos de Necessidade de Treinamento (LNT)"
    E eu clico no botão novo "Inserir"

    E eu preencho "Descrição" com "LNT xxx"
    E eu preencho campo pelo id "dataInicio" com "01/12/2016"
    E eu preencho campo pelo id "dataFim" com "30/12/2030"
    E eu marco "Empresa Padrão"
    E eu espero 1 segundos
    E eu marco "Area A"
    E eu marco "Area B"
    E eu clico no botão novo "Gravar"

    Então eu devo ver "LNT gravada com sucesso"
    E eu devo ver o título "Levantamentos de Necessidade de Treinamento (LNT)"
    E eu devo ver "LNT xxx"
    E eu devo ver "01/12/2016 a 30/12/2030"

    Então eu clico no icone "Editar" da linha contendo "LNT xxx"
    E eu devo ver o título "Editar LNT"
    E eu preencho "Descrição" com "LNT Anual"
    E eu clico no botão novo "Gravar"

    Então eu devo ver "LNT atualizada com sucesso"
    E eu devo ver o título "Levantamentos de Necessidade de Treinamento (LNT)"
    E eu não devo ver "LNT xxx"
    E eu devo ver "LNT Anual"

    Então eu clico no icone "Adicionar colaboradores" da linha contendo "LNT Anual"
    E eu devo ver o título "Participantes LNT - LNT Anual"
    E eu preencho o nome do curso da LNT "0" com "Curso Lnt 1"

    Então eu clico no botão de Id "inserir_Avaliador"
    E eu clico no botão novo "Relacionar"
    E eu devo ver o alert "Não existem cursos para relacionar" e clico no ok
    E eu clico no botão de Id "checkGroupempresasCheckDialog1"
    E eu clico no botão de Id "checkGroupareasCheckDialog1"
    E eu clico no botão de Id "checkGroupareasCheckDialog2"
    E eu clico no botão de Id "checkGroupparticipantesCheck1"
    E eu clico no botão de Id "checkGroupparticipantesCheck2"
    E eu clico no botão novo "Relacionar"
    E eu clico no input de name  "cursosCheck"
    E eu clico no botão do dialog "Relacionar"
    E eu devo ver o alert "Participantes relacionados com sucesso" e clico no ok
    E eu clico no botão novo "Sair"

    Então eu devo ver "Samuel"
    E eu devo ver "Kamilla"
    E eu clico no botão de class "more-curso"
    E eu preencho o nome do curso da LNT "1" com "Curso Lnt 2"

    Então eu clico no botão novo "Voltar"
    E eu devo ver "Existem Cursos e/ou participantes inseridos e/ou removidos que não foram gravados"
    E eu clico no botão do dialog "Cancelar"
    E eu clico no botão novo "Gravar"
    E eu espero 1 segundo
    E eu devo ver "Cursos e participantes gravados com sucesso"
    E eu clico no botão novo "Voltar"

    Então eu devo ver o título "Levantamentos de Necessidade de Treinamento (LNT)"
    E eu clico no icone "Editar" da linha contendo "LNT Anual"
    E eu preencho campo pelo id "dataFim" com "14/12/2016"
    E eu clico no botão novo "Gravar"
    E eu devo ver "LNT atualizada com sucesso"
    E eu devo ver o título "Levantamentos de Necessidade de Treinamento (LNT)"

    Então eu clico no icone "Analisar" da linha contendo "LNT Anual"
    E eu devo ver o título "Participantes LNT - LNT Anual"
    E eu seleciono o curso LNT "Curso Lnt 2"
    E eu clico na ação de excluir cursos da LNT
    E eu clico no botão novo "Gravar"
    E eu espero 1 segundo
    E eu devo ver "Cursos e participantes gravados com sucesso"
    E eu não devo ver "Curso Lnt 2"
    E eu clico no botão novo "Voltar"

    Então eu devo ver o título "Levantamentos de Necessidade de Treinamento (LNT)"
    E eu clico no icone "Finalizar" da linha contendo "LNT Anual"
    E eu devo ver "LNT finalizada com sucesso"

    Então eu clico no icone "Gerar cursos e turmas" da linha contendo "LNT Anual"
    E eu devo ver o título "Gerar Cursos e Turmas"
    E eu clico no icone "Criar Curso" da linha contendo "Curso Lnt 1"

    Então eu devo ver o título "Inserir Curso"
    E eu clico no botão "Cancelar"
    E eu devo ver o título "Gerar Cursos e Turmas"
    E eu clico no icone "Criar Curso" da linha contendo "Curso Lnt 1"
    E eu clico no botão "Gravar"

    Então eu clico no icone "Criar turmas/relacionar Participantes" da linha contendo "Curso Lnt 1"
    E eu devo ver "Cadastro de Turma"
    E eu clico no botão novo "Gravar"
    E eu devo ver o alert "Marque pelo menos um participante para ser inserido na turma selecionada" e clico no ok
    E eu clico no botão de Id "checkGroupparticipantesCheck1"
    E eu clico no botão novo "Gravar e criar nova"
    E eu devo ver o alert "Preencha os campos indicados" e clico no ok
    E eu preencho "Descrição" com "Turma Lnt 1"
    E eu preencho "Instrutor" com "Samuel"
    E eu preencho campo pelo id "prevIni" com "01/01/2017"
    E eu preencho campo pelo id "prevFim" com "30/01/2017"
    E eu preencho "Custo" com "5000"
    E eu clico no botão novo "Gravar e criar nova"
    E eu devo ver o alert "Turma e Participantes salvos com sucesso" e clico no ok

    Então eu clico no botão novo "Carregar dados da turma anterior"
    E eu preencho "Descrição" com "Turma Lnt 2"
    E eu clico no botão de Id "checkGroupparticipantesCheck2"
    E eu clico no botão novo "Gravar"

    Então eu clico no icone "Visualizar" da linha contendo "Curso Lnt 1"
    E eu devo ver o título "Curso - Curso Lnt 1"
    E eu clico no icone de visualizar participantes do curso da LNT
    E eu devo ver "Turma Lnt 1"
    E eu devo ver "Turma Lnt 2"
    E eu devo ver "Samuel"
    E eu devo ver "Kamilla"
    E eu clico no botão novo "Voltar"
    E eu devo ver o título "Gerar Cursos e Turmas"
    E eu clico no botão novo "Voltar"

    Então eu devo ver o título "Levantamentos de Necessidade de Treinamento (LNT)"
    E eu clico no icone "Reabrir" da linha contendo "LNT Anual"
    E eu devo ver "LNT reaberta com sucesso"
    E eu clico no icone "Finalizar" da linha contendo "LNT Anual"
    E eu devo ver "LNT finalizada com sucesso"

    Então eu clico no icone "Imprimir" da linha contendo "LNT Anual"
    E eu clico no botão do dialog "Gerar Relatório"
    E eu devo ver o título "Levantamentos de Necessidade de Treinamento (LNT)"

    Então eu clico no icone "Imprimir" da linha contendo "LNT Anual"
    E eu seleciono a opçao de valor "C" do rádio de id "apruparRelatorioPor"
    E eu clico no botão do dialog "Gerar Relatório"
    E eu devo ver o título "Levantamentos de Necessidade de Treinamento (LNT)"

    Então eu clico no icone "Excluir" da linha contendo "LNT Anual"
    E eu devo ver o alert do confirmar e clico no ok
    Entao eu devo ver "LNT excluída com sucesso"
    E eu não devo ver "LNT Anual"
