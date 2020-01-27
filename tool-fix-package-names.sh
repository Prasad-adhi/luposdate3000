#!/bin/bash

changed=1

while [[ $changed == 1 ]]
do
	changed=0
	failed=$(./tool-gradle-build-without-tests-jvm.sh 2>&1 | grep "BUILD FAILED in ")
	if [ -z "$failed" ]
	then
		echo success
		for f in $(grep -rwl "^package" --include "*.kt" --exclude-dir=".git" --exclude-dir="korio" --exclude-dir="build*")
		do
		        f2=$(echo $f | sed "s-.*/src/-/-g")
		        f2=$(echo $f2 | sed "s-.*/main/-/-g")
		        f2=$(echo $f2 | sed "s-.*/test/-/-g")
		        f2=$(echo $f2 | sed "s-.*/kotlin/-/-g")
		        f2=$(echo $f2 | sed "s-.*/java/-/-g")
		        f2=$(echo $f2 | sed "s-/[^/]*\$--g")
			        f2=$(echo $f2 | sed "s-^/--g")
		        p=$(grep "package" $f)
		        p=$(echo $p | sed "s/package //g")
		        p=$(echo $p | sed "s-\.-/-g")
		        if [ "$f2" != "$p" ]
		        then
        		        echo "$f"
        		        echo "$f2"
        		        echo "$p"
				name=$(echo $f | sed "s-\.kt\$--g" | sed "s-[^/]*/--g")
				nimp=$(echo $f | sed "s-.*/kotlin/--g" | sed "s-.*/java/--g" | sed "s-\.kt\$--g" | sed "s-/-\.-g")
				npkg=$(echo $nimp | sed "s-\.[^\.]*\$--g")
				opkg=$(echo $p | sed "s-/-\.-g")
				oimp="${opkg}.${name}"
				echo "oldpkg:: $opkg"
				echo "oldimp:: $oimp"
				echo "newpkg:: $npkg"
				echo "newimp:: $nimp"
				echo $f
        		        echo ""
				echo "package $npkg" > tmp
				grep -v "^package " $f >> tmp
				mv tmp $f
				for f3 in $(grep -rl "^import ${oimp}\$")
				do
					cat $f3 | sed "s-import ${oimp}-import ${nimp}-g" > tmp
					mv tmp $f3
				done
			fi
		done
	fi
done
