defmodule TreeTest do
  use ExUnit.Case
  import Tree
  
  test "expanding 0 level" do
    assert expand(3, 0) == nil
  end
  
  test "expanding 1 level" do
    assert expand(3, 1) == {3, nil, nil}
  end
  
  test "expanding 3 level" do
    assert expand(2, 3) == {2,
                            {2, {2, nil, nil}, {2, nil, nil}},
                            {2, {2, nil, nil}, {2, nil, nil}}}
  end
  
  test "summing nil" do
    assert sumit(nil) == 0
  end
  
  test "summing node" do
    node = {3, nil, nil}
    assert sumit(node) == 3
  end
  
  test "summing tree" do
    tree = {2,
            {2, {2, nil, nil}, {2, nil, nil}},
            {2, {2, nil, nil}, nil}}
    assert sumit(tree) == 12
  end
end
