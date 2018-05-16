typeof(zero, int).
typeof(one, int).
typeof(two, int).
typeof(three, int).
typeof(times(X, Y), int) :- typeof(X, int), typeof(Y, int), !.
typeof(invoke1(F, X), R) :- typeof(X, A), typeof(F, fun1(A, R)), !.
typeof(invoke2(F, F2, X), R) :- typeof(X, A), typeof(F, fun1(A, R)), typeof(F2, fun1(A, R)), !.
typeof(F, fun1(A, R)) :- fundef1(F, _, A, B), typeof(B, R), !.
typeof(X, T) :- fundef1(_, X, T, _), !.


fundef1(triple, x, int, times(three, x)).
fundef1(twice, x, int, times(three, x)).

%Queries Results

%query for invoke1(triple, two)
%?- typeof(invoke1(triple, two), X).
%X = int.

%query for triple
%?- typeof(triple, X).
%X = fun1(int, int).

%query for twice
%?- typeof(twice, X).
%X = fun1(int, int).

%query for invoke2(twice, triple, two)
%?- typeof(invoke2(twice, triple, two), X).
%X = int.


