# language: pt

Funcionalidade: Cadastrar Pesquisas

  Cenário: Cadastro de Pesquisas
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "Pesquisas > Movimentações > Pesquisas"
    Então eu devo ver o título "Pesquisas"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Pesquisa"
    E eu clico no botão "Avancar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Pesquisas"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Pesquisa"
    E eu preencho "Título" com "_pesquisa I"
    E eu preencho o campo (JS) "dataInicio" com "01/06/2011"
    E eu preencho o campo (JS) "dataFim" com "30/06/2011"
    E eu preencho "Observação" com "_clima organizacional"
    E eu seleciono "Não" de "anonima"
    E eu clico no botão "Avancar"

    Então eu devo ver o título "Pesquisa - _pesquisa I"
    E eu clico "Inserir pergunta aqui"

    Então eu devo ver o título "Inserir Pergunta"
    E eu preencho "aspecto" com "_aspecto 1"
    E eu saio do campo "aspecto"
    E eu preencho "texto" com "_pergunta 1"
    E eu seleciono "Subjetiva" de "Tipos de Respostas"
    E eu clico no botão "Gravar"

    Então eu devo ver o título "Pesquisa - _pesquisa I"
    E eu clico "Inserir pergunta aqui"

    Então eu devo ver o título "Inserir Pergunta"
    E eu preencho "aspecto" com "_aspecto 1"
    E eu saio do campo "aspecto"
    E eu seleciono "Nota" de "Tipos de Respostas"
    E eu preencho "texto" com "_pergunta 2"
    E eu preencho "notaMinima" com "5"
    E eu preencho "notaMaxima" com "10"
    E eu marco "Solicitar comentário da resposta (especifique abaixo a solicitação)"
    E eu preencho "textoComentario" com "_comentário"
    E eu clico no botão "Gravar"

    Então eu devo ver o título "Pesquisa - _pesquisa I"
    E eu clico "Inserir pergunta aqui"

    Então eu devo ver o título "Inserir Pergunta"
    E eu preencho "aspecto" com "_aspecto 2"
    E eu saio do campo "aspecto"
    E eu preencho "texto" com "_pergunta 3"
    E eu seleciono "Objetiva" de "Tipos de Respostas"
    E eu preencho "respostaObjetiva" com "_resposta a"
    E eu clico "Mais uma opção de resposta"
    E eu preencho "ro_1" com "_resposta b"
    E eu clico "Mais uma opção de resposta"
    E eu preencho "ro_2" com "_resposta c"
    E eu marco "Solicitar comentário da resposta (especifique abaixo a solicitação)"
    E eu preencho "textoComentario" com "_comentário"
    E eu clico no botão "Gravar"

    Então eu devo ver o título "Pesquisa - _pesquisa I"
    E eu clico "Inserir pergunta aqui"

    Então eu devo ver o título "Inserir Pergunta"
    E eu preencho "aspecto" com "_aspecto 2"
    E eu saio do campo "aspecto"
    E eu saio do campo "aspecto"
    E eu seleciono "Múltipla Escolha" de "Tipos de Respostas"
    E eu preencho "texto" com "_pergunta 4"
    E eu preencho "multiplaResposta" com "_resposta a"
    E eu clico "adicionarMultiplaResposta"
    E eu preencho "rm_1" com "_resposta b"
    E eu clico "adicionarMultiplaResposta"
    E eu preencho "rm_2" com "_resposta c"
    E eu marco "Solicitar comentário"
    E eu preencho "textoComentario" com "_comentário"
    E eu clico no botão "Gravar"

    Então eu devo ver o título "Pesquisa - _pesquisa I"
    E eu clico no botão "AplicarNaOrdemAtual"
    Então eu devo ver o título "Pesquisa - _pesquisa I"
    E eu clico no botão "Concluir"
    E eu devo ver o título "Pesquisas"
    E eu devo ver "_pesquisa I"

    Entao eu clico em editar "_pesquisa I"
    E eu devo ver o título "Editar Pesquisa"
    E o campo "Título" deve conter "_pesquisa I"
    E eu preencho "Título" com "_pesquisa 2"
    E eu clico no botão "Avancar"
    E eu devo ver o título "Pesquisa - _pesquisa 2"
    E eu clico no botão "AplicarNaOrdemAtual"
    Então eu devo ver o título "Pesquisa - _pesquisa 2"
    E eu clico no botão "Concluir"
    E eu devo ver o título "Pesquisas"
    
    Entao eu clico na linha "_pesquisa 2" da imagem "Questionário da pesquisa"
      Então eu devo ver o título "Pesquisa - _pesquisa 2"
      E eu clico na imagem "Editar" da pergunta "_pergunta 1"
      E eu devo ver o título "Editar Pergunta"
      E o campo "texto" deve conter "_pergunta 1"
      E eu preencho "texto" com "_pergunta 11"
      E eu clico no botão "Gravar"
      Então eu devo ver o título "Pesquisa - _pesquisa 2"

      E eu clico na imagem "Excluir" da pergunta "_pergunta 11"
      E eu devo ver o alert do confirmar exclusão e clico no ok
      Então eu devo ver "Pergunta excluída com sucesso."
      E eu não devo ver "_pergunta 11"

      E eu clico na imagem "Excluir" da pergunta "_pergunta 2"
      E eu devo ver o alert do confirmar exclusão e clico no ok
      Então eu devo ver "Pergunta excluída com sucesso."
      E eu não devo ver "_pergunta 2"

      E eu clico na imagem "Excluir" da pergunta "_pergunta 3"
      E eu devo ver o alert do confirmar exclusão e clico no ok
      Então eu devo ver "Pergunta excluída com sucesso."
      E eu não devo ver "_pergunta 3"

      E eu clico na imagem "Excluir" da pergunta "_pergunta 4"
      E eu devo ver o alert do confirmar exclusão e clico no ok
      Então eu devo ver "Pergunta excluída com sucesso."
      E eu não devo ver "_pergunta 4"

    E eu clico no botão "Voltar"
    Então eu devo ver o título "Editar Pesquisa"
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Pesquisas"

    Entao eu clico na linha "_pesquisa 2" da imagem "Aspectos da pesquisa"

      Então eu devo ver o título "Aspectos da Pesquisa - _pesquisa 2"
      E eu clico em editar "_aspecto 1"
      E eu devo ver o título "Editar Aspecto"
      E o campo "nome" deve conter "_aspecto 1"
      E eu preencho "nome" com "_aspecto 11"
      E eu clico no botão "Gravar"
      Então eu devo ver o título "Aspectos da Pesquisa - _pesquisa 2"

      Então eu clico em excluir "_aspecto 11"
      E eu devo ver o alert do confirmar exclusão e clico no ok
      E eu devo ver "Aspecto excluído com sucesso."
      E eu não devo ver "_aspecto 1"

      Entao eu clico em excluir "_aspecto 2"
      E eu devo ver o alert do confirmar exclusão e clico no ok
      E eu devo ver "Aspecto excluído com sucesso."
      E eu não devo ver "_aspecto 2"

      E eu clico no botão "Voltar"
    Então eu devo ver o título "Pesquisas"

    Então eu clico na linha "_pesquisa 2" da imagem "Clonar"
    E eu devo ver "Clonar: _pesquisa 2"
    E eu marco "Empresa Padrão"
    E eu clico no botão "Clonar"
    E eu devo ver "_pesquisa 2 (Clone)"

    Então eu clico na linha "_pesquisa 2" da imagem "Liberar Pesquisa"
    E eu devo ver o alert do confirmar e clico no ok

    Então eu clico em excluir "_pesquisa 2"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Pesquisa excluída com sucesso."

    Então eu clico em excluir "_pesquisa 2 (Clone)"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Pesquisa excluída com sucesso."