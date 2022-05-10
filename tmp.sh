luposAssemble

/usr/lib/jvm/java-16-openjdk-amd64/bin/java \
-server \
-XX:+UnlockExperimentalVMOptions \
-Xmx100g \
-XX:+UseShenandoahGC \
-XX:ShenandoahUncommitDelay=1000 \
-XX:ShenandoahGuaranteedGCInterval=10000 \
-cp \
/root/.gradle/caches/modules-2/files-2.1/com.google.code.findbugs/jsr305/3.0.2/25ea2e8b0c338a877313bd4672d3fe056ea78f0d/jsr305-3.0.2.jar:/root/.gradle/caches/modules-2/files-2.1/com.google.code.java-allocation-instrumenter/java-allocation-instrumenter/3.3.0/e356fdc63fd88d764169a864c96c69cbc2ebe5eb/java-allocation-instrumenter-3.3.0.jar:/root/.gradle/caches/modules-2/files-2.1/com.google.errorprone/error_prone_annotations/2.3.2/d1a0c5032570e0f64be6b4d9c90cdeb103129029/error_prone_annotations-2.3.2.jar:/root/.gradle/caches/modules-2/files-2.1/com.google.guava/failureaccess/1.0.1/1dcf1de382a0bf95a3d8b0849546c88bac1292c9/failureaccess-1.0.1.jar:/root/.gradle/caches/modules-2/files-2.1/com.google.guava/guava/28.1-android/c2526f8fad32a65a6d7032dd8e9524eb276b108b/guava-28.1-android.jar:/root/.gradle/caches/modules-2/files-2.1/com.google.guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/b421526c5f297295adef1c886e5246c39d4ac629/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar:/root/.gradle/caches/modules-2/files-2.1/com.google.j2objc/j2objc-annotations/1.3/ba035118bc8bac37d7eff77700720999acd9986d/j2objc-annotations-1.3.jar:/root/.gradle/caches/modules-2/files-2.1/com.ionspin.kotlin/bignum-jvm/0.3.3/7d97aee4141677a74fa0da995be8a423e7bc4c12/bignum-jvm-0.3.3.jar:/root/.gradle/caches/modules-2/files-2.1/javax.annotation/javax.annotation-api/1.3.2/934c04d3cfef185a8008e7bf34331b79730a9d43/javax.annotation-api-1.3.2.jar:/root/.gradle/caches/modules-2/files-2.1/org.checkerframework/checker-compat-qual/2.5.5/435dc33e3019c9f019e15f01aa111de9d6b2b79c/checker-compat-qual-2.5.5.jar:/root/.gradle/caches/modules-2/files-2.1/org.codehaus.mojo/animal-sniffer-annotations/1.18/f7aa683ea79dc6681ee9fb95756c999acbb62f5d/animal-sniffer-annotations-1.18.jar:/root/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib-jdk7/1.7.255-SNAPSHOT/32fa29c0d8957cb4411e073c87a4a73ecf9d2c89/kotlin-stdlib-jdk7-1.7.255-SNAPSHOT.jar:/root/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib-jdk8/1.7.255-SNAPSHOT/88f55d43c1fb50cd6c36890e00b30a6a0636f17e/kotlin-stdlib-jdk8-1.7.255-SNAPSHOT.jar:/root/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib/1.7.255-SNAPSHOT/d2fb421fe13e7219c24a0ae98d8c4be0679d8f11/kotlin-stdlib-1.7.255-SNAPSHOT.jar:/root/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlinx/kotlinx-datetime-jvm/0.2.1/11f9a2e0cf44d986812d79ae78a545b6713971e5/kotlinx-datetime-jvm-0.2.1.jar:/root/.gradle/caches/modules-2/files-2.1/org.jetbrains/annotations/13.0/919f0dfe192fb4e063e7dacadee7f8bb9a2672a9/annotations-13.0.jar:/root/.gradle/caches/modules-2/files-2.1/org.ow2.asm/asm-analysis/7.2/b6e6abe057f23630113f4167c34bda7086691258/asm-analysis-7.2.jar:/root/.gradle/caches/modules-2/files-2.1/org.ow2.asm/asm-commons/7.2/ca2954e8d92a05bacc28ff465b25c70e0f512497/asm-commons-7.2.jar:/root/.gradle/caches/modules-2/files-2.1/org.ow2.asm/asm-tree/7.2/3a23cc36edaf8fc5a89cb100182758ccb5991487/asm-tree-7.2.jar:/root/.gradle/caches/modules-2/files-2.1/org.ow2.asm/asm-util/7.2/a3ae34e57fa8a4040e28247291d0cc3d6b8c7bcf/asm-util-7.2.jar:/root/.gradle/caches/modules-2/files-2.1/org.ow2.asm/asm/7.2/fa637eb67eb7628c915d73762b681ae7ff0b9731/asm-7.2.jar:/root/.m2/repository/simora/simora-jvm/0.0.1/simora-jvm-0.0.1.jar:src/luposdate3000_buffer_manager_inmemory/build/libs/luposdate3000_buffer_manager_inmemory-jvm-0.0.1.jar:src/luposdate3000_code_gen_test_00/build/libs/luposdate3000_code_gen_test_00-jvm-0.0.1.jar:src/luposdate3000_code_generator_shared/build/libs/luposdate3000_code_generator_shared-jvm-0.0.1.jar:src/luposdate3000_dictionary/build/libs/luposdate3000_dictionary-jvm-0.0.1.jar:src/luposdate3000_endpoint/build/libs/luposdate3000_endpoint-jvm-0.0.1.jar:src/luposdate3000_endpoint_launcher/build/libs/luposdate3000_endpoint_launcher-jvm-0.0.1.jar:src/luposdate3000_jena_wrapper_off/build/libs/luposdate3000_jena_wrapper_off-jvm-0.0.1.jar:src/luposdate3000_kv/build/libs/luposdate3000_kv-jvm-0.0.1.jar:src/luposdate3000_launch_simulator_config/build/libs/luposdate3000_launch_simulator_config-jvm-0.0.1.jar:src/luposdate3000_network_wrapper_java_sockets/build/libs/luposdate3000_network_wrapper_java_sockets-jvm-0.0.1.jar:src/luposdate3000_operator_arithmetik/build/libs/luposdate3000_operator_arithmetik-jvm-0.0.1.jar:src/luposdate3000_operator_base/build/libs/luposdate3000_operator_base-jvm-0.0.1.jar:src/luposdate3000_operator_factory/build/libs/luposdate3000_operator_factory-jvm-0.0.1.jar:src/luposdate3000_operator_logical/build/libs/luposdate3000_operator_logical-jvm-0.0.1.jar:src/luposdate3000_operator_physical/build/libs/luposdate3000_operator_physical-jvm-0.0.1.jar:src/luposdate3000_optimizer_ast/build/libs/luposdate3000_optimizer_ast-jvm-0.0.1.jar:src/luposdate3000_optimizer_distributed_query/build/libs/luposdate3000_optimizer_distributed_query-jvm-0.0.1.jar:src/luposdate3000_optimizer_logical/build/libs/luposdate3000_optimizer_logical-jvm-0.0.1.jar:src/luposdate3000_optimizer_physical/build/libs/luposdate3000_optimizer_physical-jvm-0.0.1.jar:src/luposdate3000_parser/build/libs/luposdate3000_parser-jvm-0.0.1.jar:src/luposdate3000_result_format/build/libs/luposdate3000_result_format-jvm-0.0.1.jar:src/luposdate3000_shared/build/libs/luposdate3000_shared-jvm-0.0.1.jar:src/luposdate3000_simulator_db/build/libs/luposdate3000_simulator_db-jvm-0.0.1.jar:src/luposdate3000_spa_client/build/libs/luposdate3000_spa_client-jvm-0.0.1.jar:src/luposdate3000_test/build/libs/luposdate3000_test-jvm-0.0.1.jar:src/luposdate3000_test_buffermanager/build/libs/luposdate3000_test_buffermanager-jvm-0.0.1.jar:src/luposdate3000_test_dictionary_encoding/build/libs/luposdate3000_test_dictionary_encoding-jvm-0.0.1.jar:src/luposdate3000_triple_store_id_triple/build/libs/luposdate3000_triple_store_id_triple-jvm-0.0.1.jar:src/luposdate3000_triple_store_manager/build/libs/luposdate3000_triple_store_manager-jvm-0.0.1.jar:src/luposdate3000_visualize_distributed_database/build/libs/luposdate3000_visualize_distributed_database-jvm-0.0.1.jar:src/luposdate3000_vk/build/libs/luposdate3000_vk-jvm-0.0.1.jar \
MainKt \
src/luposdate3000_simulator_db/src/jvmMain/resources/ontology/campusSOSAInternalID.json \
src/luposdate3000_simulator_db/src/jvmMain/resources/topology/Uniform16DB.json \
src/luposdate3000_simulator_db/src/jvmMain/resources/programDistribution/distributedWithQueryHops.json \
src/luposdate3000_simulator_db/src/jvmMain/resources/queries/SOSA_Queries.json \
src/luposdate3000_simulator_db/src/jvmMain/resources/dataDistribution/luposdate3000_by_id_S_all_collations.json \
src/luposdate3000_simulator_db/src/jvmMain/resources/evaluation.json \
src/luposdate3000_simulator_db/src/jvmMain/resources/luposdate3000.json \
src/luposdate3000_simulator_db/src/jvmMain/resources/luposdate3000_distribution_routing.json \
src/luposdate3000_simulator_db/src/jvmMain/resources/multicast/luposdate3000MulticastDisabled.json \
src/luposdate3000_simulator_db/src/jvmMain/resources/routing/routing_RPL_Fast.json \
src/luposdate3000_simulator_db/src/jvmMain/resources/luposdate3000_local_execution_enabled.json \
tmp.json

dot graph101.dot -Tpng -ograph101.png
