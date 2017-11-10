# language: pt

Funcionalidade: Clínicas e Médicos Autorizados
@testes
  Esquema do Cenario: Cadastro de Clínicas e Médicos Autorizados
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Clínicas e Médicos Autorizados" 
     Então eu clico no botão "Inserir"
         E eu preencho "Nome" com <Nome>
         E eu seleciono <Tipo> de "Tipo"
         E eu preencho <Identificação> com <Registro>
         E eu preencho o campo (JS) "Início do contrato" com "23/02/2011"
         E eu clico no botão "Gravar"
         E eu devo ver <Mensagem>

Exemplos:
    | Nome                    | Tipo           | Identificação | Registro             | Mensagem                         |
    | ""                      | "Selecione..." | ""            | ""                   | "Preencha os campos indicados."  |
    | "Médico Teste"          | "Selecione..." | ""            | ""                   | "Preencha os campos indicados."  |
    | "Médico Teste"          | "Médico"       | "CRM"         | "123456"             | "Clínicas e Médicos Autorizados" |
    | "Clínica Médica"        | "Clínica"      | "CNPJ"        | "33.617.843/0001-90" | "Clínicas e Médicos Autorizados" |
    | "Outras Especialidades" | "Outro"        | "Nome"        | "Médico Dentista"    | "Clínicas e Médicos Autorizados" |
     

@teste
  Cenário: Edição/Exclusão de Clínicas e Médicos Autorizados
    Dado que exista um clínica(médico) "Clínica Médica"
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Clínicas e Médicos Autorizados"
    Então eu devo ver o título "Clínicas e Médicos Autorizados"

    Entao eu clico em editar "Clínica Médica"
    E eu preencho "Nome" com "Médico Obstetra"
    E eu clico no botão "Gravar"

    Então eu clico em excluir "Médico Obstetra"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Clínica/Médico Autorizada(o) excluída(o) com sucesso."
