#!/bin/bash

export JAVA_OPTS="-Xmx60g"
export JAVA_HOME=/usr/lib/jvm/java-14-openjdk-amd64
export LUPOS_HOME=/tmp/luposdate3000-test/
rm -rf $LUPOS_HOME build-cache/bin-effective
mkdir build-cache/bin-effective log
ln -s "$(pwd)/build-cache/bin/Luposdate3000_Buffer_Manager_Inmemory-jvm.jar" "$(pwd)/build-cache/bin-effective/Luposdate3000_Buffer_Manager_Inmemory-jvm.jar"
ln -s "$(pwd)/build-cache/bin/Luposdate3000_Dictionary_Inmemory-jvm.jar" "$(pwd)/build-cache/bin-effective/Luposdate3000_Dictionary_Inmemory-jvm.jar"
ln -s "$(pwd)/build-cache/bin/Luposdate3000_Endpoint-jvm.jar" "$(pwd)/build-cache/bin-effective/Luposdate3000_Endpoint-jvm.jar"
ln -s "$(pwd)/build-cache/bin/Luposdate3000_Operators-jvm.jar" "$(pwd)/build-cache/bin-effective/Luposdate3000_Operators-jvm.jar"
ln -s "$(pwd)/build-cache/bin/Luposdate3000_Optimizer-jvm.jar" "$(pwd)/build-cache/bin-effective/Luposdate3000_Optimizer-jvm.jar"
ln -s "$(pwd)/build-cache/bin/Luposdate3000_Parser-jvm.jar" "$(pwd)/build-cache/bin-effective/Luposdate3000_Parser-jvm.jar"
ln -s "$(pwd)/build-cache/bin/Luposdate3000_Result_Format-jvm.jar" "$(pwd)/build-cache/bin-effective/Luposdate3000_Result_Format-jvm.jar"
ln -s "$(pwd)/build-cache/bin/Luposdate3000_Shared-jvm.jar" "$(pwd)/build-cache/bin-effective/Luposdate3000_Shared-jvm.jar"
ln -s "$(pwd)/build-cache/bin/Luposdate3000_Test-jvm.jar" "$(pwd)/build-cache/bin-effective/Luposdate3000_Test-jvm.jar"
ln -s "$(pwd)/build-cache/bin/Luposdate3000_Triple_Store_All-jvm.jar" "$(pwd)/build-cache/bin-effective/Luposdate3000_Triple_Store_All-jvm.jar"
ln -s "$(pwd)/build-cache/bin/Luposdate3000_Triple_Store_Id_Triple-jvm.jar" "$(pwd)/build-cache/bin-effective/Luposdate3000_Triple_Store_Id_Triple-jvm.jar"

ln -s "$(pwd)/build-cache/bin/Luposdate3000_Endpoint_Java_Sockets-jvm.jar" "$(pwd)/build-cache/bin-effective/Luposdate3000_Endpoint_impl-jvm.jar"
ln -s "$(pwd)/build-cache/bin/Luposdate3000_Jena_Wrapper_Off-jvm.jar" "$(pwd)/build-cache/bin-effective/Luposdate3000_Jena_Wrapper_impl-jvm.jar"
ln -s "$(pwd)/build-cache/bin/Luposdate3000_Launch_Endpoint-jvm.jar" "$(pwd)/build-cache/bin-effective/Luposdate3000_Launch_impl.jar"

ln -s $(find ~/.gradle/caches/modules-2/files-2.1/com.soywiz.korlibs.krypto/krypto-jvm/1.9.1/ -name "krypto-jvm-1.9.1.jar") "$(pwd)/build-cache/bin-effective/krypto-jvm-1.9.1.jar"
ln -s $(find ~/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib/1.4.255-SNAPSHOT -name "kotlin-stdlib-1.4.255-SNAPSHOT.jar") "$(pwd)/build-cache/bin-effective/kotlin-stdlib.jar"

java -Xmx60g -cp $(printf %s: $(pwd)/build-cache/bin-effective/*.jar) MainKt $(hostname)

#curl -H "Content-Type: application/x-www-form-urlencoded" --data-binary "/mnt/luposdate-testdata/sp2b/1024/data0.n3" $(hostname):2324/import/turtle
#curl -H "Content-Type: application/x-www-form-urlencoded" --data-binary "@resources/sp2b/q10.sparql" $(hostname):2324/sparql/query
