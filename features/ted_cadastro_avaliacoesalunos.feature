# language: pt

Funcionalidade: Cadastrar Avaliações dos Cursos

  Cenário: Avaliações dos Cursos
     Dado que exista um modelo avaliacao aluno "Avaliaçao de Aluno Teste"
    Dado que eu esteja logado com o usuário "SOS"


    Quando eu acesso o menu "T&D > Cadastros > Avaliações dos Alunos"
    Então eu devo ver o título "Avaliações dos Alunos"
      E eu clico no botão "Inserir"
      Então eu devo ver o título "Inserir Avaliação do Aluno"
      E eu clico no botão "Gravar"
      Então eu devo ver o alert do valida campos e clico no ok
      E eu clico no botão "Cancelar"
      Então eu devo ver o título "Avaliações dos Alunos"

      Então eu clico no botão "Inserir"
      E eu devo ver o título "Inserir Avaliação do Aluno"
      E eu preencho "Título" com "_avaliacao"
      E eu seleciono "Nota" de "Tipo"
      E eu preencho "Mínimo para Aprovação" com "7"
      E eu clico no botão "Gravar"
      E eu devo ver o título "Avaliações dos Alunos"
      E eu devo ver "_avaliacao"

      Entao eu clico em editar "_avaliacao"
      E eu devo ver o título "Editar Avaliação do Aluno"
      E o campo "Título" deve conter "_avaliacao"
      E eu preencho "Título" com "_testes"
      E eu seleciono "Porcentagem (%)" de "Tipo"
      E eu preencho "Mínimo para Aprovação" com "75,00"
      E eu clico no botão "Gravar"
      E eu devo ver o título "Avaliações dos Alunos"

      Então eu clico em excluir "_testes"
      E eu devo ver o alert do confirmar exclusão e clico no ok
      Então eu devo ver "Avaliação excluída com sucesso."
      E eu não devo ver "_testes"

      Então eu clico no botão "Inserir"
      E eu devo ver o título "Inserir Avaliação do Aluno"
      E eu preencho "Título" com "_avaliacao II"
      E eu seleciono "Avaliação" de "Tipo"
      E eu preencho "Mínimo para Aprovação" com "7"
      E eu clico no botão "Gravar"
      Então eu devo ver o alert do valida campos e clico no ok
      E eu seleciono "Avaliaçao de Aluno Teste" de "Modelo da Avaliação"
      E eu clico no botão "Gravar"
      E eu devo ver o título "Avaliações dos Alunos"
      E eu devo ver "_avaliacao II"
