# language: pt

Funcionalidade: Exames

  Esquema do Cenario: Cadastro de Exames  
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Exames"
         E eu clico no botão "Inserir"
     Entao eu preencho "Nome" com <Exame>
         E eu preencho "Periodicidade (em meses)" com <Periodo>
         E eu clico no botão "Gravar"
     Então eu devo ver <Mensagem>

  Exemplos:
    | Exame             | Periodo | Mensagem                        |
    | ""                | ""      | "Preencha os campos indicados." |
    | "Exame de Sangue" | ""      | "Preencha os campos indicados." |
    | ""                | "12"    | "Preencha os campos indicados." |
    | "Exame de Sangue" | "12"    | "Exames"                        |

@tese
  Esquema do Cenario: Exclusão de Exames  
      Dado que exista um medico coordenador "Dr. João Holanda"
      Dado que exista um exame "Sangue"
      Dado que exista um exame "Urina"
      Dado que exista ums solicitação de exame para o colaborador "Melinda May"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Exames"
     Então eu clico em excluir <Exame>
         E eu devo ver o alert do confirmar exclusão e clico no ok
     Então eu devo ver <Mensagem> 
     

  Exemplos:
    | Exame   | Mensagem                                                                          |
    | "Sangue"| "Entidade exame possui dependências em exames da solicitação/atendimento médico." |
    | "Urina" | "Exame excluído com sucesso."                                                     |


  Cenário: Cadastro de Exames
    Dado que exista um exame "Exame de Sangue"
    Dado que eu esteja logado com o usuário "SOS"    
    Quando eu acesso o menu "SESMT > Cadastros > Exames" 
    Entao eu clico em editar "Exame de Sangue"
    E eu preencho "Nome" com "Exame de Urina"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Exames"
