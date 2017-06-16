# language: pt

Funcionalidade: Dias de Período de Acompanhamento de Experiência

  Cenário: Validar Campos Obrigatórios no Cadastro de Período de Acompanhamento de Experiência
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Aval. Desempenho > Cadastros > Dias do Acompanhamento do Período de Experiência"
         E eu clico no botão "Inserir"
         E eu clico no botão "Gravar"
     Então eu devo ver o alert do valida campos e clico no ok
         E eu clico no botão "Voltar"

#-------------------------------------------------------------------------------------------------------------------------------

  Cenário: Cadastro de Período de Acompanhamento de Experiência
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Aval. Desempenho > Cadastros > Dias do Acompanhamento do Período de Experiência"
         E eu clico no botão "Inserir"
         E eu preencho "Qtd. de Dias" com "30"
         E eu preencho "Descrição" com "Avaliação de 30 dias"
         E eu seleciono "Sim" de "ativo"
         E eu clico no botão "Gravar"
         
#-------------------------------------------------------------------------------------------------------------------------------

  Cenário: Editar Cadastro de Período de Acompanhamento de Experiência
      Dado que exista um periodo de experiencia "30 dias" de 30 dias
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Aval. Desempenho > Cadastros > Dias do Acompanhamento do Período de Experiência"
     Entao eu clico em editar "30 dias"
         E eu preencho "Qtd. de Dias" com "45"
         E eu clico no botão "Gravar"

#-------------------------------------------------------------------------------------------------------------------------------
 
  Cenário: Excluir Cadastro de Período de Acompanhamento de Experiência
      Dado que exista um periodo de experiencia "30 dias" de 30 dias
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Aval. Desempenho > Cadastros > Dias do Acompanhamento do Período de Experiência"
     Entao eu clico em excluir "30 dias"
         E eu devo ver o alert do confirmar exclusão e clico no ok
     Então eu devo ver "Período de Acompanhamento de Experiência excluído com sucesso."

#-------------------------------------------------------------------------------------------------------------------------------

  Cenário: Excluir Cadastro de Período de Acompanhamento de Experiência Associado a Modelos de Avaliação
      Dado que exista um periodo de experiencia "30 dias" de 30 dias
      Dado que exista um modelo avaliacao de período de experiência "Avaliação Experiência - 30 dias"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Aval. Desempenho > Cadastros > Avaliações de Desempenho/Acomp. do Período de Experiência"      
         E eu clico em editar "Avaliação Experiência - 30 dias"
         E eu seleciono "30 dias (30 dias)" de "periodoExperiencia"
         E eu clico no botão "Gravar" 
    Quando eu acesso o menu "Aval. Desempenho > Cadastros > Dias do Acompanhamento do Período de Experiência"
     Entao eu clico em excluir "30 dias"
         E eu devo ver o alert do confirmar exclusão e clico no ok
     Então eu devo ver "Entidade período de experiência possui dependências em avaliação."