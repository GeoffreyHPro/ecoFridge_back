# EcoFridge - Back

This project allows to register your food in an account. You can save your food perishable and unperishable. Some informations are given to know which food is expired.... 
This project is to reduce food waste and optimize the management of your "fridge".

# 👩‍💻 Technologies

| Back - Spring Boot | version (works) | 
| --- | --- |
| java | 22 |
| maven| 3.9.6 |

# ⚡️ Standard Execution

```bash
cd back
mvn spring-boot:run
```

URL of Swagger: http://localhost:8080/swagger-ui/index.html

# ☁️ Docker Execution

To create image Docker, execute this command

```bash
docker build -t back_ecofridge .  
```

To execute application, execute this command. 8080:8080 is port and default port, so change it if you execute application in another port.

```bash
docker run -p 8080:8080 back_ecofridge 
```