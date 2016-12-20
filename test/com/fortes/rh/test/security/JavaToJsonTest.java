package com.fortes.rh.test.security;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

import junit.framework.TestCase;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.processors.JsonValueProcessorMatcher;
import net.sf.json.util.CycleDetectionStrategy;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.MedicaoRisco;
import com.fortes.rh.security.spring.aop.ChaveNaEntidade;
import com.fortes.rh.util.DateUtil;
import com.fortes.security.auditoria.NaoAudita;

public class JavaToJsonTest extends TestCase {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		Class[] classes = JavaToJsonTest.getClasses("com.fortes.rh.model");
		for (Class clazz : classes) {
			boolean hasEntityAnnotation = clazz.isAnnotationPresent(Entity.class);
			if (hasEntityAnnotation) {
				JavaToJsonTest.tryToConvertToJson(clazz);
			}
		}
	}
	
	
	public static void testeComEntidades(String[] args) {
		
		Ambiente ambiente = new Ambiente();
		ambiente.setId(2L);
		ambiente.setNome("TriadWorks");
		
		Empresa empresa = new Empresa();
		empresa.setId(55L);
		
		ambiente.setEmpresa(empresa);
		
		List<MedicaoRisco> riscos = new ArrayList<MedicaoRisco>();
		riscos.add(new MedicaoRisco(17L, null, true, new Date()));
		riscos.add(new MedicaoRisco(1L, null, true, new Date()));
		
		ambiente.setMedicaoRiscos(riscos);
		
		JsonConfig jsonConfig = getJsonConfig();
		
		System.out.println(JSONSerializer.toJSON(ambiente, jsonConfig).toString(2));
	}


	private static JsonConfig getJsonConfig() {
		JsonConfig jsonConfig = new JsonConfig(); 
//		jsonConfig.setExcludes(new String[]{"chaveParaAuditoria"}); // exclui atributos
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.NOPROP); // evita referencia ciclica
		jsonConfig.setIgnoreJPATransient(true);
		jsonConfig.setIgnoreTransientFields(true);
		jsonConfig.addIgnoreFieldAnnotation(NaoAudita.class);
		jsonConfig.registerJsonValueProcessor(Date.class, 
			new JsonValueProcessor() {
				public Object processArrayValue(Object value, JsonConfig jsonConfig) {
					return this.process(value, jsonConfig);
				}
				public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
					return this.process(value, jsonConfig);
				}
				private Object process(Object value, JsonConfig config) {
					Date data = (Date) value;
					return DateUtil.formataDate(data, "dd/MM/yyyy HH:mm:ss");
				}
			});
		jsonConfig.registerJsonValueProcessor(AbstractModel.class, 
				new JsonValueProcessor() {
					public Object processArrayValue(Object value, JsonConfig jsonConfig) {
						return this.process(value, jsonConfig);
					}
					public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
						return this.process(value, jsonConfig);
					}
					private Object process(Object value, JsonConfig config) {
						if (value != null) {
							AbstractModel entity = (AbstractModel) value;
							return new JSONObject()
								.element("id", entity.getId())
								.element("chaveParaAuditoria", new ChaveNaEntidade(entity).procura());
						}
						return new JSONObject(true);
					}
				});
		jsonConfig.setJsonValueProcessorMatcher( 
				new JsonValueProcessorMatcher() {  
					@SuppressWarnings("unchecked")
					@Override
					public Object getMatch(Class target, Set matches) {  
						for(Object match : matches) {  
							if(((Class) match).isAssignableFrom(target)) {  
								return match;  
							}  
						}  
						return null;  
					}
				});
//		jsonConfig.registerJsonBeanProcessor(Empresa.class, 
//			new JsonBeanProcessor(){
//				public JSONObject processBean(Object bean, JsonConfig config) {
//					if (bean instanceof AbstractModel) {
//						AbstractModel entity = (AbstractModel) bean;
//						return new JSONObject()
//									.element("id", entity.getId())
//									.element("chaveParaAuditoria", entity.getChaveParaAuditoria());
//										
//					}
//					return new JSONObject().fromObject(bean);
//				}
//			});
//		jsonConfig.setJsonBeanProcessorMatcher( 
//			new JsonBeanProcessorMatcher() {  
//				@Override
//				public Object getMatch(Class target, Set matches) {  
//					for(Object match : matches) {  
//						if(((Class) match).isAssignableFrom(target)) {  
//							return match;  
//						}  
//					}  
//					return null;  
//				}
//			});
//		jsonConfig.setRootClass(Ambiente.class);
//		jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
//			public boolean apply(Object source, String name, Object value) {
//				if (source instanceof AbstractModel) {
//					if ("id".equals(name)
//							|| "chaveParaAuditoria".equals(name)) {
//						return false;
//					}
//				}
//				return true;
//			}
//		});
		return jsonConfig;
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public void testDeveriaTodasAsEntidadesSeremConvertidasParaJsonSemErros() throws ClassNotFoundException, IOException {
		Class[] classes = JavaToJsonTest.getClasses("com.fortes.rh.model");
		for (Class clazz : classes) {
			boolean hasEntityAnnotation = clazz.isAnnotationPresent(Entity.class);
			if (hasEntityAnnotation) {
				JavaToJsonTest.tryToConvertToJson(clazz);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private static boolean tryToConvertToJson(Class clazz) {
		
		JsonConfig jsonConfig = getJsonConfig();

		Object obj;
		try {
			obj = clazz.newInstance();
			String json = JSONObject.fromObject(obj, jsonConfig).toString(2);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Erro na classe: " + clazz.getName());
		}
		return true;
	}
	
	/**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	private static Class[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }
    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
	private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

}
