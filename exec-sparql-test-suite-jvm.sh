#!/bin/bash
export JAVA_OPTS="-Xmx30g"
pkill java
port="3030"
{
  echo 1.4.255-SNAPSHOT
  echo jvm
  echo SparqlTestSuite
  echo On
  echo Sequential
  echo Heap
  echo MultiMap
  echo BPlusTree
  echo Korio
  echo None
  echo On
  echo BTree
  echo BTree
  echo Count
  echo Empty
  echo 128
  echo 8
  echo 8
  echo 8
  echo false
  echo ECoverage.Disabled
  echo DontChange
  echo None
} | ./generate-buildfile.kts
./tool-gradle-build.sh
function execJvm
{
	export JAVA_HOME=/usr/lib/jvm/java-14-openjdk-amd64
	./build/executable > log/x 2>&1
}

{ { time execJvm ; } > log/c 2>&1 ;}
cd log
cat x | grep -e Exception -e Success -e Failed -e "Token unrecognized" -e "java.lang" -e "lupos.s1buildSyntaxTree.UnexpectedToken" -e "Error in the following line"|grep -v "<h1>Success</h1>"| sort | uniq -c | sed "s/kotlin.//g" | sed "s/java.lang.//g" >>c
grep -e "Test: " -e Failed -e Success x | grep -v "Failed requirement" |grep -v "<h1>Success</h1>"| grep -B1 -e Failed -e Success >> c
echo "diff c c2"
diff c c2
echo "diff a c"
diff a c
diff a c -y |grep "|" -B1|grep "Success.*|.*Failed" -B1
cd ..
