javadoc -d doc src/main/java/ru/nsu/yakhimovich/Blackjack.java \
            src/main/java/ru/nsu/yakhimovich/Deck.java \
            src/main/java/ru/nsu/yakhimovich/Hand.java \
            src/main/java/ru/nsu/yakhimovich/Card.java

javac -d build src/main/java/ru/nsu/yakhimovich/Blackjack.java \
            src/main/java/ru/nsu/yakhimovich/Deck.java \
            src/main/java/ru/nsu/yakhimovich/Hand.java \
            src/main/java/ru/nsu/yakhimovich/Card.java

java -cp build ru.nsu.yakhimovich.Blackjack
