all: javadoc compile run

compile:
	javac -d build src/main/java/ru/nsu/yakhimovich/Blackjack.java \
              src/main/java/ru/nsu/yakhimovich/Deck.java \
              src/main/java/ru/nsu/yakhimovich/Hand.java \
              src/main/java/ru/nsu/yakhimovich/Card.java

run:
	java -cp build ru.nsu.yakhimovich.Blackjack

javadoc:
	javadoc -d doc src/main/java/ru/nsu/yakhimovich/Blackjack.java \
                src/main/java/ru/nsu/yakhimovich/Deck.java \
                src/main/java/ru/nsu/yakhimovich/Hand.java \
                src/main/java/ru/nsu/yakhimovich/Card.java

clean:
	rm -rf build doc

.PHONY: all compile run javadoc clean
