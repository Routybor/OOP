SRC_DIR="src/main/java/ru/nsu/yakhimovich"
BUILD_DIR="build"
DOC_DIR="doc"

SOURCES="$SRC_DIR/Expression.java $SRC_DIR/ExpressionParser.java $SRC_DIR/Number.java $SRC_DIR/Variable.java \
          $SRC_DIR/Add.java $SRC_DIR/Sub.java $SRC_DIR/Mul.java $SRC_DIR/Div.java \
          $SRC_DIR/Main.java"

MAIN_CLASS="ru.nsu.yakhimovich.Main"

rm -rf "$BUILD_DIR" "$DOC_DIR"

mkdir -p "$BUILD_DIR" "$DOC_DIR"

javadoc -d "$DOC_DIR" $SOURCES

javac -d "$BUILD_DIR" $SOURCES

java -cp "$BUILD_DIR" "$MAIN_CLASS"
