import psycopg2

conn = psycopg2.connect("dbname=bla user=vuk")
cur = conn.cursor()
insert = "insert into lang (name, dsc, num, percent) values (%s, %s, %s, %s)"

for _ in xrange(100000):
    cur.execute(insert, ('Java', 'Bla', 12344, 123.123))

conn.commit()

cur.close()
conn.close()

