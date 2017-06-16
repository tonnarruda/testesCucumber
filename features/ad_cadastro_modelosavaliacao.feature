# language: pt

Funcionalidade: Cadastrar Modelos de Avaliação de Desempenho/Acompanhamento do Per. de Experiência

  Cenário: Validar Campos obrigatórios
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Aval. Desempenho > Cadastros > Avaliações de Desempenho/Acomp. do Período de Experiência"
         E eu clico no botão "Inserir"
         E eu clico no botão "Avancar"
     Então eu devo ver o alert do valida campos e clico no ok
         E eu clico no botão "Voltar"


  Cenário: Cadastrar Modelo de Avaliação sem pergunta associada
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Aval. Desempenho > Cadastros > Avaliações de Desempenho/Acomp. do Período de Experiência"
         E eu clico no botão "Inserir"
         E eu preencho "Título" com "Modelo de Avaliação de Desenpenho"
         E eu preencho "Observação" com "Avaliação Semestral"
         E eu seleciono "Avaliação de Desempenho" de "Tipo de Avaliação"
         E eu clico no botão "Avancar"
         E eu clico no botão "Voltar"    
         E eu clico no botão "Voltar"   


  Cenário: Cadastrar Modelo de Acompanhamento do Per. de Experiência
      Dado que exista um periodo de experiencia "30 dias" de 30 dias
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Aval. Desempenho > Cadastros > Avaliações de Desempenho/Acomp. do Período de Experiência"
         E eu clico no botão "Inserir"
         E eu preencho "Título" com "Modelo de Avaliação de Desenpenho"
         E eu preencho "Observação" com "Avaliação Semestral"
         E eu seleciono "Acompanhamento do Período de Experiência" de "Tipo de Avaliação"
         E eu clico no botão "Avancar"
     Então eu devo ver o alert do valida campos e clico no ok
         E eu seleciono "30 dias (30 dias)" de "periodoExperiencia"
         E eu clico no botão "Avancar"
         E eu clico no botão "Voltar"    
         E eu clico no botão "Voltar"           

 
  Cenário: Cadastrar Modelo de Avaliação com pergunta associada
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Aval. Desempenho > Cadastros > Avaliações de Desempenho/Acomp. do Período de Experiência"
         E eu clico no botão "Inserir"
         E eu preencho "Título" com "Modelo de Avaliação de Desenpenho"
         E eu preencho "Observação" com "_experiencia"
         E eu seleciono "Avaliação de Desempenho" de "Tipo de Avaliação"
         E eu clico no botão "Avancar"
     Então eu preencho "aspecto" com "Comportamental"
         E eu seleciono "1" de "Ordem"
         E eu preencho "pergunta" com "_pergunta 1"
         E eu preencho "peso" com "1"
         E eu seleciono "Subjetiva" de "Tipo de Resposta"
         E eu clico no botão "Gravar"
     Então eu devo ver "Pergunta gravada com sucesso." 
         E eu preencho "aspecto" com "Conhecimento Técnico"        
         E eu seleciono "2" de "Ordem"
         E eu preencho "pergunta" com "Como você avalia seu conhecimento no produto?"
         E eu preencho "peso" com "2"
         E eu seleciono "Nota" de "Tipo de Resposta"
         E eu preencho "notaMinima" com "5"
         E eu preencho "notaMaxima" com "10"
         E eu marco "Solicitar comentário"
         E eu preencho "textoComentario" com "Por favor, justifique sua resposta"
         E eu clico no botão "Gravar"
     Então eu devo ver "Pergunta gravada com sucesso."
         E eu preencho "aspecto" com "Comportamental"
         E eu seleciono "3" de "Ordem"
         E eu preencho "pergunta" com "Como você avalia seu conhecimento na ferramenta X"
         E eu preencho "peso" com "3"
         E eu seleciono "Objetiva" de "Tipo de Resposta"
         E eu preencho "respostaObjetiva" com "Ruim"
         E eu preencho "pesoRespostaObjetiva_0" com "1-1"
         E eu clico no botão "Gravar"
         E eu devo ver o alert "Campos inválidos." e clico no ok
         E eu preencho "pesoRespostaObjetiva_0" com "-3"
         E eu clico no botão "Gravar"
         E eu devo ver o alert "A pergunta têm que possuir no mínimo 2 alternativas." e clico no ok
         E eu clico "Mais uma opção de resposta"
         E eu preencho "ro_1" com "Regular"
         E eu clico "Mais uma opção de resposta"
         E eu preencho "ro_2" com "Bom"
         E eu marco "Solicitar comentário"
         E eu preencho "textoComentario" com "Por favor, justifique sua resposta"
         E eu clico no botão "Gravar"
     Então eu devo ver "Pergunta gravada com sucesso."        
         E eu preencho "aspecto" com "Comportamental"
         E eu seleciono "4" de "Ordem"
         E eu preencho "pergunta" com "Pergunta de múltipla escolha"
         E eu preencho "peso" com "4"
         E eu seleciono "Múltipla Escolha" de "Tipo de Resposta"
         E eu preencho "multiplaResposta" com "Resposta A"
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
     Então eu clico no botão "Voltar"
         E eu devo ver o título "Perguntas da Avaliação"
         E eu clico no botão "Voltar"
     Então eu devo ver o título "Modelos de Avaliação de Desempenho/Acompanhamento do Per. de Experiência"
         E eu devo ver "Modelo de Avaliação de Desenpenho"



  Cenário: Inativar um  Modelo de Avaliação 
      Dado que exista um periodo de experiencia "30 dias" de 30 dias
      Dado que exista um modelo avaliacao de período de experiência "Avaliação Experiência - 30 dias"
      Dado que exista um modelo avaliacao de período de experiência "Avaliação Experiência - 45 dias"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Aval. Desempenho > Cadastros > Avaliações de Desempenho/Acomp. do Período de Experiência"      
         E eu clico em editar "Avaliação Experiência - 30 dias"
         E eu seleciono "Não" de "ativo"
         E eu seleciono "30 dias (30 dias)" de "periodoExperiencia"
         E eu clico no botão "Gravar"

 
  Cenário: Editar um  Modelo de Avaliação 
      Dado que exista um periodo de experiencia "30 dias" de 30 dias
      Dado que exista um modelo avaliacao de período de experiência "Avaliação Experiência - 30 dias"
      Dado que exista um modelo avaliacao de período de experiência "Avaliação Experiência - 45 dias"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Aval. Desempenho > Cadastros > Avaliações de Desempenho/Acomp. do Período de Experiência"      
         E eu clico em editar "Avaliação Experiência - 30 dias"
         E eu seleciono "30 dias (30 dias)" de "periodoExperiencia"
         E eu clico no botão "Gravar" 

 @teste
  Cenário: Clonar um  Modelo de Avaliação 
      Dado que exista um periodo de experiencia "30 dias" de 30 dias
      Dado que exista um modelo avaliacao de período de experiência "Avaliação Experiência - 30 dias"
      Dado que exista um modelo avaliacao de período de experiência "Avaliação Experiência - 45 dias"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Aval. Desempenho > Cadastros > Avaliações de Desempenho/Acomp. do Período de Experiência"      
     Então eu clico na linha "Avaliação Experiência - 30 dias" da imagem "Clonar"
         E eu marco "Empresa Padrão"
         E eu clico no botão "Clonar"
         E eu devo ver "Avaliação Experiência - 30 dias (Clone)" 
     Então eu clico em excluir "Avaliação Experiência - 30 dias (Clone)"   
         E eu devo ver o alert do confirmar exclusão e clico no ok
         E eu devo ver "Modelo de avaliação excluído com sucesso."          