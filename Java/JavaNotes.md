Check if Java is installed:

`$ java --version`

In my case I have OpenJDK installed, which is an open source Java Development Kit. One could install OracleJDK, which is targeted towards enterprise (releases are more stable and constant), but both OpenJDK and OracleJDK are maintained by Oracle engineers.

In order to compile the java code, i.e javac command, install:

`$ sudo apt-get install openjdk-11-jdk-headless`

Name of the public class has to be the same name as the .java file we're writing the program.

`$ javac BhaskaraCalculator.java`

A .class file will be created, which is kind of a binary file? (read about it)

To run our compiled file:

`$ java BhaskaraCalculator`



