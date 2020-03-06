docker start sonarqube
java -jar dependencies/jacococli.jar report $(find -name "jacoco.*exec") --classfiles build/build_jvm_jvmS00LaunchJavaFuzzMain_commonS00ExecutionSequentialMain_commonS00TraceOffMain_commonS01HeapMain_commonS12DummyMain_commonS14ServerNoneMain_jvmS14ClientKtorTarget_commonS15DistributedMain/classes/kotlin/main/ --sourcefiles src/ --html html/coverage --xml coverage.xml
sonar-scanner

#view localhost:9000
