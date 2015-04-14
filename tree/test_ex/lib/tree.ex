defmodule Tree do

  @compile [:native, {:hipe, [:o3]}]
  
  def expand(_, 0), do: nil
  
  def expand(init, level) do
    {init, expand(init, level - 1), expand(init, level - 1)}
  end

  def sumit(nil), do: 0
  
  def sumit({value, left, right}) do
    value + sumit(left) + sumit(right)
  end
  
  def start() do
    for _ <- 1..100, i <- 1..20 do
      result = sumit(expand(25 - i, i))
      IO.puts("#{i}: #{result}")
    end
  end
end
