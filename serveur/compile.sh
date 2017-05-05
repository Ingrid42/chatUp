find -name "*.java" > sources.txt
#findrule -name "*.java" > sources.txt
javac -Xlint -d classes -cp classes:libs/* @sources.txt
rm -rf sources.txt
