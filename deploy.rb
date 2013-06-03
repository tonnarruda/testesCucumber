require 'rubygems'
require 'net/ssh'
require 'net/scp'
require 'yaml'
require './ruby/ssh.rb'

deploy_config = YAML::load_file('./deploy.yml')

deploy_config.select{|k,v| ARGV.include? k}.each_pair do |name, config|
	puts "Publicando \"#{name}\""
	
	connect config['host'], config['user'], config['password'] do |conn|

		tomcat_home = config['tomcat_home']
		fortes_var = config['environment'].map{|key,value| "export #{key}=#{value};"}.join if config['environment']
		app_path = "#{tomcat_home}/webapps/#{config['app_name']}"
		backup_folder = config['backup_folder']
		
		if backup_folder 
		  bkpfile = "#{backup_folder}/#{config['db_name']}_#{Time.now.strftime('%Y%m%d%H%M')}.sql"
		  puts "Gerando backup \"#{bkpfile}\""
		  conn.exec "pg_dump -U postgres #{config['db_name']} > #{bkpfile}"
		end
		
  	puts "Parando tomcat"
		conn.exec "#{fortes_var} sh #{tomcat_home}/bin/shutdown.sh"
		
  	puts "Apagando pasta da aplicacao"
		conn.exec "rm -rf #{app_path}"
		
  	puts "Apagando war aplicacao"
		conn.exec "rm -rf #{app_path}.war"

  	puts "Apagando pasta work da aplicacao"
    conn.exec "rm -rf #{tomcat_home}/work/*"
		
  	puts "Enviando war para o servidor da aplicacao"
		conn.upload config['repository_app'], "#{app_path}.war"
		#conn.exec "cp #{tomcat_home}/webapps/unifor.war #{app_path}.war"
		
		if config['fortes_home_properties'] || config['run_migrates']
			conn.exec "unzip #{app_path}.war -d #{app_path}", :output=>:none
		end
		
		if config['fortes_home_properties']	
			conn.create_file "#{app_path}/WEB-INF/classes/fortes_home.properties", :content=>config['fortes_home_properties']
		end
		
		if config['run_migrates']
      	puts "Executando migrates"
    		conn.upload 'migrate.rb', "#{app_path}/migrate.rb"
    		conn.exec "ruby #{app_path}/migrate.rb --db #{config['db_name']}"
    		conn.exec "rm -f #{app_path}/migrate.rb"
		end
		
  	puts "Iniciando tomcat"
		conn.exec "#{fortes_var} sh #{tomcat_home}/bin/startup.sh"
		
    puts "Publicacao em \"#{name}\" finalizada."
	end
end
