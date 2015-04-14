-module(test_alt).
-export([start/0, expand/2, sumit/1]).

expand(_, 0) ->
	nil;
expand(Init, Level) ->
	{Init, expand(Init, Level - 1), expand(Init, Level - 1)}.

sumit(nil) ->
	0;
sumit({Value, Left, Right}) ->
	Value + sumit(Left) + sumit(Right).
	
start() ->
	lists:foreach(
		fun(_) -> 
		lists:foreach(
			fun(X) -> io:fwrite("~B: ~B~n ", [X, sumit(expand(25 - X, X))]) end,
			lists:seq(1,20,1)) end,
		lists:seq(1,100,1)),
	init:stop().
