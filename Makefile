all: *.java
	javac Main.java
	jar cvfe CS304App.jar Main *.class

run: all
	java -jar CS304App.jar
clean:
	rm -f *.class
	rm -f CS304App.jar
