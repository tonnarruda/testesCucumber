require 'rubygems'
require 'pg'

$db_name = "fortesrh"

def reload_db
  puts ""
  puts "Executando a limpeza e apagando todos os registros do Banco de Dados."
  conn = nil
  begin
    conn = PGconn.connect( :dbname => $db_name, :user => 'postgres', :host => 'localhost' )
    conn.exec("select alter_trigger(table_name, 'DISABLE') FROM information_schema.constraint_column_usage  where table_schema='public'  and table_catalog='#{$db_name}' group by table_name;")
    
    tables = conn.exec("SELECT table_name FROM information_schema.tables WHERE table_type = 'BASE TABLE' AND table_schema NOT IN ('pg_catalog', 'information_schema');")
    delete_tables = tables.map {|table| "delete from #{table['table_name']};"}.join()
    
    conn.exec delete_tables
    
    alter_table_sequence = tables.map {|table| "alter table #{table['table_name']} alter column id set default nextval('#{table['table_name']}_sequence');"}
    alter_table_sequence.each do |comando|
      begin
        conn.exec comando
      rescue Exception => e
      end
    end

    conn.exec("select alter_trigger(table_name, 'ENABLE') FROM information_schema.constraint_column_usage  where table_schema='public'  and table_catalog='#{$db_name}' group by table_name;")
    puts "A limpeza do Banco de Dados foi realizada com sucesso."
    popula_db conn
  ensure
    conn.finish if conn
  end
end

def popula_db conn
    puts "Aguarde.. Banco de dados sendo populado com os dados iniciais..."
    i = 0
    sql = "";
    File.readlines("./web/WEB-INF/metadata/create_data.sql").each do |linha|
        if linha =~ /^(set|select)/i
          sql << linha
        elsif linha =~ /( cid | codigoCBO | cidade | areaformacao )/i
          if linha =~ /^insert into cidade.*Fortaleza/i
            sql << linha
          elsif linha =~ /^insert into/i and i <= 6
            i+=1
            sql << linha
          elsif linha =~ /^alter table/i
            i = 0 
            sql << linha     
          end
        elsif linha =~ /^(alter table|insert into)/i
          sql << linha
        end
        
        if linha =~ /^alter table (.*) disable trigger all/i
          begin
            sql << conn.exec("select pg_catalog.setval('#{$1}_sequence',10000 , false);")
      	  rescue Exception => e
          end
        end
    end
    
    sql << "update  parametrosdosistema  set proximaversao = '2020-01-01';"
    
    conn.exec(sql);
    
    puts "Banco de dados populado com sucesso."
end

if ARGV[0] == '--start'
  reload_db
end