### Spring Boot App to Remind About Task at Specified Interval

HTTP Endpoints are exposed to create, retrieve tasks. The application it self pushes notification to remind about task.
Works with **Spring Boot 2.3** and **Java 11**. 

The application is intended to be used a server for command line clients. So, Go client projects are available to create tasks and archive tasks. To keep the id's to be single or 2 digits max, when the application starts, it reindex the id's of the previous tasks.

 