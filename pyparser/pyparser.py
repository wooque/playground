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
        return {'filename': filename, 'ast': parse_node(parsed)}

class IntC(object):
    pass

class FloatC(object):
    pass

class StringC(object):
    pass

Int = IntC()
Float = FloatC()
String = StringC()

def symbol_table(filename):
    st = [{'int': Int, 'float': Float, 'string': String}, 
          {'prog_name': None}]

    return st

def check_name_clash(name, symbol_table):
    for scope in symbol_table:
        if name in scope:
            print 'Name {} already defined'.format(name)
            return true
    else:
        return false

def check_source(source):
    st = symbol_table()
    # st[1] is program scope
    st[1]['prog_name'] = source['filename']
    

if __name__ == "__main__":
    print parse_source("test.py")['ast']
