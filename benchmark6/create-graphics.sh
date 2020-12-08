#!/usr/bin/bash

for f in $(ls stat*csv)
do
sed "s/0.0,0.0/0.0001,0.0001/g" $f > ${f}1
done

for f in $(ls stat*csv | sed "s/_[^_]*$//g" | sort | uniq)
do

gnuplot << EOF
#set terminal png size 1920,1080
#set output 'graph$(echo $f | sed "s/\./_/g").png'
set terminal epslatex color size 9cm,6cm
set output 'graph$(echo $f | sed "s/\./_/g").tex'
#set colorsequence classic
set datafile separator ','
set notitle
set key right bottom
#set logscale x
#set logscale y
set xlabel 'occurences relative to dataset size'
set ylabel 'percentage of dataset'
#distribution function
set yrange [0.0001:1]
set xrange [0.0001:1]
plot '${f}_s.csv1' using 1:2 with histeps title "S" dashtype 1 linewidth 3 linecolor rgb 'red', \
     '${f}_p.csv1' using 1:2 with histeps title "P" dashtype 2 linewidth 3 linecolor rgb 'orange', \
     '${f}_o.csv1' using 1:2 with histeps title "O" dashtype 3 linewidth 3 linecolor rgb 'yellow', \
     '${f}_sp.csv1' using 1:2 with histeps title "SP" dashtype 4 linewidth 3 linecolor rgb 'green', \
     '${f}_so.csv1' using 1:2 with histeps title "SO" dashtype 5 linewidth 3 linecolor rgb 'cyan', \
     '${f}_po.csv1' using 1:2 with histeps title "PO" dashtype 6 linewidth 3 linecolor rgb 'violet'
EOF
done

rm *.csv1
