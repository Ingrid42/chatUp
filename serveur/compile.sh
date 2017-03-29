find -name "*.java" > sources.txt
javac -Xlint -d classes -cp classes:libs/json-simple-1.1.1.jar @sources.txt
rm -rf sources.txt
