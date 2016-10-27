# language: pt

Funcionalidade: Cadastrar Modelos de Avaliação de Desempenho/Acompanhamento do Per. de Experiência

  Cenário: Cadastro de Modelos de Avaliação de Desempenho/Acompanhamento do Per. de Experiência
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "Aval. Desempenho > Cadastros > Avaliações de Desempenho/Acomp. do Período de Experiência"
    Então eu devo ver o título "Modelos de Avaliação de Desempenho/Acompanhamento do Per. de Experiência"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Modelo de Avaliação"
    E eu clico no botão "Avancar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Modelos de Avaliação de Desempenho/Acompanhamento do Per. de Experiência"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Modelo de Avaliação"
    E eu preencho "Título" com "_avaliacao I"
    E eu preencho "Observação" com "_experiencia"
    E eu seleciono "Avaliação de Desempenho" de "Tipo de Avaliação"
    E eu clico no botão "Avancar"

    Então eu devo ver o título "Inserir Pergunta da Avaliação"
    E eu preencho "aspecto" com "_aspecto 1"
    E eu seleciono "1" de "Ordem"
    E eu preencho "pergunta" com "_pergunta 1"
    E eu preencho "peso" com "1"
    E eu seleciono "Subjetiva" de "Tipo de Resposta"
    E eu clico no botão "Gravar"
    E eu devo ver "Pergunta gravada com sucesso."

    Então eu devo ver o título "Inserir Pergunta da Avaliação"
    E eu preencho "aspecto" com "_aspecto 1"
    E eu seleciono "2" de "Ordem"
    E eu espero 1 segundo
    E eu preencho "pergunta" com "_pergunta 2"
    E eu preencho "peso" com "2"
    E eu seleciono "Nota" de "Tipo de Resposta"
    E eu preencho "notaMinima" com "5"
    E eu preencho "notaMaxima" com "10"
    E eu marco "Solicitar comentário"
    E eu preencho "textoComentario" com "_comentário"
    E eu clico no botão "Gravar"
    E eu devo ver "Pergunta gravada com sucesso."

    Então eu devo ver o título "Inserir Pergunta da Avaliação"
    E eu preencho "aspecto" com "_aspecto 2"
    E eu seleciono "3" de "Ordem"
    E eu preencho "pergunta" com "_pergunta 3"
    E eu preencho "peso" com "3"
    E eu seleciono "Objetiva" de "Tipo de Resposta"
    E eu preencho "respostaObjetiva" com "_resposta a"
    E eu preencho "pesoRespostaObjetiva_0" com "1-1"
    E eu clico no botão "Gravar"
    E eu devo ver o alert "Campos inválidos." e clico no ok
    E eu preencho "pesoRespostaObjetiva_0" com "-"
    E eu clico no botão "Gravar"
    E eu devo ver o alert "Campos inválidos." e clico no ok
    E eu preencho "pesoRespostaObjetiva_0" com "-3"
    E eu clico no botão "Gravar"
    E eu devo ver o alert "A pergunta têm que possuir no mínimo 2 alternativas." e clico no ok
    E eu clico "Mais uma opção de resposta"
    E eu preencho "ro_1" com "_resposta b"
    E eu preencho "pesoRespostaObjetiva_1" com ""
    E eu clico "Mais uma opção de resposta"
    E eu preencho "ro_2" com "_resposta c"
    E eu preencho "pesoRespostaObjetiva_2" com ""
    E eu clico no botão "Gravar"
    E eu devo ver o alert "Não é possível inserir mais de uma alternativa com peso vazio." e clico no ok
    E eu preencho "pesoRespostaObjetiva_1" com "5"
    E eu marco "Solicitar comentário"
    E eu preencho "textoComentario" com "_comentário"
    E eu clico no botão "Gravar"
    E eu devo ver "Pergunta gravada com sucesso."

    Então eu devo ver o título "Inserir Pergunta da Avaliação"
    E eu preencho "aspecto" com "_aspecto 2"
    E eu seleciono "4" de "Ordem"
    E eu preencho "pergunta" com "_pergunta 4"
    E eu preencho "peso" com "4"
    E eu seleciono "Múltipla Escolha" de "Tipo de Resposta"
    E eu preencho "multiplaResposta" com "_resposta a"
    E eu preencho "pesoRespostaMultipla" com "1-1"
    E eu clico no botão "Gravar"
    E eu devo ver o alert "Campos inválidos." e clico no ok
    E eu preencho "pesoRespostaMultipla" com "-"
    E eu clico no botão "Gravar"
    E eu devo ver o alert "Campos inválidos." e clico no ok
    E eu preencho "pesoRespostaMultipla" com "-5"
    E eu clico "adicionarMultiplaResposta"
    E eu preencho "multiplaResposta_1" com "_resposta b"
    E eu preencho "pesoRespostaMultipla_1" com ""
    E eu clico "adicionarMultiplaResposta"
    E eu preencho "multiplaResposta_2" com "_resposta c"
    E eu preencho "pesoRespostaMultipla_2" com ""
    E eu clico no botão "Gravar"
    E eu devo ver o alert "Não é possível inserir mais de uma alternativa com peso vazio." e clico no ok
    E eu preencho "pesoRespostaMultipla_1" com "5"
    E eu marco "Solicitar comentário"
    E eu preencho "textoComentario" com "_comentário"
    E eu clico no botão "Gravar"
    E eu devo ver "Pergunta gravada com sucesso."

    E eu clico no botão "Voltar"
    E eu devo ver o título "Perguntas da Avaliação"
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Modelos de Avaliação de Desempenho/Acompanhamento do Per. de Experiência"
    E eu devo ver "_avaliacao I"

    Entao eu clico em editar "_avaliacao I"
    E eu devo ver o título "Editar Modelo de Avaliação"
    E o campo "Título" deve conter "_avaliacao I"
    E eu preencho "Título" com "_avaliacao II"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Modelos de Avaliação de Desempenho/Acompanhamento do Per. de Experiência"

    Então eu clico na linha "_avaliacao II" da imagem "Clonar"
    E eu devo ver "Clonar: _avaliacao II"
    E eu marco "Empresa Padrão"
    E eu clico no botão "Clonar"
    E eu devo ver "_avaliacao II (Clone)"
    
    Entao eu clico na linha "_avaliacao II" da imagem "Perguntas"
      Então eu devo ver o título "Perguntas da Avaliação"
      E eu clico em editar "_pergunta 1"
      E eu devo ver o título "Editar Pergunta da Avaliação"
      E o campo "pergunta" deve conter "_pergunta 1"
      E eu preencho "pergunta" com "_pergunta 11"
      E eu clico no botão "Gravar"
      Então eu devo ver o título "Perguntas da Avaliação"

      E eu clico em excluir "_pergunta 11"
      E eu devo ver o alert do confirmar exclusão e clico no ok
      Então eu devo ver "Pergunta excluída com sucesso."
      E eu não devo ver "_pergunta 11"

      E eu clico em excluir "_pergunta 2"
      E eu devo ver o alert do confirmar exclusão e clico no ok
      Então eu devo ver "Pergunta excluída com sucesso."
      E eu não devo ver "_pergunta 2"

      E eu clico em excluir "_pergunta 3"
      E eu devo ver o alert do confirmar exclusão e clico no ok
      Então eu devo ver "Pergunta excluída com sucesso."
      E eu não devo ver "_pergunta 3"

      E eu clico em excluir "_pergunta 4"
      E eu devo ver o alert do confirmar exclusão e clico no ok
      Então eu devo ver "Pergunta excluída com sucesso."
      E eu não devo ver "_pergunta 4"

    E eu clico no botão "Voltar"
    Então eu devo ver o título "Modelos de Avaliação de Desempenho/Acompanhamento do Per. de Experiência"

    Entao eu clico na linha "_avaliacao II" da imagem "Aspectos"

      Então eu devo ver o título "Aspectos da Avaliação"
      E eu clico em editar "_aspecto 1"
      E eu devo ver o título "Editar Aspecto"
      E o campo "nome" deve conter "_aspecto 1"
      E eu preencho "nome" com "_aspecto 11"
      E eu clico no botão "Gravar"
      Então eu devo ver o título "Aspectos da Avaliação"

      Então eu clico em excluir "_aspecto 11"
      E eu devo ver o alert do confirmar exclusão e clico no ok
      E eu devo ver "Aspecto excluído com sucesso."
      E eu não devo ver "_aspecto 1"

      Entao eu clico em excluir "_aspecto 2"
      E eu devo ver o alert do confirmar exclusão e clico no ok
      E eu devo ver "Aspecto excluído com sucesso."
      E eu não devo ver "_aspecto 2"

      E eu clico no botão "Voltar"
    Então eu devo ver o título "Modelos de Avaliação de Desempenho/Acompanhamento do Per. de Experiência"

    Então eu clico em excluir "_avaliacao II"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Modelo de avaliação excluído com sucesso."