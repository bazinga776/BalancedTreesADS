:%s/^[ ]\+/^I/
JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES =	\
	Bldg.java \
	MinHeapImpl.java \
	RedBTree.java \
	risingCity.java
default:classes

classes:$(CLASSES:.java=.class)

clean:
	$(RM) *.class
