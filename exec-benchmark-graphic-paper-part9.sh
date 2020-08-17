#!/bin/bash
column_x=2
column_z=7
column_y=11

rm tmp/part_d* tmp/v*
gnuplot_terminal="set terminal epslatex"
gnuplot_terminal_ending="tex"
gnuplot_terminal="set terminal png size 1920,1080"
gnuplot_terminal_ending="png"
echo "${gnuplot_terminal}" > tmp/graph_9.plot
echo "set output 'graph_9.${gnuplot_terminal_ending}'" >> tmp/graph_9.plot
echo "set datafile separator ','" >> tmp/graph_9.plot
echo "set notitle" >> tmp/graph_9.plot
echo "set key inside right top" >> tmp/graph_9.plot
echo "set hidden3d" >> tmp/graph_9.plot
echo "set xlabel 'triples'" >> tmp/graph_9.plot
echo "set ylabel 'selectivity' " >> tmp/graph_9.plot
echo "set zlabel 'triples/second'" >> tmp/graph_9.plot
echo "set logscale x" >>tmp/graph_9.plot
echo "set view 45,300" >>tmp/graph_9.plot
echo "set isosamples 100,100" >>tmp/graph_9.plot
echo "log2(x) = log(x) / log(2)" >>tmp/graph_9.plot

echo "g1(x,z) = 149.05503915778687 + -2.185798113535027 * x + 7428.463130777173 * z + 192.12375413041286 * x * z" >>tmp/graph_9.plot
echo "g2(x,z) = 464.81978163282633 + 19.03966001304443 * x + 832.5546287364972 * z + 177.22796857794856 * x * z" >>tmp/graph_9.plot
echo "g3(x,z) = 460.5937222998722 + 51.9851686996316 * x + 1595.420118964041 * z + 122.06419684714913 * x * z" >>tmp/graph_9.plot
echo "g4(x,z) = 431.8190315110182 + 79.52812562101528 * x + 1292.4200033769368 * z + 108.9485976184602 * x * z" >>tmp/graph_9.plot
echo "g5(x,z) = 403.4338294736871 + 89.4994579986608 * x + 1441.6791704384602 * z + 74.47003970287184 * x * z" >>tmp/graph_9.plot
echo "g6(x,z) = 384.6310415024538 + 87.81415099186856 * x + 1581.205429376247 * z + 47.18238331389838 * x * z" >>tmp/graph_9.plot

echo "mapX(x) = log2(x)" >>tmp/graph_9.plot
echo "mapZ(z) = 1 / (1 + z)" >>tmp/graph_9.plot
echo "f1(x,z) = (z<40?0:1) + (x<2097152?0:1) < 1 ? -1 : g1(mapX(x),mapZ(z))" >>tmp/graph_9.plot
echo "f2(x,z) = (z<40?0:1) + (x<2097152?0:1) < 1 ? -1 : g2(mapX(x),mapZ(z))" >>tmp/graph_9.plot
echo "f3(x,z) = (z<40?0:1) + (x<2097152?0:1) < 1 ? -1 : g3(mapX(x),mapZ(z))" >>tmp/graph_9.plot
echo "f4(x,z) = (z<40?0:1) + (x<2097152?0:1) < 1 ? -1 : g4(mapX(x),mapZ(z))" >>tmp/graph_9.plot
echo "f5(x,z) = (z<40?0:1) + (x<2097152?0:1) < 1 ? -1 : g5(mapX(x),mapZ(z))" >>tmp/graph_9.plot
echo "f6(x,z) = (z<40?0:1) + (x<2097152?0:1) < 1 ? -1 : g6(mapX(x),mapZ(z))" >>tmp/graph_9.plot
echo "splot [1 : 100000000000] [0:120] [0:5000]\\" >> tmp/graph_9.plot
#for d in $(seq 1 6)
for d in 3
do
	v=$(( $d - 1))
        r=$(( ( ( $v % 6 ) * 256 ) / 6))
        g=$(( ( ( $v / 6 ) * 256 ) / 6))
        b="0"
        c=$(( ( ( $r * 256 ) + $g ) * 256 + $b ))
	c2=$(( 256*256*256 * $v / 24))

	cat tmp/all.csv | grep "q2,[^,]*,1,$d," | sed -E "s/q2,(.),/q2,0\1,/g" | sort > tmp/part_d${d}.csv
	echo "'tmp/part_d${d}.matrix2.csv' matrix nonuniform title '${d}' with linespoints lc ${d} pt 1, \\" >> tmp/graph_9.plot
	echo "f$d(x,y),\\" >> tmp/graph_9.plot
	cat tmp/part_d${d}.csv | sort -b -t ',' -k ${column_x},${column_x}n -k ${column_z},${column_z}n > tmp/xxx
	mv tmp/xxx tmp/part_d${d}.csv

	for x in $(cat tmp/part_d${d}.csv | cut -d ',' -f${column_x} | sort -n | uniq)
	do
		row=""
		for z in $(cat tmp/part_d${d}.csv | cut -d ',' -f${column_z} | sort -n | uniq)
		do
		row+=$(cat tmp/part_d${d}.csv \
				| awk "BEGIN { FS = \",\" } ; {if (\$${column_x} == \"$x\") print \$0;}" \
			| awk "BEGIN { FS = \",\" } ; {if (\$${column_z} == \"$z\") print \$0;}" \
			| cut -d ',' -f${column_y})
			row+=","
		done
		echo $row | sed "s/,\./,0./g" | sed "s/^\./0./g" >> tmp/part_d${d}.matrix.csv
	done
	cat tmp/part_d${d}.csv | cut -d ',' -f${column_x} | sort -n | uniq | tr "\n" "," > tmp/part_d${d}.matrix.rows.csv
	cat tmp/part_d${d}.csv | cut -d ',' -f${column_z} | sort -n | uniq | tr "\n" "," > tmp/part_d${d}.matrix.columns.csv
        echo "xxx,$(cat tmp/part_d${d}.csv | cut -d ',' -f${column_z} | sort -n | uniq| tr '\n' ',')" > tmp/part_d${d}.matrix2.csv
	for x in $(cat tmp/part_d${d}.csv | cut -d ',' -f${column_x} | sort -n | uniq)
	do
		row=$x
#		row=$(echo "1 / ( 1 + ${x})" | bc -l)
		row+=","
		for z in $(cat tmp/part_d${d}.csv | cut -d ',' -f${column_z} | sort -n | uniq)
		do
			tmp=$(cat tmp/part_d${d}.csv \
			| awk "BEGIN { FS = \",\" } ; {if (\$${column_x} == \"$x\") print \$0;}" \
			| awk "BEGIN { FS = \",\" } ; {if (\$${column_z} == \"$z\") print \$0;}" \
			| cut -d ',' -f${column_y})
			row+=$(echo "1 / ${tmp}" | bc -l)
			row+=","
		done
		echo $row | sed "s/,\./,0./g" | sed "s/^\./0./g" >> tmp/part_d${d}.matrix2.csv
	done
done
for f in $(find tmp -name "graph_9*.plot")
do
	gnuplot $f
done
mv graph_9* tmp
