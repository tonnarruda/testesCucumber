# language: pt

Funcionalidade: Gerenciamento do Cadastro de Atitudes

Esquema do Cenario: Cadastro de Atitude
      Dado que exista a área organizacional "Administração"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Atitudes" 
     Então  eu clico no botão "Inserir"  
         E eu preencho "Nome" com <Atitude>
         E eu marco <Lotação>
         E eu preencho "Observação" com <Observação>
     Então eu clico no botão "Gravar"
         E eu devo ver <Mensagem>

Exemplos:
    | Atitude       | Observação                                                          | Lotação         | Mensagem                        |
    | ""            | ""                                                                  | ""              | "Preencha os campos indicados." |
    | "Organização" | "Será exigido que o Candidato/Colaborador seja bastante organizado" | "Administração" | "Atitudes"                      |

#----------------------------------------------------------------------------------------------------------------------------------------------------

  Cenário: Edição de Atitudes
      Dado que exista a área organizacional "Administração"
      Dado que exista a área organizacional "Administração > Desenvolvimento"
      Dado que exista a atitude "Organizdo"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Atitudes"
     Entao eu clico em editar "Organizdo"
         E eu devo ver o título "Editar Atitude"
         E eu preencho "Nome" com "Organizado"
         E eu marco "Administração > Desenvolvimento"
     Então eu clico no botão "Gravar"

#----------------------------------------------------------------------------------------------------------------------------------------------------

  Esquema do Cenario: Exclusão de Cadastro de Atitude
      Dado que exista um nivel de competencia "Bom"
      Dado que exista um historico de nivel de competencia na data "01/01/2010"
      Dado que exista uma configuracao de nivel de competencia com nivel "Bom" no historico do nivel de data "01/01/2010" na ordem 1
      Dado que exista a área organizacional "Administração"
      Dado que exista o cargo "Quality Assurance" na área organizacional "Administração"
      Dado que exista o cargo "Desenvolvedor" na área organizacional "Administração"
      Dado que exista o cargo "Gerente de Produto" na área organizacional "Administração"
      Dado que haja uma faixa salarial com id 3, nome "II", cargo_id 3
      Dado que exista a atitude "Organização"
      Dado que exista a atitude "Persistencia"
      Dado que exista a atitude "Resiliencia"
      Dado que exista uma configuracao de nivel de competencia "Bom" na atitude "Resiliencia" para a faixa salarial "II"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Atitudes"
     Entao eu clico em editar "Organização"
         E eu marco "Administração"
     Então eu clico no botão "Gravar"
    Quando eu acesso o menu "C&S > Cadastros > Cargos e Faixas"
         E eu clico em editar "Desenvolvedor"
         E eu marco o checkbox com name "atitudesCheck"       
         E eu clico no botão "Gravar"
    Quando eu acesso o menu "C&S > Cadastros > Atitudes"     
         E eu clico em excluir <Atitude>
         E eu devo ver o alert do confirmar exclusão e clico no ok
     Então eu devo ver <Mensagem>

Exemplos:
    | Atitude        | Mensagem                                                                                                          |
    | "Organização"  | "Não foi possível excluir a atitude."                                                                             |
    | "Persistencia" | "Atitude excluída com sucesso."                                                                                   |
    | "Resiliencia"  | "Não é possível excluir esta atitude pois possui dependência com o nível de competencia do cargo/faixa salarial." |