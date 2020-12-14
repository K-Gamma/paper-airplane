BIN = /usr/lib/jvm/java-8-openjdk-amd64/bin/
JAVAC = $(BIN)javac
JAR = $(BIN)jar

SRCDIR = ./src
SRC = $(wildcard $(SRCDIR)/*.java)
BINDIR = ./bin
CLASS = $(wildcard $(BINDIR)/*.class)
OPTIONS = -Xlint:deprecation
JARFILE = paper-airplane.jar


.PHONY: all clean
all: clean jar

javac:
	$(JAVAC) $(OPTIONS) $(SRC) -d $(BINDIR)

jar: javac
	cd $(BINDIR) && $(JAR) cf $(JARFILE) ./*

clean:
	rm -rf $(CLASS) $(BINDIR)/$(JARFILE)
