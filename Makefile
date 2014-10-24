JAVAC ?= javac
JFLAGS = -source 1.5
CLASSDIR = build
SOURCES = $(shell find com -type f -name '*.java')
OBJECTS = $(SOURCES:.java=.class)

all: scramble-lib.jar unscramble.jar

scramble-lib.jar:
	$(JAVAC) $(JFLAGS) $(SOURCES)
	jar cf $@ $(OBJECTS)

unscramble.jar: Main.java
	$(JAVAC) $(JFLAGS) -cp . Main.java
	jar cf $@ $(OBJECTS) Main.class

clean:
	$(RM) $(shell find . -type f -name '*.class') *.jar