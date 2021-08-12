import sys


def generate_queries():
    predicates = []
    prefixes = []
    with open(input_file, "r") as file:
        for line in file:
            if line[0] == "@":
                prefixes.append(line)
            else:
                predicates.append(line.split(" ")[1])

    unique_predicates_tmp = list(set(predicates))
    unique_predicates_tmp.sort()
    unique_predicates = []
    for predicate in unique_predicates_tmp:
        if predicate.split(":")[1] != '':
            unique_predicates.append(predicate)

    with open(output_directory + "dictionary", "w") as dictionary_file:
        for idx, predicate in enumerate(unique_predicates):
            dictionary_file.write(str(idx) + " " + predicate + "\n")

    sparql_prefixes = []
    for prefix in prefixes:
        tmp = prefix.split(" ")
        tmp[-1] = "\n"
        tmp[0] = "PREFIX"
        prefix = " ".join(tmp)
        sparql_prefixes.append(prefix)

    join_patterns = []
    if joins == "s" or joins == "a":
        join_patterns.append(["?x", "?x", "?x", "?o0", "?o1", "?o2"])
    if joins == "a":
        join_patterns.append(["?x", "?x", "?s2", "?o0", "?o1", "?x"])
        join_patterns.append(["?x", "?s1", "?x", "?o0", "?x", "?o2"])
        join_patterns.append(["?s0", "?x", "?x", "?x", "?o1", "?o2"])

        join_patterns.append(["?x", "?s1", "?s2", "?o0", "?x", "?x"])
        join_patterns.append(["?s0", "?x", "?s2", "?x", "?o1", "?x"])
        join_patterns.append(["?s0", "?s1", "?x", "?x", "?x", "?02"])

        join_patterns.append(["?s0", "?s1", "?x", "?x", "?x", "?o2"])
        join_patterns.append(["?s0", "?x", "?s2", "?x", "?o1", "?x"])
        join_patterns.append(["?x", "?s1", "?s2", "?o0", "?x", "?x"])
    if joins == "p" or joins == "a":
        join_patterns.append(["?s0", "?s1", "?s2", "?x", "?x", "?x"])

    lupos3000_query_params = ""
    python_ml_params = ""

    file_index = 0
    join_on = ""
    for p in range(len(join_patterns)):
        s0 = join_patterns[p][0]
        s1 = join_patterns[p][1]
        s2 = join_patterns[p][2]
        o0 = join_patterns[p][3]
        o1 = join_patterns[p][4]
        o2 = join_patterns[p][5]
        if joins != "p" and p == 0:
            join_on = "S"
        elif joins == "p" and p == 0:
            join_on = "P"
        elif joins == "a" and p == 10:
            join_on = "P"
        else:
            join_on = ""

        for i in range(len(unique_predicates)):
            for j in range(i + 1, len(unique_predicates)):
                for k in range(j + 1, len(unique_predicates)):

                    with open(output_directory +
                              "q" + join_on + str(file_index) + ".sparql", "w") as query:
                        for sparql_prefix in sparql_prefixes:
                            query.write(sparql_prefix)
                        query.write("\n")
                        query.write("SELECT * {\n")
                        query.write("    " + s0 + " " + unique_predicates[i] + " " + o0 + " .\n")
                        query.write("    " + s1 + " " + unique_predicates[j] + " " + o1 + " .\n")
                        query.write("    " + s2 + " " + unique_predicates[k] + " " + o2 + " .\n")
                        query.write("}\n")

                    with open(output_directory + "q" + join_on + str(file_index) + ".mlq", "w") as python_q_file:
                        bucket_list = [[s0]]
                        for a in [s1, s2, o0, o1, o2]:
                            for b in bucket_list:
                                if a in b:
                                    b.append(a)
                                    break
                            else:
                                bucket_list.append([a])

                        ids = {}
                        for idx, bucket in enumerate(bucket_list):
                            for variable in bucket:
                                ids[variable] = -(idx + 1)

                        python_q_file.write(str(ids[s0]) + "," + str(i) + "," + str(ids[o0]) + ";")
                        python_q_file.write(str(ids[s1]) + "," + str(j) + "," + str(ids[o1]) + ";")
                        python_q_file.write(str(ids[s2]) + "," + str(k) + "," + str(ids[o2]) + ";" + "\n")

                    python_ml_params += output_directory + "q" + join_on + str(file_index) + ".mlq" + ";"
                    lupos3000_query_params += output_directory + "q" + join_on + str(file_index) + ".sparql" + ";"

                    file_index += 1

        python_ml_params = python_ml_params[:-1]
        lupos3000_query_params = lupos3000_query_params[:-1]

        with open(output_directory + "luposdate3000_query_params", "w") as params_file:
            params_file.write(lupos3000_query_params)

        with open(output_directory + "python_ml_params", "w") as p_params_file:
            p_params_file.write(python_ml_params)

def generate_train_file():
    with open(input_file, "r") as benchmark_file:
        with open(output_directory + "train.me", "w") as train_file:
            for line in benchmark_file:
                tmp = line.split(" ")
                ml_query_file_string = tmp[0].split("/")
                tmp2 = ml_query_file_string[-1].split(".")[0]
                tmp3 = tmp2 + ".mlq"
                ml_query_file_string = ml_query_file_string[:-1]
                ml_query_file_string.append(tmp3)
                ml_query_file_string = "/".join(ml_query_file_string)
                with open(ml_query_file_string, "r") as ml_query_file:
                    for line2 in ml_query_file:
                        ml_query = line2[:-1]
                tmp[0] = ml_query
                tmp3 = " ".join(tmp)
                train_file.write(tmp3)




def print_error():
    print("Usage: ")
    print("Param 1: \"query\" (without \") generate query files from triple file")
    print("Param 1: \"train\" (without \") generate train file from benchmark")
    print("Param 2: query: complete path to triple file")
    print("Param 2: train: complete path to benchmark file")
    print("Param 3: output directory for files")
    print("Param 4: query: \"s\", \"o\" or \"a\": join on subject/object/all")

if __name__ == '__main__':

    try:
        query_or_train = sys.argv[1]
        input_file = sys.argv[2]
        output_directory = sys.argv[3]
    except:
        print_error()
        sys.exit()
    if query_or_train == "query":
        try:
            joins = sys.argv[4]
        except:
            print("Param 4: query: \"s\", \"o\" or \"a\": join on subject/object/all")
            sys.exit()

    if query_or_train == "query":
        generate_queries()
    elif query_or_train == "train":
        generate_train_file()
    else:
        print_error()
