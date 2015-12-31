/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
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
package com.example;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

public class Resources {

    // Expose an entity manager using the resource producer pattern
    @PersistenceContext
    @Produces
    private EntityManager em;
    
	
	@Resource(mappedName="java:jboss/datasources/JRPostgisDS") 
	DataSource datasource;
	 
  

//    public EntityManager getEm() {
//		Connection conn;
//		try {
//			conn = datasource.getConnection();
//			DatabaseMetaData md = conn.getMetaData();
//			org.postgresql.jdbc42.Jdbc42Connection pgConn = (org.postgresql.jdbc42.Jdbc42Connection) md.getConnection();
//			pgConn.addDataType("geometry",Class.forName("org.postgis.PGgeometry"));
//			pgConn.addDataType("box3d",Class.forName("org.postgis.PGbox3d"));
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return em;
//	}

	@Produces
    public Logger getLogger(InjectionPoint ip) {
        String category = ip.getMember().getDeclaringClass().getName();
        return Logger.getLogger(category);
    }

    @Produces
    @RequestScoped
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

}
