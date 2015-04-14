import ast
import _ast

AST_TYPES = set([t for t in _ast.__dict__.values() if type(t) == type])

def parse_node(node):
    node_type = type(node)
    if node_type == list:
        return [parse_node(f) for f in node]
    elif node_type in AST_TYPES:
        return {node_type.__name__:
                dict([(n, parse_node(v)) for (n,v) in ast.iter_fields(node)])}
    else:
        return node

def parse_source(filename):
    with open(filename) as sourcefile:
        source = sourcefile.read()
        parsed = ast.parse(source)
        return parse_node(parsed)

if __name__ == "__main__":
    print parse_source("test.py")
