# language: pt

Funcionalidade: Cadastrar Avaliações de Desempenho

  Cenário: Validar Campos Obrigatórios no Cadastro de Avaliações de Desempenho
      Dado que exista um modelo avaliacao desempenho "Avaliacao de Desempenho"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Avaliações de Desempenho"
         E eu clico no botão "Inserir"
     Então eu devo ver o título "Inserir Avaliação de Desempenho"
         E eu clico no botão "Avancar"
     Então eu devo ver o alert do valida campos e clico no ok
         E eu preencho "Título" com "Avaliação de Desempenho Grupo Fortes"
         E eu clico no botão "Avancar"
     Então eu devo ver o alert do valida campos e clico no ok
         E eu preencho o campo (JS) "inicio" com "01/06/2017"
         E eu clico no botão "Avancar"
     Então eu devo ver o alert do valida campos e clico no ok
         E eu preencho o campo (JS) "fim" com "30/06/2017"
         E eu clico no botão "Avancar"
     Então eu devo ver o alert do valida campos e clico no ok
         E eu seleciono "Avaliacao de Desempenho" de "modelo"
         E eu clico no botão "Avancar"
     Então eu devo ver o alert do valida campos e clico no ok
         E eu seleciono "Não" de "anonima"
         E eu clico no botão "Avancar"
     Então eu devo ver o alert do valida campos e clico no ok
         E eu seleciono "Não" de "permiteAutoAvaliacao"
         E eu clico no botão "Avancar"

#-------------------------------------------------------------------------------------------------------------------------------------------

  Cenário: Cadastrar de Avaliações de Desempenho
      Dado que exista um modelo avaliacao desempenho "Avaliacao de Desempenho"
      Dado que exista um colaborador "Samuel Pinheiro", da area "Desenvolvimento", com o cargo "Desenvolvedor Java" e a faixa salarial "3"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Avaliações de Desempenho"
         E eu clico no botão "Inserir"
         E eu preencho "Título" com "Avaliação de Desempenho Grupo Fortes"
         E eu preencho o campo (JS) "inicio" com "01/06/2017"
         E eu preencho o campo (JS) "fim" com "30/06/2017"
         E eu seleciono "Avaliacao de Desempenho" de "modelo"
         E eu seleciono "Não" de "anonima"
         E eu seleciono "Sim" de "permiteAutoAvaliacao"
         E eu clico no botão "Avancar"
     Então eu clico no botão de Id "inserir_Avaliador"
         E eu espero 1 segundos
         E eu devo ver "Inserir Avaliador"
         E eu clico no botão "Pesquisar"
         E eu marco "Samuel"
         E eu clico no botão "Gravar"
         E eu espero 1 segundos         
     Entao eu clico no botão de Id "inserir_Avaliado"
         E eu clico no botão "Pesquisar"
         E eu marco "Samuel"
         E eu clico no botão "Gravar"
         E eu espero 1 segundos
         E eu clico no botão de class "nome"
         E eu espero 1 segundos
         E eu clico no botão de Id "relacionar_selecionados"
         E eu espero 1 segundos
         E eu clico no botão de class "for-all"
         E eu espero 1 segundos    
     Entao eu clico no botão de Id "btnGravar"
         E eu devo ver "Gravado com sucesso"

#-------------------------------------------------------------------------------------------------------------------------------------------

  Cenário: Editar Cadastro de Avaliações de Desempenho
      Dado que exista um modelo avaliacao desempenho "Avaliacao de Desempenho"
      Dado que exista uma avaliacao desempenho "Avaliação Grupo Fortes" no período de "01/06/2017" até "30/06/2017"
      Dado que exista um colaborador "Samuel Pinheiro", da area "Desenvolvimento", com o cargo "Desenvolvedor Java" e a faixa salarial "3"
      Dado que eu esteja logado com o usuário "SOS"     
    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Avaliações de Desempenho"
     Então eu clico em editar "Avaliação Grupo Fortes"
         E eu preencho "Título" com "Avaliação de Desempenho - Desenvolvimento" 
         E eu clico no botão "Gravar"

#-------------------------------------------------------------------------------------------------------------------------------------------

  Cenário: Excluir Cadastro de Avaliações de Desempenho
      Dado que exista uma avaliacao desempenho "Avaliação Grupo Fortes" no período de "01/06/2017" até "30/06/2017"
      Dado que eu esteja logado com o usuário "SOS"     
    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Avaliações de Desempenho"
     Então eu clico em excluir "Avaliação Grupo Fortes"
        E eu devo ver o alert do confirmar exclusão e clico no ok
        E eu devo ver "Avaliação de desempenho excluída com sucesso."

#-------------------------------------------------------------------------------------------------------------------------------------------

  Cenário: Clonar Cadastro de Avaliações de Desempenho
      Dado que exista uma avaliacao desempenho "Avaliação Grupo Fortes" no período de "01/06/2017" até "30/06/2017"
      Dado que eu esteja logado com o usuário "SOS"     
    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Avaliações de Desempenho"
     Então eu clico na linha "Avaliação Grupo Fortes" da imagem "Clonar"
        E eu marco "Empresa Padrão"
        E eu clico no botão "Clonar"
        E eu devo ver "Avaliação de desempenho clonada com sucesso."
    Então eu clico em excluir "Avaliação Grupo Fortes (Clone)"    
        E eu devo ver o alert do confirmar exclusão e clico no ok
        E eu devo ver "Avaliação de desempenho excluída com sucesso."  

#-------------------------------------------------------------------------------------------------------------------------------------------

  Cenário: Incluir Empregados no Cadastro de Avaliações de Desempenho
      Dado que exista uma avaliacao desempenho "Avaliação Grupo Fortes" no período de "01/06/2017" até "30/06/2017"
      Dado que exista um colaborador "Samuel Pinheiro", da area "Desenvolvimento", com o cargo "Desenvolvedor Java" e a faixa salarial "3"
      Dado que eu esteja logado com o usuário "SOS"     
    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Avaliações de Desempenho"
     Então eu clico na linha "Avaliação Grupo Fortes" da imagem "Participantes"
     Entao eu clico no botão de Id "inserir_Avaliado"
         E eu clico no botão "Pesquisar"
         E eu marco "Samuel"
         E eu clico no botão "Gravar"
         E eu espero 1 segundos
         E eu clico no botão de class "nome"
         E eu espero 1 segundos
         E eu clico no botão de Id "relacionar_selecionados"
         E eu espero 1 segundos
         E eu clico no botão de class "for-all"    
     Então eu clico no botão de class "ui-button-text"
         E eu clico no botão de Id "inserir_Avaliador"
         E eu espero 1 segundos
         E eu devo ver "Inserir Avaliador"
         E eu clico no botão "Pesquisar"
         E eu marco "Samuel"
         E eu clico no botão "Gravar"
         E eu espero 1 segundos  
     Então eu clico no botão de Id "relacionar_selecionados"
         E eu espero 1 segundos
         E eu clico no botão de class "for-all"
         E eu espero 1 segundos    
     Entao eu clico no botão de Id "btnGravar"
         E eu devo ver "Gravado com sucesso"

#-------------------------------------------------------------------------------------------------------------------------------------------

  Cenário: Liberar Avaliação de Desempenho sem empregados Associados
      Dado que exista uma avaliacao desempenho "Avaliação Grupo Fortes" no período de "01/06/2017" até "30/06/2017"
      Dado que exista um colaborador "Samuel Pinheiro", da area "Desenvolvimento", com o cargo "Desenvolvedor Java" e a faixa salarial "3"
      Dado que eu esteja logado com o usuário "SOS"     
    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Avaliações de Desempenho"
     Então eu clico na linha "Avaliação Grupo Fortes" da imagem "Liberar"
         E eu devo ver o alert do confirmar e clico no ok
     Então eu devo ver "Não foi possível liberar esta avaliação: Nenhum avaliador possui colaboradores para avaliar."

#------------------------------------------------------------------------------------------------------------------------------------------

  Cenário: Liberar/Bloquear Avaliação de Desempenho com empregados Associados
      Dado que exista uma avaliacao desempenho "Avaliação Grupo Fortes" no período de "01/06/2017" até "30/06/2017"
      Dado que eu esteja logado com o usuário "SOS"     
    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Avaliações de Desempenho"
     Então eu clico na linha "Avaliação Grupo Fortes" da imagem "Liberar"
         E eu devo ver o alert do confirmar e clico no ok
         E eu devo ver "Não foi possível liberar esta avaliação: Nenhum avaliador possui colaboradores para avaliar."

#-------------------------------------------------------------------------------------------------------------------------------------------

  Cenário: Liberar/Bloquear Avaliação de Desempenho com empregados Associados
      Dado que exista uma avaliacao desempenho "Avaliação Grupo Fortes" no período de "01/06/2017" até "30/06/2017"
      Dado que exista um colaborador "Samuel Pinheiro", da area "Desenvolvimento", com o cargo "Desenvolvedor Java" e a faixa salarial "3"
      Dado que eu esteja logado com o usuário "SOS"     
    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Avaliações de Desempenho"
    Então eu clico na linha "Avaliação Grupo Fortes" da imagem "Participantes"
Então eu clico no botão de Id "inserir_Avaliador"
         E eu espero 1 segundos
         E eu devo ver "Inserir Avaliador"
         E eu clico no botão "Pesquisar"
         E eu marco "Samuel"
         E eu clico no botão "Gravar"
         E eu espero 1 segundos         
     Entao eu clico no botão de Id "inserir_Avaliado"
         E eu clico no botão "Pesquisar"
         E eu marco "Samuel"
         E eu clico no botão "Gravar"
         E eu espero 1 segundos
         E eu clico no botão de class "nome"
         E eu espero 1 segundos
         E eu clico no botão de Id "relacionar_selecionados"
         E eu espero 1 segundos
         E eu clico no botão de class "for-all"
         E eu espero 1 segundos    
     Entao eu clico no botão de Id "btnGravar"
         E eu devo ver "Gravado com sucesso"
         E eu clico no botão "Voltar"
     Então eu clico na linha "Avaliação Grupo Fortes" da imagem "Liberar"
         E eu devo ver o alert do confirmar e clico no ok
         E eu devo ver "Avaliação liberada com sucesso."
     Então eu clico na linha "Avaliação Grupo Fortes" da imagem "Bloquear"
         E eu devo ver o alert do confirmar e clico no ok
     Então eu devo ver "Avaliação bloqueada com sucesso."

#-------------------------------------------------------------------------------------------------------------------------------------------

  Cenário: Enviar Email de Avaliação de Desempenho para empregados
      Dado que exista uma avaliacao desempenho "Avaliação Grupo Fortes"
      Dado que eu esteja logado com o usuário "SOS"     
    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Avaliações de Desempenho"
     Então eu clico na linha "Avaliação Grupo Fortes" da imagem "Enviar e-mail de Lembrete"
         E eu devo ver o alert do confirmar e clico no ok
     Então eu devo ver "Email(s) enviado(s) com sucesso."

#-------------------------------------------------------------------------------------------------------------------------------------------
@test
  Cenário: Liberar Avaliação de Desempenho em lote sem participantes
      Dado que exista uma avaliacao desempenho "Avaliação Grupo Fortes" no período de "01/06/2017" até "30/06/2017"
      Dado que exista uma avaliacao desempenho "Avaliação Invertida" no período de "01/06/2017" até "30/06/2017"
      Dado que eu esteja logado com o usuário "SOS"     
    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Avaliações de Desempenho"
         E eu clico no botão de class "btnLiberarAvalEmLote"
         E eu clico no botão pesquisar na tela de liberar avaliação em lote
         E eu clico em marcar todas as avaliações e liberar
     Então eu devo ver "Não foi possível realizar a operação 'Liberar avaliações em lote': "

#-------------------------------------------------------------------------------------------------------------------------------------------

  Cenário: Pesquisar Avaliação de Desempenho 
      Dado que exista uma avaliacao desempenho "Avaliação Grupo Fortes" no período de "01/06/2017" até "30/06/2017"
      Dado que exista uma avaliacao desempenho "Avaliação Invertida" no período de "01/05/2017" até "31/05/2017"
      Dado que eu esteja logado com o usuário "SOS"     
    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Avaliações de Desempenho"
         E eu clico "Exibir Filtro"
         E eu preencho o campo (JS) "periodoInicial" com "01/06/2017"
         E eu preencho o campo (JS) "periodoFinal" com "30/06/2017"
     Então eu clico no botão "Pesquisar"
         E eu não devo ver "Avaliação Invertida"
         E eu devo ver "Avaliação Grupo Fortes"
         E eu preencho o campo (JS) "periodoInicial" com "01/05/2017"
         E eu preencho o campo (JS) "periodoFinal" com "31/05/2017"
     Então eu clico no botão "Pesquisar"
         E eu devo ver "Avaliação Invertida"
         E eu não devo ver "Avaliação Grupo Fortes"
         E eu preencho o campo (JS) "periodoInicial" com "01/01/2017"
         E eu preencho o campo (JS) "periodoFinal" com "31/01/2017"
     Então eu clico no botão "Pesquisar"
         E eu não devo ver "Avaliação Invertida"
         E eu não devo ver "Avaliação Grupo Fortes"
         E eu devo deslogar do sistema

#---------------------------------------------------------------------------------------------------------------------------------------         
@teste
  Cenário: Inclusão e remoção de Avaliado
      Dado que exista uma avaliacao desempenho "Avaliação de Desempenho" no período de "01/10/2017" até "31/10/2017"
      Dado que existam "3" colaboradores, na area "Desenvolvimento", com o cargo "Desenvolvedor" e a faixa salarial "1A"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Avaliações de Desempenho"
         E eu clico em participantes de "Avaliação de Desempenho"
     Então eu clico no botão de Id "inserir_Avaliado"
         E eu espero 1 segundos
         E eu clico no botão "Pesquisar"
         E eu marco "Colaborador Teste 0"
     Então eu clico no botão "Gravar"  
         E eu clico no botão de class "nome"  
         E eu clico em remover colaboradores selecionados da coluna avaliados
     Entao eu clico no botão de Id "btnGravar"   
         E eu devo ver "Gravado com sucesso"

#-------------------------------------------------------------------------------------------------------------------------------------------------
@testes
  Cenário: Inclusão e remoção de Avaliadores
      Dado que exista uma avaliacao desempenho "Avaliação de Desempenho" no período de "01/10/2017" até "31/10/2017"
      Dado que existam "3" colaboradores, na area "Desenvolvimento", com o cargo "Desenvolvedor" e a faixa salarial "1A"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Avaliações de Desempenho"
         E eu clico em participantes de "Avaliação de Desempenho"
     Então eu clico no botão de Id "inserir_Avaliador"
         E eu espero 1 segundos
         E eu clico no botão "Pesquisar"
         E eu marco "Colaborador Teste 0"
     Então eu clico no botão "Gravar"  
         E eu clico no botão de class "nome"  
         E eu clico em remover colaboradores selecionados da coluna avaliadores
     Entao eu clico no botão de Id "btnGravar"   
         E eu devo ver "Gravado com sucesso"

#-------------------------------------------------------------------------------------------------------------------------------------------------

  Cenário: Inclusão de avaliados e  avaliadores na Avaliação de Desempenho
      Dado que exista uma avaliacao desempenho "Avaliação de Desempenho" no período de "01/10/2017" até "31/10/2017"
      Dado que existam "15" colaboradores, na area "Desenvolvimento", com o cargo "Desenvolvedor" e a faixa salarial "1A"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Avaliações de Desempenho"
         E eu clico em participantes de "Avaliação de Desempenho"
     Então eu clico no botão de Id "inserir_Avaliador"
         E eu clico no botão "Pesquisar"
         E eu marco "Colaborador Teste 0"
         E eu marco "Colaborador Teste 1"
         E eu marco "Colaborador Teste 2"
     Então eu clico no botão "Gravar"
     Então eu clico no botão de Id "inserir_Avaliado"
         E eu espero 1 segundos
         E eu clico no botão "Pesquisar"
         E eu marcos todos os colaboladores
     Então eu clico no botão "Gravar"
         E eu espero 1 segundos
         E eu seleciono todos os avaliados
         E eu espero 1 segundos
         E eu clico no botão de Id "relacionar_selecionados"
         E eu espero 1 segundos
         E eu clico no botão de class "for-all"
         E eu espero 10 segundos
     Entao eu clico no botão de Id "btnGravar"
         E eu devo ver "Gravado com sucesso"

#-------------------------------------------------------------------------------------------------------------------------------------------------
@teste
  Cenário: Inclusão de avaliados e avaliadores na Avaliação de Desempenho
      Dado que exista uma avaliacao desempenho "Avaliação de Desempenho" no período de "01/10/2017" até "31/10/2017"
      Dado que existam "501" colaboradores, na area "Desenvolvimento", com o cargo "Desenvolvedor" e a faixa salarial "1A"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Avaliações de Desempenho"
         E eu clico em participantes de "Avaliação de Desempenho"
     Então eu clico no botão de Id "inserir_Avaliador"
         E eu clico no botão "Pesquisar"
         E eu marco "Colaborador Teste 0"
         E eu marco "Colaborador Teste 1"
         E eu marco "Colaborador Teste 10"
         E eu marco "Colaborador Teste 100"
         E eu marco "Colaborador Teste 101"
     Então eu clico no botão "Gravar"
     Então eu clico no botão de Id "inserir_Avaliado"
         E eu espero 1 segundos
         E eu clico no botão "Pesquisar"
         E eu espero 2 segundos
         E eu marcos todos os colaboladores
     Então eu clico no botão "Gravar"
         E eu espero 1 segundos
         E eu seleciono todos os avaliados
         E eu espero 1 segundos
         E eu clico no botão de Id "relacionar_selecionados"
         E eu espero 1 segundos
         E eu clico no botão de class "for-all"
        E eu devo ver "Não é possível realizar esse procedimento"

#-------------------------------------------------------------------------------------------------------------------------------------------------

  Cenário: Inclusão de avaliados e avaliadores na Avaliação de Desempenho
      Dado que exista uma avaliacao desempenho "Avaliação de Desempenho" no período de "01/10/2017" até "31/10/2017"
      Dado que existam "5" colaboradores, na area "Desenvolvimento", com o cargo "Desenvolvedor" e a faixa salarial "1A"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Avaliações de Desempenho"
         E eu clico em participantes de "Avaliação de Desempenho"
     Então eu clico no botão de Id "inserir_Avaliador"
         E eu clico no botão "Pesquisar"
         E eu marcos todos os colaboladores
     Então eu clico no botão "Gravar"
     Então eu clico no botão de Id "inserir_Avaliado"
         E eu espero 1 segundos
         E eu clico no botão "Pesquisar"
         E eu marcos todos os colaboladores
     Então eu clico no botão "Gravar"
         E eu espero 1 segundos
         E eu seleciono todos os avaliados
         E eu espero 1 segundos
         E eu clico no botão de Id "relacionar_selecionados"
         E eu espero 1 segundos
         E eu clico no botão de class "for-all"
         E eu espero 5 segundos
      Entao eu clico no botão de Id "btnGravar"
          E eu devo ver "Gravado com sucesso"

#-------------------------------------------------------------------------------------------------------------------------------------------------
