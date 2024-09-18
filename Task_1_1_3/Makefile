SRC_DIR = src/main/java/ru/nsu/yakhimovich
BUILD_DIR = build
DOC_DIR = doc

SOURCES = $(SRC_DIR)/Expression.java $(SRC_DIR)/Number.java $(SRC_DIR)/Variable.java \
          $(SRC_DIR)/Add.java $(SRC_DIR)/Sub.java $(SRC_DIR)/Mul.java $(SRC_DIR)/Div.java \
          $(SRC_DIR)/Main.java

MAIN_CLASS = ru.nsu.yakhimovich.Main

all: javadoc compile run

compile:
	javac -d $(BUILD_DIR) $(SOURCES)

run:
	java -cp $(BUILD_DIR) $(MAIN_CLASS)

javadoc:
	javadoc -d $(DOC_DIR) $(SOURCES)

clean:
	rm -rf $(BUILD_DIR) $(DOC_DIR)

.PHONY: all compile run javadoc clean
