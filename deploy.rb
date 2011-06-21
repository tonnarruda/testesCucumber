require 'rubygems'
require 'net/ssh'
require 'net/scp'

Net::SSH.start('10.1.3.48', 'web', :password => "123456") do |ssh|

	tomcat_dir = '/home/web/temp/apache-tomcat-6.0.29'
	webapps_dir = "#{tomcat_dir}/webapps"
	fortes_var = "export FORTES_HOME_COMERCIAL=/home/web/fortes/RHCOMERCIAL;export JAVA_HOME=/home/web/temp/jdk1.6.0_22;"

	ssh.exec!("#{fortes_var} sh #{tomcat_dir}/bin/shutdown.sh") do |channel, stream, data|
		puts data
	end
	
	ssh.exec!("rm -rf #{webapps_dir}/fortesrhcomercial") do |channel, stream, data|
		puts data
	end
	
	ssh.exec!("rm -rf #{webapps_dir}/fortesrhcomercial.war") do |channel, stream, data|
		puts data
	end
	
	ssh.scp.upload!('//10.1.254.1/public/Outros/PRONTO/FortesRH/versaoHomologacao/fortesrh.war', "#{webapps_dir}/fortesrhcomercial.war" ) do |channel, name, sent, total|
		print "\rUploading #{name}: #{(sent.to_f * 100 / total.to_f).to_i}%"
	end

	ssh.exec!("unzip #{webapps_dir}/fortesrhcomercial.war -d #{webapps_dir}/fortesrhcomercial") do |channel, stream, data|
		puts data
	end
	
	ssh.scp.upload!( StringIO.new('name=FORTES_HOME_COMERCIAL'), "#{webapps_dir}/fortesrhcomercial/WEB-INF/classes/fortes_home.properties" ) do |channel, name, sent, total|
		print "\rUploading #{name}: #{(sent.to_f * 100 / total.to_f).to_i}%"
	end
	
	ssh.exec!("#{fortes_var} sh #{tomcat_dir}/bin/startup.sh") do |channel, stream, data|
		puts data
	end
	
end