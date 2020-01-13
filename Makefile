JFLAGS = -g
JC = javac
JVM = java
.SUFFIXES: .java .class
.java.class: 
	$(JC) $*.java

CLASSES = / Building.java / wayneRBT.java / wayneMinHeap.java / risingCity.java

default: classes

classes: $(CLASSES:.java=.class)

clean: 
	$(RM) *.class
