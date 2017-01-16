package com.fortes.rh.test.business.auditoria;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import org.jmock.MockObjectTestCase;

import com.fortes.security.auditoria.Audita;

public class ManagerAuditaTest extends MockObjectTestCase
{
	public void testMetodosAuditados() 
    {
    	Exception exception = null;
    	
    	try {
    		Class<?>[] callbacks = getClasses("com.fortes.rh.security.spring.aop.callback");
    		
    		for (Class<?> auditorCallback : callbacks) {
    			if ("".equals(auditorCallback.getSimpleName()) || auditorCallback.getSimpleName().equals("AuditorCallbackImpl") || auditorCallback.getName().startsWith("com.fortes.rh.security.spring.aop.callback.crud"))
    				continue;
    			
    			Class<?> manager = findManagerBusiness(auditorCallback.getSimpleName().replace("AuditorCallbackImpl", ""));    			
    			
    			if(manager == null || manager.getDeclaredMethods() == null)
    				manager = findManagerModel(auditorCallback.getSimpleName().replace("AuditorCallbackImpl", ""));
    			
    			Method[] metodosManager = manager.getDeclaredMethods();

    	    	Method[] metodosAuditorCallback = auditorCallback.getDeclaredMethods();
    	    	
    	    	Collection<String> nomeMetodosCallback = new ArrayList<String>();
    	    	for (Method methodCallback : metodosAuditorCallback) 
    	    		nomeMetodosCallback.add(methodCallback.getName());
    	
    	    	for (Method methodManager : metodosManager) 
    	    	{
    	    		if(methodManager.isAnnotationPresent(Audita.class))
  	    				assertTrue("" + methodManager.getName(),nomeMetodosCallback.contains(methodManager.getName()));
    			}
    	    	
    	    	String teste = "";
			}
	    	
    	} catch (Exception e) {
			exception = e;
			e.printStackTrace();
		}
		
		assertNull(exception);
    }
	
	/**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private Class<?>[] getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
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
    private List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();
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
    
    private Class<?> findManagerBusiness(String entityName) throws ClassNotFoundException, IOException {
    	Class<?>[] managers = getClasses("com.fortes.rh.business");
    	
    	for (Class<?> manager : managers) {
    		if (manager.isInterface() && manager.getSimpleName().replace("Manager", "").equals(entityName)) {
				return manager;
			}
		}
    	
    	return null;
    }
    
    private Class<?> findManagerModel(String entityName) throws ClassNotFoundException, IOException {
    	Class<?>[] managers = getClasses("com.fortes.rh.model");
    	
    	for (Class<?> manager : managers) {
    		if (manager.isInterface() && manager.getSimpleName().replace("Manager", "").equals(entityName)) {
				return manager;
			}
		}
    	
    	return null;
    }
}
