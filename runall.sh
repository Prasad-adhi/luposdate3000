#!/bin/bash
./run.sh > x
cat x | grep -e Exception -e Success -e Failed | sort | uniq -c
grep -e "Test: " -e Failed -e Success x | grep -B1 -e Failed -e Success

