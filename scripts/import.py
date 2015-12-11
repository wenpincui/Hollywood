#!/usr/bin/env python

import csv
import MySQLdb

conn = MySQLdb.connect('localhost','root','password','small')

cursor = conn.cursor()

txt = csv.reader(file('../data/small/movies.csv'))
line = 0
for row in txt:
    if (line > 0):
        cursor.execute('insert into movies(movieId,title,genres)' 'values     (%s,"%s","%s")',row)
    line += 1

conn.commit()

txt = csv.reader(file('../data/small/ratings.csv'))
line = 0
for row in txt:
    if (line > 0):
        cursor.execute('insert into ratings(userId,movieId,rating, timestamp)' 'values (%s,%s,%s,%s)',row)
    line += 1

conn.commit()

txt = csv.reader(file('../data/small/tags.csv'))
line = 0
for row in txt:
    if (line > 0):
        cursor.execute('insert into tags(userId,movieId,tag, timestamp)' 'values (%s,%s,"%s",%s)',row)
    line += 1
conn.commit()

txt = csv.reader(file('../data/small/links.csv'))
line = 0
for row in txt:
    if (line > 0):
        cursor.execute('insert into links(movieId,imdbId,tmdbId)' 'values     (%s,%s,%s)',row)
        conn.commit()
    line += 1

conn.close()
