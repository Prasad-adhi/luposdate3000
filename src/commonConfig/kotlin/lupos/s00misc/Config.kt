package lupos.s00misc

/* this File is autogenerated by generate-buildfile.kts */
/* DO NOT MODIFY DIRECTLY */
typealias SanityCheck = SanityCheckOn

typealias CoroutinesHelperMutex = Lock
typealias CoroutinesHelper = CoroutinesHelperSequential
typealias MySetGeneric<T> = MySetGenericBinaryTree<T>
typealias MySetLong = MySetLongBinaryTree
typealias MySetInt = MySetIntBinaryTree
typealias MySetDouble = MySetDoubleBinaryTree
typealias MyMapIntGeneric<T> = MyMapIntGenericBTree<T>
typealias MyMapLongGeneric<T> = MyMapLongGenericBTree<T>
typealias MyMapLongInt = MyMapLongIntBTree
typealias MyMapIntInt = MyMapIntIntBTree
typealias MyMapDoubleInt = MyMapDoubleIntBTree

const val ARRAY_LIST_BLOCK_CAPACITY = 1024
const val B_TREE_BRANCHING_FACTOR = 512
val COVERAGE_MODE = ECoverage.Count
