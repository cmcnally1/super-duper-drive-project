# Super*Duper*Drive Cloud Storage
Super*Duper*Drive is a cloud storage application that provides the following to users:
1. **Simple File Storage:** Upload/download/remove files
2. **Note Management:** Add/update/remove text notes
3. **Password Management:** Save, edit, and delete website credentials.  


### The Back-End
The back-end for the application is built using Spring Boot with a MVC architecture. It uses Spring security to manage user access and has a custom authentication service to validate logins. Data is persisted in a SQL database and MyBatis is used to map the data to Java POJOs. Passwords encryption is implemented for storing user credentials.

### The Front-End
The front-end pages are simple UI templates that utilise Bootstrap. Thymeleaf to create these templates and to bind user input data with the back-end.

### Testing
This project was built with a Test Driven Development approach. Tests were developed using JUnit in conjunction with Selenium.
