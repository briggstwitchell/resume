###############################
# Makefile for Workbook Program
###############################

JFLAGS = -nowarn -Xlint
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java


CLASSES = Main.java

default: classes

classes: $(CLASSES:.java=.class)

clean: 
	$(RM) *.class

