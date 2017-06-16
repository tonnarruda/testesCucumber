# language: pt

Funcionalidade: Modelos de Entrevistas de Desligamento

  Cenário: Cadastro de Modelos de Entrevistas de Desligamento Sem Pergunta Vinculada | Ativa |
       Dado que eu esteja logado com o usuário "SOS"
     Quando eu acesso o menu "Info. Funcionais > Cadastros > Modelos de Entrevistas de Desligamento"
      Então  eu clico no botão "Inserir"
          E eu preencho "Título" com "Entrevistas de Desligamento Sem Justa Causa"
          E eu preencho "Observação" com "Só deverá ser utilizada para desligamento sem justa causa"
          E eu clico no botão "Avancar"
      Então  eu clico no botão "Voltar"
          E eu clico no botão "Cancelar"
    Então eu devo ver o título "Modelos de Entrevistas de Desligamento"


  Cenário: Cadastro de Modelos de Entrevistas de Desligamento Sem Pergunta Vinculada | Inativa |
       Dado que eu esteja logado com o usuário "SOS"
     Quando eu acesso o menu "Info. Funcionais > Cadastros > Modelos de Entrevistas de Desligamento"
      Então  eu clico no botão "Inserir"
          E eu preencho "Título" com "Entrevistas de Desligamento Sem Justa Causa"
          E eu seleciono "Não" de "Ativa"
          E eu preencho "Observação" com "Só deverá ser utilizada para desligamento sem justa causa"
          E eu clico no botão "Avancar"
      Então  eu clico no botão "Voltar"
          E eu clico no botão "Cancelar"
    Então eu devo ver o título "Modelos de Entrevistas de Desligamento"


  Cenário: Cadastro de Modelos de Entrevistas de Desligamento | Completo - Pergunta Por Nota|
       Dado que eu esteja logado com o usuário "SOS"
     Quando eu acesso o menu "Info. Funcionais > Cadastros > Modelos de Entrevistas de Desligamento"
      Então  eu clico no botão "Inserir"
          E eu preencho "Título" com "Entrevistas de Desligamento Sem Justa Causa"
          E eu preencho "Observação" com "Só deverá ser utilizada para desligamento sem justa causa"
          E eu clico no botão "Avancar"
          E eu clico "Inserir pergunta aqui"
          E eu preencho "aspecto" com "Comportamental"
          E eu preencho "texto" com "Pergunta sobre o aspecto comportamental"
          E eu seleciono "Nota" de "Tipos de Respostas"
          E eu preencho "notaMinima" com "0"
          E eu preencho "notaMaxima" com "10"
          E eu marco "Solicitar comentário da resposta (especifique abaixo a solicitação)"
          E eu clico no botão "Gravar"
          


  Cenário: Cadastro de Modelos de Entrevistas de Desligamento | Completo - Pergunta Subjetiva|
       Dado que eu esteja logado com o usuário "SOS"
     Quando eu acesso o menu "Info. Funcionais > Cadastros > Modelos de Entrevistas de Desligamento"
      Então  eu clico no botão "Inserir"
          E eu preencho "Título" com "Entrevistas de Desligamento Sem Justa Causa"
          E eu preencho "Observação" com "Só deverá ser utilizada para desligamento sem justa causa"
          E eu clico no botão "Avancar"
          E eu clico "Inserir pergunta aqui"
          E eu preencho "aspecto" com "Comportamental"
          E eu preencho "texto" com "Pergunta sobre o aspecto comportamental"
          E eu seleciono "Subjetiva" de "Tipos de Respostas"
          E eu clico no botão "Gravar"



  Cenário: Cadastro de Modelos de Entrevistas de Desligamento | Completo - Pergunta Objetiva|
       Dado que eu esteja logado com o usuário "SOS"
     Quando eu acesso o menu "Info. Funcionais > Cadastros > Modelos de Entrevistas de Desligamento"
      Então  eu clico no botão "Inserir"
          E eu preencho "Título" com "Entrevistas de Desligamento Sem Justa Causa"
          E eu preencho "Observação" com "Só deverá ser utilizada para desligamento sem justa causa"
          E eu clico no botão "Avancar"
          E eu clico "Inserir pergunta aqui"
          E eu preencho "aspecto" com "Comportamental"
          E eu preencho "texto" com "Pergunta sobre o aspecto comportamental"
          E eu seleciono "Objetiva" de "Tipos de Respostas"
          E eu preencho "respostaObjetiva" com "Pergunta 1"
          E eu marco "Solicitar comentário da resposta (especifique abaixo a solicitação)"
          E eu clico no botão "Gravar"



  Cenário: Cadastro de Modelos de Entrevistas de Desligamento | Completo - Pergunta Multipla Escolha|
       Dado que eu esteja logado com o usuário "SOS"
     Quando eu acesso o menu "Info. Funcionais > Cadastros > Modelos de Entrevistas de Desligamento"
      Então  eu clico no botão "Inserir"
          E eu preencho "Título" com "Entrevistas de Desligamento Sem Justa Causa"
          E eu preencho "Observação" com "Só deverá ser utilizada para desligamento sem justa causa"
          E eu clico no botão "Avancar"
          E eu clico "Inserir pergunta aqui"
          E eu preencho "aspecto" com "Comportamental"
          E eu preencho "texto" com "Pergunta sobre o aspecto comportamental"
          E eu seleciono "Múltipla Escolha" de "Tipos de Respostas"
          E eu preencho "multiplaResposta" com "Pergunta 1"
          E eu marco "Solicitar comentário da resposta (especifique abaixo a solicitação)"
          E eu clico no botão "Gravar"

@teste
  Cenário: Cadastro de Modelos de Entrevistas de Desligamento | Completo - Usando todos os tipos|
       Dado que eu esteja logado com o usuário "SOS"
     Quando eu acesso o menu "Info. Funcionais > Cadastros > Modelos de Entrevistas de Desligamento"
      Então  eu clico no botão "Inserir"
          E eu preencho "Título" com "Entrevistas de Desligamento Sem Justa Causa"
          E eu preencho "Observação" com "Só deverá ser utilizada para desligamento sem justa causa"
          E eu clico no botão "Avancar"
          E eu clico "Inserir pergunta aqui"
          E eu preencho "aspecto" com "Comportamental"
          E eu preencho "texto" com "Pergunta sobre o aspecto comportamental"
          E eu seleciono "Subjetiva" de "Tipos de Respostas"
          E eu clico no botão "Gravar"          
          E eu clico "Inserir pergunta aqui"
          E eu preencho "aspecto" com "Comportamental"
          E eu preencho "texto" com "Pergunta sobre o aspecto comportamental"
          E eu seleciono "Múltipla Escolha" de "Tipos de Respostas"
          E eu preencho "multiplaResposta" com "Pergunta 1"
          E eu marco "Solicitar comentário da resposta (especifique abaixo a solicitação)"
          E eu clico no botão "Gravar"
          E eu clico no botão "Voltar"
          E eu clico "Inserir pergunta aqui"
          E eu preencho "aspecto" com "Comportamental"
          E eu preencho "texto" com "Pergunta sobre o aspecto comportamental"
          E eu seleciono "Nota" de "Tipos de Respostas"
          E eu preencho "notaMinima" com "0"
          E eu preencho "notaMaxima" com "10"
          E eu marco "Solicitar comentário da resposta (especifique abaixo a solicitação)"
          E eu clico no botão "Gravar"     
          E eu clico "Inserir pergunta aqui"
          E eu preencho "aspecto" com "Comportamental"
          E eu preencho "texto" com "Pergunta sobre o aspecto comportamental"
          E eu seleciono "Objetiva" de "Tipos de Respostas"
          E eu preencho "respostaObjetiva" com "Pergunta 1"
          E eu marco "Solicitar comentário da resposta (especifique abaixo a solicitação)"
          E eu clico no botão "Gravar"  
          E eu clico no botão "Voltar"


  Cenário: Editar Cadastro de Modelo de Entrevista de Desligamento
       Dado que exista um modelo de entrevista de desligamento "Entrevista Teste" com a pergunta "Pergunta Teste"
       Dado que eu esteja logado com o usuário "SOS"
     Quando eu acesso o menu "Info. Funcionais > Cadastros > Modelos de Entrevistas de Desligamento"
      Entao eu clico em editar "Entrevista Teste"
          E eu preencho "Título" com "Entrevista de Desligamento"
          E eu clico no botão "Gravar"




  Cenário: Inativar Modelo de Entrevista de Desligamento
       Dado que exista um modelo de entrevista de desligamento "Entrevista Teste" com a pergunta "Pergunta Teste"
       Dado que eu esteja logado com o usuário "SOS"
     Quando eu acesso o menu "Info. Funcionais > Cadastros > Modelos de Entrevistas de Desligamento"
      Entao eu clico em editar "Entrevista Teste"
          E eu seleciono "Não" de "Ativa"
          E eu clico no botão "Gravar"



  Cenário: Excluir Perguntas do Modelo de Entrevista de Desligamento
       Dado que exista um modelo de entrevista de desligamento "Entrevista Teste" com a pergunta "Pergunta Teste"
       Dado que eu esteja logado com o usuário "SOS"
     Quando eu acesso o menu "Info. Funcionais > Cadastros > Modelos de Entrevistas de Desligamento"
      Entao eu clico na linha "Entrevista Teste" da imagem "Questionário da entrevista"
          E eu clico na imagem com o título "Excluir"
          E eu devo ver o alert do confirmar exclusão e clico no ok
      Então eu devo ver "Pergunta excluída com sucesso."
          E eu clico no botão "Voltar"


  
  Cenário: Clonar /Excluir clone do Modelo de Entrevista de Desligamento
       Dado que exista um modelo de entrevista de desligamento "Entrevista Teste" com a pergunta "Pergunta Teste"
       Dado que eu esteja logado com o usuário "SOS"
     Quando eu acesso o menu "Info. Funcionais > Cadastros > Modelos de Entrevistas de Desligamento"
      Entao eu clico na linha "Entrevista Teste" da imagem "Clonar"
          E eu marco "Empresa Padrão"
          E eu clico no botão "Clonar"
      Então eu clico em excluir "Entrevista Teste (Clone)" 
          E eu devo ver o alert do confirmar exclusão e clico no ok
      Então eu devo ver "Entrevista excluída com sucesso."


  Cenário: Excluir  Modelo de Entrevista de Desligamento
       Dado que exista um modelo de entrevista de desligamento "Entrevista Teste" com a pergunta "Pergunta Teste"
       Dado que eu esteja logado com o usuário "SOS"
     Quando eu acesso o menu "Info. Funcionais > Cadastros > Modelos de Entrevistas de Desligamento"
      Então eu clico em excluir "Entrevista Teste" 
          E eu devo ver o alert do confirmar exclusão e clico no ok
      Então eu devo ver "Entrevista excluída com sucesso."


  Cenário: Inserir Aspectos noModelo de Entrevista de Desligamento já cadastrado
       Dado que exista um modelo de entrevista de desligamento "Entrevista Teste" com a pergunta "Pergunta Teste"
       Dado que eu esteja logado com o usuário "SOS"
     Quando eu acesso o menu "Info. Funcionais > Cadastros > Modelos de Entrevistas de Desligamento"
      Entao eu clico na linha "Entrevista Teste" da imagem "Aspectos da entrevista"
          E eu clico no botão "Inserir"
          E eu preencho "aspecto.nome" com "Aspecto Comportamental"
          E eu clico no botão "Gravar"
          E eu clico no botão "Inserir"
          E eu clico no botão "Cancelar"
          E eu clico no botão "Voltar"