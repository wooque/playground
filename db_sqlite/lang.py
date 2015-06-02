import sqlite3
import random
import string
import os
import time

LETTERS = string.lowercase + string.uppercase + string.digits
re = random.Random()

def randstr(n):
    return "".join(re.choice(LETTERS) for _ in xrange(n))

try:
    os.remove('./lang.db')
except OSError:
    pass

conn = sqlite3.connect('./lang.db')
c = conn.cursor()
c.execute("create table lang (name text, desc text, num integer, percent real)")
conn.commit()

#data = [(randstr(10), randstr(10), re.randint(10, 5000000), re.uniform(0, 100)) for _ in xrange(1000000)]

data = [("Java", "Bla", 123445, 34535.345) for _ in xrange(1000000)]
begin = time.clock()

c.executemany("insert into lang values (?, ?, ?, ?)", data)
conn.commit()
conn.close()

end = time.clock()
print "Executing:", (end - begin), "s"
