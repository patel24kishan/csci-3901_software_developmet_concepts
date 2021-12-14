DIR  = build
JC   = javac
DOCC = javadoc
OPTS = -g -d $(DIR) -cp $(DIR):junit-platform-console-standalone-1.7.1.jar

SRC     = Food.java HfxDonairExpress.java Menu.java
CLASSES = $(patsubst %.java, $(DIR)/%.class, $(SRC) )
DOCS    = $(patsubst %.java, %.html, $(SRC) )
TARGET  = hfxdonairexpress.jar


# Default target
all: $(TARGET)

# Class files
$(DIR)/%.class: %.java
	$(JC) $(OPTS) $<

# Documentation files
%.html: %.java
	$(DOCC) $<

# Jar
$(TARGET): $(CLASSES)
	jar -cfm hfxdonairexpress.jar manifest.mf -C $(DIR) .

# Documentation
doc: $(DOCS)

# Test
test: $(TARGET)
	java -jar junit-platform-console-standalone-1.7.1.jar --class-path $(DIR) --scan-class-path
