rental-site-admin: Hibernate Spatial with PostGIS and Custom User Type under Hibernate
======================================================================================
Author: James Rohrbach  
Level: Beginner  
Technologies: Hibernate 5 with Hibernate Spatial, PostGIS, CDI, JSF, JPA, EJB, JTA  
Summary: The `rental-site-admin` quickstart demonstrates how to create a Java EE 7 compliant application with Hibernate Spatial, PostGIS using  *CDI 1.2*,  *JPA 2.1*, *JTA 1.2*, *EJB 3.1* and *JSF 2.2*.  Based on `jboss-greeter` quickstart  
Target Product: Hibernate Spatial 5.0.5+  
Product Versions: EAP 7 Beta  
Source: <https://github.com/jjrohrb/rental-site-admin>    

What is it?
-----------

The `rental-site-admin` quickstart demonstrates Hibernate Spatial and PostGIS and a Custom User Type under Hibernate 5 while using  *CDI 1.2*,  *JPA 2.1*, *JTA 1.2*, *EJB 3.1* and *JSF 2.2* in Red Hat JBoss Enterprise Application Platform 7.

The table 'properties' has a column, 'geo_coordinates' which is a PostGIS 'geometry' type. 

There is also a column, 'feature_list' that is a Postgres-specific array, 'character varying[]'. The project contains a custom user type, StringArrayType that extends UserType that allows Hibernate to deal with the Postgres array type. 

The Property class shows '@Type' annotation on 'feature_list' as well as the '@Column' annotation on 'geo_coordinates' and 'feature_list' that map to the columns and columns' data types.

This project demonstrates using Hibernate 5 with Hibernate Spatial 5.0.5+ to create, read, update and delete values in the above described types.

System requirements
-------------------

The application this project produces is designed to be run on Red Hat JBoss Enterprise Application Platform (JBoss EAP) 7.

All you need to build this project is Java SDK 1.8 or later, Maven 3.0 or later.

This project uses Postgres 9.4 with PostGIS 2.x extension. See <http://www.postgresql.org> for information on PostgreSQL. 
Refer to <http://postgis.net/install> for instructions on installing PostGIS.

Prepare Database
----------------

1. Create the JBRentals database. Run the following sql: 

	`CREATE DATABASE "JBRentals";`
		
2. Switch to new database JBRentals and run the following statement: 

	`CREATE EXTENSION IF NOT EXISTS "postgis";`

3. Create the properties table. Run the following sql: 
		
	`CREATE TABLE properties (`  
	  `id bigint NOT NULL,`  
	  `name character varying(255),`  
	  `geo_coordinates geometry,`  
	  `feature_list character varying[],`  
	  `zoomlevel integer,`  
	  `CONSTRAINT properties_pkey PRIMARY KEY (id)`  
	 `);`
			
4. (Optional) Seed the table with data. The sql to insert data is located in the `src/main/resources/import.sql` file.
        
Install Postgres JDBC Driver and Postgis driver as Core Modules
--------------------------------------------- 
 
These instructions are based on <https://access.redhat.com/documentation/en-US/JBoss_Enterprise_Application_Platform/6/html/Administration_and_Configuration_Guide/Install_a_JDBC_Driver_as_a_Core_Module1.html>
 
1. Download the Postgres JDBC driver from <http://jdbc.postgresql.org/> - <https://jdbc.postgresql.org/download/postgresql-9.4.1207.jar>.

2. Compile the Postgis JDBC driver from source as described at <http://postgis.net> or download the jar at <http://mvnrepository.com/artifact/net.postgis/postgis-jdbc/2.2.0>.

3.  Create the file path structure under the *EAP_HOME*/modules/ directory - *EAP_HOME*/modules/org/postgresql/main/.

4. Copy the JDBC driver jars to the main/ subdirectory.

5. In the main/subdirectory, create a module.xml file with the following contents:

    `<?xml version="1.0" ?>`  
		`<module xmlns="urn:jboss:module:1.1" name="org.postgresql">`  
    		`<resources>`  
        		`<resource-root path="postgresql-9.4.1207.jar"/>`  
        		`<resource-root path="postgis-jdbc-2.2.0.jar"/>`  
    		`</resources>`  
    		`<dependencies>`  
        		`<module name="javax.api"/>`  
				`<module name="javax.transaction.api"/>`  
    		`</dependencies>`  
		`</module>` 
        
   
Start the JBoss EAP Server
-------------------------

1. Open a command line and navigate to the root of the JBoss EAP directory.
2. The following shows the command line to start the server with the default profile:

        For Linux:   bin/standalone.sh
        For Windows: bin\standalone.bat
             
Configure EAP 7 Datasource
---------------------------

A command line interface (CLI) script is located in the `src/main/resources/dsScript.cli` 

1. Modify the CLI script -
	a. change 'localhost:5432' to the correct host and port for your install of Postgres and
	b. insert the correct password or if using Vault, insert the correct encrypted string.
2. Open a terminal/command window and run the following substituting in the appropriate paths for *EAP_HOME*  and *PATH_TO_CLI_SCRIPT*.

	For Linux:
		$ *EAP_HOME*/bin/jboss-cli.sh --connect --file=*PATH_TO_CLI_SCRIPT*/dsScript.cli

	 For Windows: 
	 	C:\>*EAP_HOME*\bin\jboss-cli.bat --connect --file=*PATH_TO_CLI_SCRIPT*\dsScript.cli

Configure Maven
---------------

If you have not yet done so, you must [Configure Maven](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/CONFIGURE_MAVEN.md#configure-maven-to-build-and-deploy-the-quickstarts) before testing the quickstarts.
	 	

Build and Deploy the Quickstart
-------------------------

_NOTE: The following build command assumes you have configured your Maven user settings. If you have not, you must include Maven setting arguments on the command line. 

1. Make sure you have started the JBoss EAP server as described above.
2. Open a command prompt and navigate to the root directory of this quickstart.
3. Type this command to build and deploy the archive:

        mvn clean install wildfly:deploy

4. This will deploy `target/rental-site-admin.war` to the running instance of the server.


Access the application
---------------------

The application will be running at the following URL:  <http://localhost:8080/rental-site-admin/>.


Undeploy the Archive
--------------------

1. Make sure you have started the JBoss EAP server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. When you are finished testing, type this command to undeploy the archive:

        mvn wildfly:undeploy

