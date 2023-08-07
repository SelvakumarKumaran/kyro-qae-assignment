# kyro-qae-assignment

This project is implemented using the Selenium test automation tool with a TestNG framework. It follows Maven as a project management tool using POM (Page Object Model).

**Selenium:**
Selenium is a free and open-source test automation tool for web UI. It supports multiple languages (ex: Java, C#, python,..), multiple browsers (Chrome, Firefox, Edge, ..) and multiple platforms (Windows, Mac and Linux)

**TestNG:**
TestNG is a testing framework that will segregate the code using annotations. It will also help us to generate the report

**POM:** 
Page Object Model is a design pattern to create the object repositories for the web elements. In POM, individual class files will be created for each web page and the elements and methods related to the page will be captured in the class files

**Maven:**
Maven is a project management tool using POM. It has a centralized repository and we can download all the required dependencies, JARs from there by adding the details in the POM.xml file

**Pre-requisites:**
Download and install jdk 1.8 version and add the path in the environment variables.

**Steps to execute the scripts:**
1. Open 'test-xml' folder
2. Right-click on the 'testng.xml' file and select Run As -> TestNG Suite

**Reporting System:**
Both testNG and ExtentReport will be available.
Path: Expand test-output folder to view ExtentReportResults.html and emailable-report.html (testNG report).
