require 'rubygems'
require 'pg'

$db_name = "fortesrh"

def reload_db
  puts "Limpando banco de dados, apagando todos os registros"
  conn = nil
  begin
    conn = PGconn.connect( :dbname => $db_name, :user => 'postgres')
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

    setval_sequence = tables.map {|table| "select pg_catalog.setval('#{table['table_name']}_sequence',10000 , false);"}
    setval_sequence.each do |comando|
      begin
        conn.exec comando
      rescue Exception => e
      end
    end

    conn.exec("select alter_trigger(table_name, 'ENABLE') FROM information_schema.constraint_column_usage  where table_schema='public'  and table_catalog='#{$db_name}' group by table_name;")
    
    popula_db conn
  ensure
    conn.finish if conn
  end
  puts "Limpeza do banco de dados realizada com sucesso"
end

def popula_db conn
    puts "Populando banco de dados com dados iniciais..."
    i = 0
    File.readlines("./web/WEB-INF/metadata/create_data.sql").each do |linha|
        if linha =~ /^(set|select)/i
          conn.exec(linha)
        elsif linha =~ /( cid | codigoCBO | cidade )/i
          if linha =~ /^insert into cidade.*Fortaleza/i
            conn.exec(linha)
          elsif linha =~ /^insert into/i and i <= 6
            conn.exec(linha)
            i+=1
          elsif linha =~ /^alter table/i
            conn.exec(linha)
            i = 0          
          end
        elsif linha =~ /^(alter table|insert into)/i
            conn.exec(linha)
        end
    end
    puts "Banco de dados populado com sucesso."
end

if ARGV[0] == '--start'
  reload_db
end