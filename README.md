Los comandos para compilar y ejecutar el código respectivamente son:

javac -cp "lib/gson-2.11.0.jar;src" -d bin src\Main.java
java --add-opens java.base/java.time=ALL-UNNAMED -cp "bin;lib/gson-2.11.0.jar" Main