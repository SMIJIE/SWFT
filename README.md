# Project name - SWFT
## Project description:
System of Weight and Food Tracking. The customer selects food (name, count. proteins, fats, carbohydrates), 
which he ate (from the already prepared list) and choose amount. The customer can add his type of 
food (name, calories, count. proteins, fats, carbohydrates). If the Client has used the daily calories, 
the system will inform him about this and write how much the norm was exceeded. The norm take from the 
parameters of the client (age, height, weight, lifestyle, etc.).
# Developer:
Pavlo Zakusylo

# How to run project:
1. Istall maven http://www.apache-maven.ru/install.html
2. Select URL in src\main\resources\db.properties 'jdbc:mysql://62.80.166.79:3306/oldSWFT'
3. In project directory open PowerShell/CMD
4. Enter command "mvn tomcat7:run"
5. Open browser and follow the link http://localhost:8888/
6. Login: 'pavlo@mail.ua'   Password: 'qwerty'

