# language: pt

Funcionalidade: Cadastrar Curso

  Cenário: Cadastro de Curso
    Dado que exista uma avaliacao de curso "Avaliacao"
    Dado que eu esteja logado
    Dado que exista um tipo de despesa "_apostilas"
    Dado que exista um tipo de despesa "_alimentacao"

    Quando eu acesso o menu "T&D > Cadastros > Cursos/Treinamentos"
    Então eu devo ver o título "Cursos"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Curso"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Cursos"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Curso"
    E eu preencho "Nome" com "5s"
    E eu preencho "Carga Horária" com "8"
    E eu preencho "Percentual mínimo de frequência para aprovação (%)" com "75"
    E eu preencho "Conteúdo Programático" com "_conteudo"
    E eu preencho "Critérios de Avaliação" com "_criterios"
    E eu marco "Avaliacao"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Cursos"
    E eu devo ver "5s"

    Entao eu clico em editar "5s"
    E eu devo ver o título "Editar Curso"
    E o campo "Nome" deve conter "5s"
    E eu preencho "Nome" com "_testes"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Cursos"

    Então eu clico na linha "_testes" da imagem "Turmas"
    E eu devo ver o título "Turmas - _testes"
    E eu clico no botão "Inserir"
    E eu seleciono "_testes" de "Curso"
    E eu preencho "Descrição" com "_testes automatizados"
    E eu preencho "Horário" com "08:00"
    E eu preencho "Instrutor" com "_jose"
    E eu preencho "Custo (R$)" com "50"
    E eu saio do campo "Custo (R$)"

    Então eu clico no ícone com o título "Detalhamento dos custos"
    E eu preencho o campo do item "_apostilas" com "2,55"
    E eu preencho o campo do item "_alimentacao" com "3,05"
    E eu clico no botão com o texto "Gravar"
    E eu espero 1 segundos
    
    Então o campo "custo" deve conter "5,60"

    E eu preencho "Instituição" com "_fortes"
    E eu preencho "Qtd. Prevista de Participantes" com "30"
    E eu seleciono "Não" de "Realizada"
    E eu preencho o campo (JS) "prevIni" com "13/06/2012"
    E eu preencho o campo (JS) "prevFim" com "17/06/2012"
    E eu saio do campo "prevFim"
    E eu marco "13/06/2012 - quarta-feira"
    E eu marco "14/06/2012 - quinta-feira"
    E eu marco "15/06/2012 - sexta-feira"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Incluir Colaboradores na Turma - _testes automatizados"
    E eu clico no botão "Voltar"
    E eu devo ver o título "Colaboradores Inscritos no curso de _testes, Turma - _testes automatizados"
    E eu clico no botão "Voltar"
    E eu devo ver o título "Turmas - _testes"

    Então eu clico em editar "_testes automatizados"
    E eu devo ver o título "Editar Turma - _testes"
    E o campo "Descrição" deve conter "_testes automatizados"
    E eu preencho "Descrição" com "_testes auto"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Turmas - _testes"
    E eu não devo ver "_testes automatizados"

    Então eu clico na linha "_testes auto" da imagem "Lista de Frequência"
    E eu devo ver "Período: 13/06/2012 a 17/06/2012"
    E eu clico no botão "VoltarListaFrequencia"
    E eu devo ver o título "Turmas - _testes"
    E eu clico no botão "Voltar"
    E eu devo ver o título "Cursos"
    
    Então eu clico em excluir "_testes"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "possui dependências em"

    Então eu clico na linha "_testes" da imagem "Turmas"
    E eu devo ver o título "Turmas - _testes"
    E eu clico em excluir "_testes auto"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Turma excluída com sucesso."
    E eu não devo ver "_testes auto"

    E eu clico no botão "Voltar"
    E eu devo ver o título "Cursos"
    Então eu clico em excluir "_testes"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Curso excluído com sucesso."
    E eu não devo ver "_testes"