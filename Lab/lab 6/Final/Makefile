DIR  = build
JC   = javac
DOCC = javadoc
SBD	 = docs
OPTS = -g -d $(DIR) -cp $(DIR):junit-platform-console-standalone-1.7.1.jar

SRC     = Food.java HfxDonairExpress.java Menu.java UnitTests.java
CLASSES = $(patsubst %.java, $(DIR)/%.class, $(SRC) )
DOCS    = $(patsubst %.java, $(SBD)/%.html, $(SRC) )
TARGET  = hfxdonairexpress.jar


# Default target
all: $(TARGET)

# Class files
$(DIR)/%.class: %.java
	$(JC) $(OPTS) $<

# Documentation files
$(SBD)/%.html: %.java
	$(DOCC) $<

# Jar
$(TARGET): $(CLASSES)
	jar -cfm hfxdonairexpress.jar manifest.mf -C $(DIR) .

# Documentation
doc: $(DOCS)

# Test
test: $(TARGET)
	java -jar junit-platform-console-standalone-1.7.1.jar --class-path $(DIR) --scan-class-path
	
# Clean
clean: 
	rm $(DIR)/*.class $(SBD)/*.html
