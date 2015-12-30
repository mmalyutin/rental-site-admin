# rental-site-admin
The `rental-site-admin` quickstart demonstrates how to create a Java EE 7 compliant application with Hibernate Spatial, PostGIS using JSF 2.2, CDI 1.2, and AJAX-enabled RichFaces 4.5.
rental-site-admin: Hibernate Spatial with PostGIS
==================================================================
Author: James Rohrbach  
Level: Beginner  
Technologies: Hibernate 5 with Hibernate Spatial, PostGIS, CDI, JSF, RichFaces  
Summary: The `rental-site-admin` quickstart demonstrates how to create a Java EE 7 compliant application with Hibernate Spatial, PostGIS using JSF 2.2, CDI 1.2, and AJAX-enabled RichFaces 4.5.  
Target Product:   
Product Versions: EAP 7  
Source: <https://github.com/jjrohrb/rental-site-admin>  

What is it?
-----------




System requirements
-------------------

The application this project produces is designed to be run on Red Hat JBoss Enterprise Application Platform (JBoss EAP) 7.

All you need to build this project is Java SDK 1.8 or later, Maven 3.0 or later.

Configure Maven
---------------

If you have not yet done so, you must [Configure Maven](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/CONFIGURE_MAVEN.md#configure-maven-to-build-and-deploy-the-quickstarts) before testing the quickstarts.


Start the JBoss EAP Server
-------------------------

1. Open a command line and navigate to the root of the JBoss EAP directory.
2. The following shows the command line to start the server with the default profile:

        For Linux:   EAP_HOME/bin/standalone.sh
        For Windows: EAP_HOME\bin\standalone.bat


Build and Deploy the Quickstart
-------------------------




Access the application
---------------------

The application will be running at the following URL:  <http://localhost:8080/rental-site-admin/>.


Undeploy the Archive
--------------------

1. Make sure you have started the JBoss EAP server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. When you are finished testing, type this command to undeploy the archive:

        mvn jboss-as:undeploy

Run the Arquillian Functional Tests
-----------------------------------

This quickstart provides Arquillian functional tests as well. They are located in the functional-tests/ subdirectory under the root directory of this quickstart.
Functional tests verify that your application behaves correctly from the user's point of view. The tests open a browser instance, simulate clicking around the page as a normal user would do, and then close the browser instance.

To run these tests, you must build the main project as described above.

1. Open a command line and navigate to the root directory of this quickstart.
2. Build the quickstart WAR using the following command:

        mvn clean package

3. Navigate to the functional-tests/ directory in this quickstart.
4. If you have a running instance of the JBoss EAP server, as described above, run the remote tests by typing the following command:

        mvn clean verify -Parq-jbossas-remote

5. If you prefer to run the functional tests using managed instance of the JBoss EAP server, meaning the tests will start the server for you, type fhe following command:

        mvn clean verify -Parq-jbossas-managed


Run the Quickstart in Red Hat JBoss Developer Studio or Eclipse
-------------------------------------
You can also start the server and deploy the quickstarts or run the Arquillian tests from Eclipse using JBoss tools. For more information, see [Use JBoss Developer Studio or Eclipse to Run the Quickstarts](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/USE_JBDS.md#use-jboss-developer-studio-or-eclipse-to-run-the-quickstarts) 


Debug the Application
------------------------------------

If you want to debug the source code or look at the Javadocs of any library in the project, run either of the following commands to pull them into your local repository. The IDE should then detect them.

        mvn dependency:sources
        mvn dependency:resolve -Dclassifier=javadoc
