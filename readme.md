6 вариант проекта (Система учета книг)

 Пошаговая документация по установке и развертыванию приложения.

1. распаковать архив с приложением;
2. проинициализировать скрипты в pgAdmin4 "create_db_script.sql" и "create_script.sql" из пакета "/controller/src/main/resources/sql/";
3. Убедиться, что на текущей ЭВМ установлена java 23, в противном случае поменять в корневом "pom.xml" в properties <java.version> на нужную вам версию java; 
4. Ввести команду "./mvnw clean package" в пакете с приложением в Git Bash (в любой другой unix системе) для упаковки приложения;
5. Ввести команду в терминале приложения "java -jar \controller\target\controller-0.0.1-SNAPSHOT.jar" для развертывания приложения.


Скрипты по упаковке и развертывании указаны в файле "readme.md", а также в пакете "/controller/src/main/resources/bashScripts/"