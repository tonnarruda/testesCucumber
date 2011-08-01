# encoding: utf-8
# IMPORTANT: This file is generated by cucumber-rails - edit at your own peril.
# It is recommended to regenerate this file in the future when you upgrade to a 
# newer version of cucumber-rails. Consider adding your own code to a new file 
# instead of editing this one. Cucumber will automatically load all features/**/*.rb
# files.

require File.expand_path(File.join(File.dirname(__FILE__), "..", "support", "paths"))

Dado /^que eu esteja logado$/ do
  unless page.has_selector?('.saudacao')
    # Evita problema quando o firefox eh instanciado com a janela menor do que o necessario
    page.execute_script("window.resizeTo(screen.width, screen.height);window.moveTo(0,0);window.focus()")
    Dado %{que eu esteja na pagina de login}
    E %{eu preencho "username" com "fortes"}
    E %{eu preencho "password" com "1234"}
    E %{eu clico em "Entrar"}
    Então %{eu devo ver "Bem-vindo(a)"}
    page.execute_script("$('#splash').dialog('close');")
  end
end

Quando /^eu acesso o menu "([^"]*)"$/ do |menu_path|
  items = menu_path.strip.split(/\s*>\s*/)
  menu_master = items.shift;
  link = find("#menuDropDown > li > a:contains('" + menu_master + "')")
  items.each do |item|
     link = link.find(:xpath, "../ul/li/a[text()='" + item + "']")
  end
  page.execute_script("window.location = '#{link[:href]}'")
end

Quando /^eu clico em "Entrar"?$/ do
    find('.btnEntrar').click
end

Quando /^eu clico no botão "([^"]*)"$/ do |text|
    find('.btn' + text).click
end

Quando /^eu clico em excluir "([^"]*)"$/ do |text|
  find(:xpath, "//td[contains(text(), '#{text}')]/../td/a/img[@title='Excluir']").click
  #find(:xpath, "//td[text()='#{text}']/../td")
end

Quando /^eu clico em editar "([^"]*)"$/ do |text|
  find(:xpath, "//td[contains(text(), '#{text}')]/../td/a/img[@title='Editar']").click
end

Quando /^eu clico na linha "([^"]*)" da imagem "([^"]*)"$/ do |desc, img|
  find(:xpath, "//td[contains(text(), '#{desc}')]/../td/a/img[@title='#{img}']").click
end

Quando /^eu clico na imagem "([^"]*)" da pergunta "([^"]*)"$/ do |img, titulo|
  find(:xpath, "//p[contains(text(), '#{titulo}')]/../div[@class='acaoPerguntas']/a/img[@title='#{img}']").click
end

Quando /^eu clico na imagem com o título "([^"]*)"$/ do |titulo|
  find(:xpath, "//img[@title='#{titulo}']").click
end

Então /^eu devo ver o título "([^"]*)"$/ do |text|
  Then %{I should see "#{text}" within "#waDivTitulo"}
end

Dado /^que eu esteja na (.+)$/ do |page_name|
  Given %{I am on #{page_name}}
end

Quando /^eu vou para (.+)$/ do |page_name|
  When %{I go to #{page_name}}
end

Quando /^eu aperto "([^"]*)"$/ do |button|
  When %{I press "#{button}"}
end

Quando /^eu clico "([^"]*)"$/ do |link|
  When %{I follow "#{link}"}
end

Quando /^eu clico "([^"]*)" dentro de "([^"]*)"$/ do |link, parent|
  When %{I follow "#{link}" within "#{parent}"}
end

Quando /^eu preencho "([^"]*)" com "([^"]*)"$/ do |field, value|
  field = get_field(field)
  When %{I fill in "#{field}" with "#{value}"}
end

Quando /^eu preencho o campo \(JS\) "([^"]*)" com "([^"]*)"$/ do |field, value|
  field = get_field(field)
  page.execute_script("$('##{field}').val('#{value}')")
end

Quando /^eu preencho "([^"]*)" para "([^"]*)"$/ do |value, field|
  When %{I fill in "#{value}" for "#{field}"}
end

Quando /^eu preencho o seguinte:$/ do |fields|
  When %{I fill in the following:}, fields
end

Quando /^eu seleciono "([^"]*)" de "([^"]*)"$/ do |value, field|
  field = get_field(field)
  When %{I select "#{value}" from "#{field}"}
end

Quando /^eu seleciono "([^"]*)" como a data e a hora$/ do |time|
  When %{I select "#{time}" as the date and time}
end

Quando /^eu seleciono "([^"]*)" como a data e a hora "([^"]*)"$/ do |datetime, datetime_label|
  When %{I select "#{datetime}" as the "#{datetime_label}" date and time}
end

Quando /^eu seleciono "([^"]*)" como a hora$/ do |time|
  When %{I select "#{time}" as the time}
end

Quando /^eu seleciono "([^"]*)" como a hora "([^"]*)"$/ do |time, time_label|
  When %{I select "#{time}" as the "#{time_label}" time}
end

Quando /^eu seleciono "([^"]*)" como a data$/ do |date|
  When %{I select "#{date}" as the date}
end

Quando /^eu seleciono "([^"]*)" como a data "([^"]*)"$/ do |date, date_label|
  When %{I select "#{date}" as the "#{date_label}" date}
end

Quando /^eu seleciono "([^"]*)" como "([^"]*)"$/ do |date, date_label|
  When %{I select "#{date}" as the "#{date_label}" date}
end

Quando /^eu marco "([^"]*)"$/ do |field|
  When %{I check "#{field}"}
end

Quando /^eu desmarco "([^"]*)"$/ do |field|
  When %{I uncheck "#{field}"}
end

Quando /^eu escolho "([^"]*)"$/ do |field|
  When %{I choose "#{field}"}
end

Quando /^eu anexo o arquivo em "([^"]*)" a "([^"]*)"$/ do |path, field|
  When %{I attach the file "#{path}" to "#{field}"}
end

Então /^eu devo ver "([^"]*)"$/ do |text|
  Then %{I should see "#{text}"}
end

Então /^eu devo ver "([^"]*)" dentro de "([^"]*)"$/ do |text, selector|
  Then %{I should see "#{text}" within "#{selector}"}
end

Então /^eu devo ver \/([^\/]*)\/$/ do |regexp|
  Then %{I should see /#{regexp}/}
end

Então /^eu devo ver \/([^\/]*)\/ dentro de "([^"]*)"$/ do |regexp, selector|
  Then %{I should see /#{regexp}/ within "#{selector}"}
end

Então /^eu não devo ver "([^"]*)"$/ do |text|
  Then %{I should not see "#{text}"}
end

Então /^eu não devo ver na listagem "([^"]*)"$/ do |text|
  Then %{I should not see "#{text}" within ".dados"}
end

Então /^eu não devo ver "([^"]*)" dentro de "([^"]*)"$/ do |text, selector|
  Then %{I should not see "#{text}" within "#{selector}"}
end

Então /^eu não devo ver \/([^\/]*)\/$/ do |regexp|
  Then %{I should not see /#{regexp}/}
end

Então /^eu não devo ver \/([^\/]*)\/ dentro de "([^"]*)"$/ do |regexp, selector|
  Then %{I should not see /#{regexp}/ within "#{selector}"}
end

Então /^o campo "([^"]*)" deve conter "([^"]*)"$/ do |field, value|
  Then %{the "#{field}" field should contain "#{value}"}
end

Então /^o campo "([^"]*)" não deve conter "([^"]*)"$/ do |field, value|
  Then %{the "#{field}" field should not contain "#{value}"}
end

Então /^o checkbox "([^"]*)" deve estar marcado$/ do |label|
  Then %{the "#{label}" checkbox should be checked}
end

Então /^o checkbox "([^"]*)" não deve estar marcado$/ do |label|
  Then %{the "#{label}" checkbox should not be checked}
end

Então /^eu devo estar na (.+)$/ do |page_name|
  Then %{I should be on #{page_name}}
end

Então /^mostre-me a página$/ do
  Then %{show me the page}
end

Então /^eu devo ver o alert do valida campos e clico no ok/ do
  Then %{I should see "Preencha os campos indicados."}
  When %{I press "OK"}
end

Então /^eu devo ver o alert "([^"]*)" e clico no ok/ do |msg_alert|
  Then %{I should see "#{msg_alert}"}
  When %{I press "OK"}
end

Então /^eu devo ver o alert do confirmar exclusão e clico no ok/ do
  Then %{I should see "Confirma exclusão?"}
  When %{I press "OK"}
end

Então /^eu devo ver o alert do confirmar e clico no ok/ do
  When %{I press "OK"}
end

Quando /^eu espero (\d+) segundos$/ do |segundos|
  sleep segundos.to_i
end

Quando /^eu espero o campo "([^"]*)" ficar habilitado$/ do |field|
  field = find_field(field)
  1.upto(100) do
    break if (field[:disabled] == "false")
    sleep 0.1
  end
  field[:disabled].should == "false"
end

Quando /^eu saio do campo "([^"]*)"$/ do |field|
  field = find_field(field)
  page.execute_script("$('##{field[:id]}').blur()")
end

Então /^eu não devo ver a aba "([^"]*)"$/ do |titulo_aba|
  page.should_not have_xpath("//div[@id='abas']/div/a", :text => /#{titulo_aba}/i)
end

Então /^eu devo ver a aba "([^"]*)"$/ do |titulo_aba|
  page.should have_xpath("//div[@id='abas']/div/a", :text => /#{titulo_aba}/i)
end

Quando /^eu clico na aba "([^"]*)"$/ do |text|
   find(:xpath, "//div[@id='abas']/div/a", :text => /#{text}/i).click
end

Então /^o campo "([^"]*)" deve ter "([^"]*)" selecionado$/ do |field, value|
  field = find_field(field)
  option = field.find(:xpath, "//option[contains(text(), '#{value}')]")
  value=option.value
  Então %{o campo "#{field[:id]}" deve conter "#{value}"}
end

Dado /^que exista o evento "([^"]*)"$/ do |nome_evento|
   insert :evento do
     nome nome_evento
   end
end

Dado /^que exista o estabelecimento "([^"]*)"$/ do |nome|
   insert :estabelecimento do
     nome nome
     empresa :id => 1
   end
end

Dado /^que exista a área organizacional "([^"]*)"$/ do |nome_area|
   insert :areaorganizacional do
     nome nome_area
     empresa :id => 1
   end
end

Dado /^que exista a área organizacional "([^"]*)", filha de "([^"]*)"$/ do |nome_area, nome_area_mae|
   insert :areaorganizacional do
     nome nome_area
     empresa :id => 1
     areamae :areaorganizacional, :nome => nome_area_mae
   end
end

Dado /^que exista o cargo "([^"]*)"$/ do |nome_cargo|
   insert :cargo do
     nome nome_cargo
     nomemercado nome_cargo
     empresa :id => 1
   end
end

Dado /^que exista a tabela de reajuste "([^"]*)" na data "([^"]*)" aprovada "([^"]*)"$/ do |nome, data, aprovada|
   insert :tabelareajustecolaborador do
     nome nome
     data data
     aprovada aprovada
     empresa :id => 1
   end
end

Dado /^que exista a faixa salarial "([^"]*)" no cargo "([^"]*)"$/ do |faixa_nome, cargo_nome|
   insert :faixasalarial do
     nome faixa_nome
     cargo :nome => cargo_nome
   end
end

Dado /^que exista um historico para a faixa salarial "([^"]*)" na data "([^"]*)"$/ do |faixa_nome, faixa_data|
   insert :faixasalarialhistorico do
     data faixa_data
     tipo 3
     valor 500
     status 1
     faixasalarial :nome => faixa_nome
   end
end

Dado /^que exista uma avaliacao "([^"]*)"$/ do |avaliacao_titulo|
   insert :avaliacao do
     id 1
     titulo avaliacao_titulo
     tipomodeloavaliacao 'D'
     ativo true
     empresa :id => 1
   end
end

Dado /^que exista uma avaliacao de curso "([^"]*)"$/ do |avaliacaocurso_titulo|
   insert :avaliacaocurso do
     titulo avaliacaocurso_titulo
     tipo 'n'
   end
end

Dado /^que exista um usuario "([^"]*)"$/ do |usuario_nome|
   insert :usuario do
     nome usuario_nome
     acessosistema true
     superadmin false
   end
   insert :usuarioempresa do
     usuario :nome => usuario_nome
     empresa :id => 1
   end
end

Dado /^que exista um curso "([^"]*)"$/ do |curso_nome|
   insert :curso do
     nome curso_nome
     empresa :id => 1
   end
end

Dado /^que exista uma turma "([^"]*)" para o curso "([^"]*)"$/ do |turma_descricao, curso_nome|
   insert :turma do
     descricao turma_descricao
     curso :nome => curso_nome
     realizada false
     dataprevini '01/07/2011'
     dataprevfim '15/07/2011'
     empresa :id => 1
   end
end

Dado /^que exista uma empresa "([^"]*)"$/ do |empresa_nome|
   insert :empresa do
      nome empresa_nome
      acintegra false
      maxcandidatacargo 10
      exibirsalario true
   end
end

Dado /^que exista um papel "([^"]*)"$/ do |papel_nome|
   insert :papel do
      nome papel_nome
      codigo 'ROLE'
      ordem 1
      menu false
   end
end

Dado /^que exista um ambiente "([^"]*)" com o risco "([^"]*)"$/ do |ambiente_nome, risco_descricao|
   insert :ambiente do
      nome ambiente_nome
      empresa :nome => 'Empresa Padrão'
      estabelecimento :nome => 'Estabelecimento Padrão'
   end

   insert :historicoambiente do
      descricao ambiente_nome
      data '25/07/2011'
      tempoexposicao '8h'
      ambiente :nome => ambiente_nome
   end

   insert :risco do
      descricao risco_descricao
      empresa :nome => 'Empresa Padrão'
   end

   insert :riscoambiente do
      epceficaz true
      historicoambiente :descricao => ambiente_nome
      risco :descricao => risco_descricao
   end
end

Dado /^que exista o EPI "([^"]*)" da categoria "([^"]*)"$/ do |epi_nome, tipoepi_nome|
  insert :tipoepi do
    nome tipoepi_nome
    empresa :nome => 'Empresa Padrão'
  end

  insert :epi do
    nome epi_nome
    fardamento true
    tipoepi :nome => tipoepi_nome
    empresa :nome => 'Empresa Padrão'
  end
end

Dado /^que exista um colaborador "([^"]*)", da area "([^"]*)", com o cargo "([^"]*)" e a faixa salarial "([^"]*)"$/ do |colaborador_nome, areaorganizacional_nome, cargo_nome, faixasalarial_nome|
  insert :areaorganizacional do
    nome areaorganizacional_nome
    empresa :nome => 'Empresa Padrão'
    ativo true
  end

  insert :cargo do
    nome cargo_nome
    nomemercado cargo_nome
  end

  insert :faixasalarial do
    nome faixasalarial_nome
    cargo :nome => cargo_nome
  end

  insert :colaborador do
    nome colaborador_nome
    nomecomercial colaborador_nome
    dataadmissao '01/07/2011'
    desligado false
    conjugetrabalha true
    qtdfilhos 0
    sexo 'M'
    naointegraac true
    deficiencia 'N'
    respondeuentrevista true
    empresa :nome => 'Empresa Padrão'
  end

  insert :historicocolaborador do
    data '01/07/2011'
    colaborador :nome => colaborador_nome
    faixasalarial :nome => faixasalarial_nome
    areaorganizacional :nome => areaorganizacional_nome
    estabelecimento :id => 1
    motivo 'C'
    tiposalario 3
    salario 1000
    status 1
  end
end

Dado /^que exista um extintor localizado em "([^"]*)"$/ do |extintor_localizacao|
  insert :extintor do
    estabelecimento :nome => 'Estabelecimento Padrão'
    localizacao extintor_localizacao
    tipo '1'
    ativo true
    empresa :nome => 'Empresa Padrão'
  end
end

Dado /^que exista um medico coordenador "([^"]*)"$/ do |medico_nome|
  insert :medicocoordenador do
    nome medico_nome
    inicio '28/07/2011'
    empresa :nome => 'Empresa Padrão'
  end
end

Dado /^que exista uma funcao "([^"]*)" no cargo "([^"]*)"$/ do |funcao_nome, cargo_nome|
  insert :funcao do
    nome funcao_nome
    cargo :nome => cargo_nome
  end
end

Dado /^que exista um modelo de ficha medica "([^"]*)" com a pergunta "([^"]*)"$/ do |fichamedica_nome, pergunta|
  insert :questionario do
    titulo fichamedica_nome
    tipo 4
    liberado true
    anonimo false
    aplicarporaspecto false
    empresa :id => 1
  end

  insert :pergunta do
    id 1
    texto pergunta
    questionario :titulo => fichamedica_nome
    tipo 3
    ordem 1
    comentario false
  end

  insert :fichamedica do
    questionario :titulo => fichamedica_nome
    ativa true
  end
end

Dado /^que exista um afastamento "([^"]*)"$/ do |afastamento_descricao|
  insert :afastamento do
    descricao afastamento_descricao
    inss true
  end
end

Dado /^que todos os papeis estejam permitidos$/ do
   exec_sql "update parametrosdosistema set modulos = encode(cast(array_to_string(array(select id from papel order by id), ',') as bytea), 'base64');"
end

Dado /^que exista a etapa seletiva "([^"]*)"$/ do |etapaseletiva_nome|
   exec_sql "insert into etapaseletiva (id,nome,ordem,empresa_id) values(nextval('etapaseletiva_sequence'),'#{etapaseletiva_nome}', 1,  1);"
end

Dado /^que exista o motivo da solicitacao "([^"]*)"$/ do |motivosolicitacao_descricao|
   exec_sql "insert into motivosolicitacao (id,descricao) values(nextval('motivosolicitacao_sequence'),'#{motivosolicitacao_descricao}');"
end

def get_field field
  label = all(:xpath, "//label[contains(text(), '#{field}')]").select{|e| e.text.match("^\s*#{field}\:?")}.first
  field = label[:for] unless label.nil?
  field
end