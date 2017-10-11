/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.getset.calendarchurch.util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Resources {
	private static Map<String, Object> configOverrides = new HashMap<String, Object>();
	static {
		Map<String, String> env = System.getenv();
		for (String envName : env.keySet()) {
		    if (envName.contains("STR_CONEXAO_JDBC")) {
		        configOverrides.put("javax.persistence.jdbc.url", env.get(envName));    
		    }
		    if (envName.contains("USER_BD")) {
		    	configOverrides.put("javax.persistence.jdbc.user", env.get(envName));    
		    }
		    if (envName.contains("PASS_BD")) {
		    	configOverrides.put("javax.persistence.jdbc.password", env.get(envName));    
		    }
		}

	}
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("calendarChurch",configOverrides);

	@Produces
	@RequestScoped
	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void close(@Disposes EntityManager em) {
		em.close();
	}

	/*
	 * @Produces
	 * 
	 * @PersistenceContext(name = "calendarChurch") private EntityManager em;
	 */

	@Produces
	public Logger produceLog(InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}

	/*
	 * @Produces
	 * 
	 * @RequestScoped public FacesContext produceFacesContext() { return
	 * FacesContext.getCurrentInstance(); }
	 */

}
