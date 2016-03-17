# language: pt

Funcionalidade: Cadastrar Avaliações de Desempenho

  Cenário: Cadastro de Avaliações de Desempenho
    Dado que exista um modelo avaliacao desempenho "Avaliacao de Desempenho"

    Dado que eu esteja logado com o usuário "fortes"
    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Avaliações de Desempenho"
    Então eu devo ver o título "Avaliações de Desempenho"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Avaliação de Desempenho"
    E eu clico no botão "Avancar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Avaliações de Desempenho"

    Então eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Avaliação de Desempenho"
    E eu preencho "Título" com "_avaliacao 1"
    E eu preencho o campo (JS) "inicio" com "01/06/2011"
    E eu preencho o campo (JS) "fim" com "30/06/2011"
    E eu seleciono "Avaliacao de Desempenho" de "modelo"
    E eu seleciono "Não" de "anonima"
    E eu seleciono "Não" de "permiteAutoAvaliacao"
    E eu clico no botão "Avancar"

    Então eu devo ver o título "Participantes - _avaliacao 1"
    E eu clico no botão "Voltar"
    
    Então eu devo ver o título "Avaliações de Desempenho"
    E eu clico em editar "_avaliacao 1"

    Então eu devo ver o título "Editar Avaliação de Desempenho"
    E o campo "Título" deve conter "_avaliacao 1"
    E eu preencho "Título" com "_avaliacao 2"
    E eu clico no botão "Gravar"

    Então eu devo ver o título "Avaliações de Desempenho"
    Então eu clico na linha "_avaliacao 2" da imagem "Clonar"
    E eu devo ver "Clonar: _avaliacao 2"
    E eu marco "Empresa Padrão"
    E eu clico no botão "Clonar"
    E eu devo ver "_avaliacao 2 (Clone)"

    Então eu clico na linha "_avaliacao 2" da imagem "Liberar"
    Então eu devo ver o alert do confirmar e clico no ok
    E eu devo ver "Não foi possível liberar esta avaliação: Nenhum avaliador possui colaboradores para avaliar."

    Então eu clico em excluir "_avaliacao 2"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Avaliação de desempenho excluída com sucesso."

    Então eu clico em excluir "_avaliacao 2 (Clone)"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Avaliação de desempenho excluída com sucesso."
   