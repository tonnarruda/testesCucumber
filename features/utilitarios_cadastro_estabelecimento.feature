# language: pt

Funcionalidade: Cadastrar Estabelecimento

Esquema do Cenario: Edição de Cadastro de Estabelecimentos
    Dado que eu esteja logado com o usuário "SOS" 
  Quando eu acesso o menu "Utilitários > Cadastros > Estabelecimentos" 
       E eu clico em editar <Estabelecimento> 
       E eu preencho <Item> com <Descrição>
   Então eu clico no botão "Gravar"
       E eu devo deslogar do sistema    

  Exemplos:
    | Estabelecimento          | Item         | Descrição                   |
    | "Estabelecimento Padrão" | "Nome"       | "Estabelecimento Matriz"    |
    | "Estabelecimento Padrão" | "Logradouro" | "Avenida Washington Soares" |
    | "Estabelecimento Padrão" | "Bairro"     | "Eng. Luciano Cavalcante"   |

#------------------------------------------------------------------------------------------------------------------------------------------------------

Esquema do Cenario: Exclusão de Cadastro de Estabelecimentos
    Dado que exista o estabelecimento "Filial São Paulo"
    Dado que exista um ambiente "Cozinha" com o risco "Queimadura"
    Dado que eu esteja logado com o usuário "SOS" 
  Quando eu acesso o menu "Utilitários > Cadastros > Estabelecimentos" 
       E eu clico em excluir <Estabelecimento> 
   Então eu devo ver o alert "Confirma exclusão?" e clico no ok
       E eu devo ver <Mensagem> 
       E eu devo deslogar do sistema 

  Exemplos:
    | Estabelecimento          | Mensagem                                                      |
    | "Estabelecimento Padrão" | "Entidade estabelecimento possui dependências em ambiente."   |
    | "Filial São Paulo"       | "Estabelecimento excluído com sucesso."                       |

#------------------------------------------------------------------------------------------------------------------------------------------------------

Esquema do Cenario: Gerenciamento do Cadastro de Estabelecimentos
    Dado que eu esteja logado com o usuário "SOS" 
  Quando eu acesso o menu "Utilitários > Cadastros > Estabelecimentos" 
       E eu clico no botão "Inserir" 
       E eu preencho "Nome" com <Estabelecimento>
       E eu preencho "Logradouro" com <Endereco>
       E eu preencho "num" com <Num>
       E eu seleciono "CE" de "Estado"
       E eu preencho "Bairro" com <Bairro>  
       E eu preencho campo pelo id "complementoCnpj" com <CNPJ>     
   Então eu clico no botão "Gravar" 
       E eu devo ver <Mensagem> 
       E eu devo deslogar do sistema    

  Exemplos:
    | Estabelecimento | CNPJ     | Endereco             | Num    | Bairro                    | Mensagem                                  |
    | "Matriz"        | "0000"   | "Rua Antonio Fortes" | "330"  | "Eng. Luciano Cavalcante" | "CNPJ já existe."                         |
    | ""              | ""       | ""                   | ""     | ""                        | "Complemento do CNPJ deve ter 4 dígitos." |
    | ""              | "0002"   | ""                   | ""     | ""                        | "Preencha os campos indicados."           |
    | "Filial"        | "0002"   | ""                   | ""     | ""                        | "Preencha os campos indicados."           |
    | "Filial"        | "0002"   | "Rua Antonio Fortes" | ""     | ""                        | "Preencha os campos indicados."           |
    | "Filial"        | "0002"   | "Rua Antonio Fortes" | "330"  | ""                        | "Preencha os campos indicados."           |
    | "Filial"        | "0002"   | "Rua Antonio Fortes" | "330"  | "Eng. Luciano Cavalcante" | ""                                        |
