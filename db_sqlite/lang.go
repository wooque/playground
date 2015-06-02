package main

import (
	"database/sql"
	_ "github.com/mattn/go-sqlite3"
    "math/rand"
    "os"
    "time"
    "fmt"
)

var letters = []rune("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789")

func RandSeq(n int) string {
    b := make([]rune, n)
    for i := range b {
        b[i] = letters[rand.Intn(len(letters))]
    }
    return string(b)
}

type Lang struct {
    name string
    desc string
    num int
    percent float64
}

func main() {

    os.Remove("./lang.db")

    db, _ := sql.Open("sqlite3", "./lang.db")
	defer db.Close()

	sqlStmt := "create table lang (id integer not null primary key, name text, desc text, num integer, percent real);"
	db.Exec(sqlStmt)

    data := make([]Lang, 1000000)
    for i := range data {
        data[i] = Lang{RandSeq(10), RandSeq(10), 10+rand.Intn(5000000), 100.0*rand.Float64()}
    }

    tx, _ := db.Begin()
    stmt, _ := tx.Prepare("insert into lang (name, desc, num, percent) values (?, ?, ?, ?)")
	defer stmt.Close()

    begin := time.Now()

    for _, l := range data {
		stmt.Exec(l.name, l.desc, l.num, l.percent)
	}

	tx.Commit()
    fmt.Println("Executing: ", time.Since(begin))
}
