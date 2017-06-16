# language: pt

Funcionalidade: Emissão de Relatório de Resultado das Entrevistas

  Cenário: Validar Campos Obrigatórios do Relatório de desligamentos
      Dado que exista um modelo de entrevista de desligamento "Sem Justa Causa" com a pergunta "Foi demitido Por que?"
      Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "Info. Funcionais > Relatórios > Resultados das Entrevistas"
         E eu clico no botão "Relatorio"
     Então eu devo ver o alert "Preencha os campos indicados." e clico no ok
          E eu seleciono "Sem Justa Causa" de "entrevista"
          E eu clico no botão "Relatorio"
     Então eu devo ver "Nenhuma pergunta foi respondida."

#------------------------------------------------------------------------------------------------------------

       


         