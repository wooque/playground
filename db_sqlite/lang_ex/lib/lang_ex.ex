defmodule LangEx do

  def prepare() do
    File.rm("lang.db")
    {:ok, db} = :esqlite3.open(String.to_char_list("lang.db"))
    create_str = """
                 create table lang 
                 (id integer primary key not null, 
                  name text, 
                  desc text, 
                  num integer, 
                  percent real);
                 """
    :ok = :esqlite3.exec(create_str, db)
    prep_str = """
               insert into lang 
               (name, desc, num, percent) 
               values (?1, ?2, ?3, ?4);
               """
    {:ok, stmt} = :esqlite3.prepare(prep_str, db)
    {db, stmt}
  end

  def test(db, stmt) do
    :esqlite3.exec("begin;", db)
    for _ <- 1..100000 do
      :esqlite3.bind(stmt, ["Java", "Bla", 12345, 2342.567])
      :esqlite3.step(stmt)
    end
    :esqlite3.exec("commit;", db)
  end

  def bench() do
    {db, stmt} = prepare()
    {ms, :ok} = :timer.tc(LangEx, :test, [db, stmt])
    IO.puts("Time: #{ms/1000000} s")
    :esqlite3.close(db)
  end
end
