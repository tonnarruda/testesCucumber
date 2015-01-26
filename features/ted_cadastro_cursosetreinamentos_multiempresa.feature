# language: pt

Funcionalidade: Cadastrar Curso

  Cenário: Cadastro de Curso
    Dado que exista uma avaliacao de curso "Avaliacao"
    Dado que exista um tipo de despesa "_apostilas"
    Dado que exista um tipo de despesa "_alimentacao"
    Dado que exista uma empresa "Newfortes"
    Dado que eu esteja logado com o usuário "fortes"

    Quando eu acesso o menu "Utilitários > Configurações > Sistema"
    Então eu devo ver o título "Editar Configurações do Sistema"
    E eu preencho "Atualizador" com "Samuel Pinheiro"
    E eu preencho "E-mail do suporte técnico" com "samuelpinheiro@entetenologia.com.br"
    E eu preencho "Configuração do autenticador" com "samuel"
    E eu preencho "Usuário" com "samuelpinheiro@entetenologia.com.br"
    E eu preencho "Senha" com "123456"
    E eu marco "Compartilhar cursos entre empresas."
    E eu clico no botão "Gravar"
    Entao eu devo ver "Configurações do sistema atualizadas com sucesso."

    Então eu acesso o menu "T&D > Cadastros > Cursos/Treinamentos"
    E eu devo ver o título "Cursos"
    E eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Curso"
    E eu preencho "Nome" com "5s"
    E eu preencho "Carga Horária" com "8"
    E eu preencho "Percentual mínimo de frequência para aprovação (%)" com "75"
    E eu preencho "Conteúdo Programático" com "_conteudo"
    E eu preencho "Critérios de Avaliação" com "_criterios"
    E eu marco "Newfortes"
    E eu marco "Avaliacao"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Cursos"
    E eu devo ver "5s"

    Então eu clico na linha "5s" da imagem "Turmas"
    E eu devo ver o título "Turmas do curso 5s"
    E eu clico no botão "Inserir"
    E eu seleciono "5s" de "Curso"
    E eu preencho "Descrição" com "5s automatizados"
    E eu preencho "Horário" com "08:00"
    E eu preencho "Nome" com "_jose"
    E eu preencho "Custo (R$)" com "50"
    E eu saio do campo "Custo (R$)"
    E eu preencho o campo (JS) "prevIni" com "13/06/2012"
    E eu preencho o campo (JS) "prevFim" com "17/06/2012"
    E eu saio do campo "prevFim"
    E eu marco "13062012D"
    E eu clico no botão "Gravar"

    Então eu acesso o menu "Utilitários > Alterar Empresa > Newfortes"

    Então eu acesso o menu "T&D > Cadastros > Cursos/Treinamentos"
    E eu clico em editar "5s"
    E eu devo ver "Este curso foi compartilhado pela empresa Empresa Padrão."
    E eu devo ver o título "Editar Curso"
    E o campo "Nome" deve conter "5s"
    E eu preencho "Nome" com "_testes"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Cursos"

    Então eu clico na linha "_testes" da imagem "Turmas"
    E eu devo ver o título "Turmas do curso _testes"
    E eu devo ver "5s automatizados"
    E eu clico no botão "Inserir"
    E eu seleciono "_testes" de "Curso"
    E eu preencho "Descrição" com "_testes automatizados"
    E eu preencho "Horário" com "08:00"
    E eu preencho "Custo (R$)" com "0,50"
    E eu saio do campo "Custo (R$)"
    E eu preencho "Instituição" com "_fortes"
    E eu preencho "Nome" com "Samuel Pinheiro"
    E eu preencho "Qtd. Prevista de Participantes" com "30"
    E eu seleciono "Não" de "Realizada"
    E eu preencho o campo (JS) "prevIni" com "13/06/2012"
    E eu preencho o campo (JS) "prevFim" com "17/06/2012"
    E eu saio do campo "prevFim"
    E eu marco "13062012D"
    E eu marco "14062012D"
    E eu marco "15062012D"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Incluir Colaboradores na Turma - _testes automatizados"
    E eu clico no botão "Voltar"
    E eu devo ver o título "Colaboradores Inscritos no Curso de _testes, Turma - _testes automatizados"
    E eu clico no botão "Voltar"
    E eu devo ver o título "Turmas do curso _testes"

    Então eu clico em editar "5s automatizados"
    E eu devo ver o título "Editar Turma - 5s automatizados"
    E o campo "Descrição" deve conter "5s automatizados"
    E eu preencho "Descrição" com "5s automatizados auto"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Turmas do curso _testes"
    E eu devo ver "5s automatizados auto"

    Então eu clico na linha "5s automatizados auto" da imagem "Lista de Frequência"
    E eu devo ver "Período: 13/06/2012 a 17/06/2012"
    E eu clico no botão "Voltar"
    E eu devo ver o título "Turmas do curso _testes"
    E eu clico no botão "Voltar"
    E eu devo ver o título "Cursos"

    Então eu clico na linha "_testes" da imagem "Turmas"
    E eu devo ver o título "Turmas do curso _testes"
    E eu clico em excluir "_testes auto"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Turma excluída com sucesso."
    E eu não devo ver "_testes auto"

    Então eu acesso o menu "Utilitários > Alterar Empresa > Empresa Padrão"
    Então eu acesso o menu "T&D > Cadastros > Cursos/Treinamentos"

    Então eu clico na linha "_testes" da imagem "Turmas"
    E eu devo ver o título "Turmas do curso _testes"
    E eu clico em excluir "5s automatizados auto"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Turma excluída com sucesso."
    E eu não devo ver "5s automatizados auto"

    E eu clico no botão "Voltar"
    E eu devo ver o título "Cursos"

    Então eu clico em excluir "_testes"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Curso excluído com sucesso."
    E eu não devo ver "_testes"