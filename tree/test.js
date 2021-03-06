function Node(val, l, r) {
    this.value = val;
    this.left = l;
    this.right = r;
}

function sumit(node) {
	if (node === null) return 0;
	return node.value + sumit(node.left) + sumit(node.right);
}

function expand(init, level) {
    if (level === 0) return null;
    return new Node(init, expand(init, level - 1), expand(init, level - 1));
}

function bench() {
    var start = new Date().getTime();
    for(var j = 1; j < 101; j++)
		for(var i = 1; i < 21; i++)
			print(i + ": " + sumit(expand(25 - i, i)));
    var end = new Date().getTime();
    print('Time: ' + (end - start) + ' ms');
}

bench();
