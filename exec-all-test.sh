#!/bin/bash
./tool-clear-caches.sh
for r in Enable Disable
do
for i in Enable Disable
do
for s in Disable
do
./compile-module-all.main.kts --releaseMode=$r --inlineMode=$i --suspendMode=$s --dryMode=Disable --fastMode=Disable --intellijMode=Disable > all-test-$r-$i-$s.compile-log 2>&1
./exec-binary-test-suite-jvm.main.kts --releaseMode=$r --inlineMode=$i --suspendMode=$s > all-test-$r-$i-$s-Partitions.test-log 2>&1
./exec-binary-test-suite-jvm.main.kts --releaseMode=$r --inlineMode=$i --suspendMode=$s --noPartitions > all-test-$r-$i-$s-NoPartitions.test-log 2>&1
./exec-binary-test-suite-jvm.main.kts --releaseMode=$r --inlineMode=$i --suspendMode=$s --noPartitions --persistent > all-test-$r-$i-$s-NoPartitions.test-log 2>&1
done
done
done
