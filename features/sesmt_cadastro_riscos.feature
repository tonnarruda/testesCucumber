# language: pt

Funcionalidade: Riscos
#Refatorar todo o teste
#Alterar Teste de geração do PPP quando existe um risco na SEP e não existe medição de risco (Config da empresa  por Ambiente)
#Alterar Teste de geração do PPP quando existe um risco na SEP (Config da empresa por Função)
  Esquema do Cenario: Cadastro de Riscos  
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Riscos"
         E eu clico no botão "Inserir"
         E eu devo ver "Ajuda eSocial"
         E eu clico no botão do dialog "Fechar"
     Então eu preencho "Nome" com <Risco>
         E eu seleciono <TipoEsocial> de "Tipo de Risco eSocial"
     Então eu clico selecione um fator de risco    
         E eu clico no botão "Gravar"
     Então eu devo ver <Mensagem>    

  Exemplos:
    | Risco      | Tipo             | Mensagem                        |
    | ""         | "[Selecione...]" | "Preencha os campos indicados." |
    | "Queda"    | "Físico"         | "Riscos"                        |

#-----------------------------------------------------------------------------------------------------------------

  Esquema do Cenario: Exclusão de Cadastro de Riscos  
      Dado que exista um risco "Queimadura"
      Dado que exista um ambiente "Cozinha" com o risco "Queda"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Riscos"
     Entao eu clico em excluir <Risco>
         E eu devo ver o alert do confirmar exclusão e clico no ok
         E eu devo ver <Mensagem>

  Exemplos:
    | Risco        | Mensagem                                                                       |
    | "Queimadura" | "Risco excluído com sucesso."                                                  |
    | "Queda"      | "O risco não pode ser excluído, pois possui dependência com outros cadastros." |    

#-----------------------------------------------------------------------------------------------------------------

  Cenário: Edição do Cadastro de Riscos
      Dado que exista um risco "Queimadura"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Riscos"
     Entao eu clico em editar "Queimadura"
         E eu preencho "Nome" com "Queimadura Grave"
         E eu clico no botão "Gravar"