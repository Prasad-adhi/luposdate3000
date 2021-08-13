#!/bin/bash

#git clean -xdf
#./launcher.main.kts --setup --intellijMode=Disable --releaseMode=Enable
#./gradlew assemble

BASE_PATH="src/luposdate3000_simulator_iot/src/jvmMain/resources"
JSON_LOCATION="${BASE_PATH}/campus.json"
for q in Q1 Q2 Q3 Q4 Q5 Q6 Q7 Q8 Q0
do
JSON_QUERY="${BASE_PATH}/$q.json"
#for t in central distributed
for t in distributed
do
JSON_TOPOLOGY="${BASE_PATH}/$t.json"
for d in luposdate3000_by_key luposdate3000_by_id
do
JSON_DATABASE="${BASE_PATH}/$d.json"
cmd=$(./launcher.main.kts --run --mainClass=Launch_Simulator_Config --dryMode=Enable | grep exec | sed "s/exec :: //g")
echo $cmd $JSON_LOCATION $JSON_TOPOLOGY $JSON_QUERY $JSON_DATABASE
eval $cmd $JSON_LOCATION $JSON_TOPOLOGY $JSON_QUERY $JSON_DATABASE
done
done
done
