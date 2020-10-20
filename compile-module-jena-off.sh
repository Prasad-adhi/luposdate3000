#!/bin/bash
./generate-buildfile-module.kts "Luposdate3000_Jena_Wrapper_Off" "src/luposdate3000_jena_wrapper_off" "linuxX64" --inline --nosuspend --release
gradle jvmJar #build
gradle publishJvmPublicationToMavenLocal #publishToMavenLocal
mkdir build-cache
rm -rf build-cache/build-module-jena-off build-cache/src-module-jena-off .gradle
mv build build-cache/build-module-jena-off
mv src.generated build-cache/src-module-jena-off
