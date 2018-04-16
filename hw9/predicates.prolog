last(E, [_|T]) :- last(E, T).
last(E, [H]) :- E == H.
last(_, []) :- false.

notlast([], [_]).
notlast([], []) :- false.
notlast([_], []) :- false.
notlast([H|T1], [H|T2]) :- notlast(T1, T2).

tail([_|[T]], [T]).
test([T], [_, [T]]).

%contains([H],[H]).
contains([H], [H|T]).
contains([H], [_|T]) :- contains([H], T).
%contains([H1|[T1]],[H2|[T2]]) :- contains([H1], [H2|T2]), contains([T1], [H2|T2]).

subseq([], []).
subseq([H], [H]).
subseq([H], [R]) :- contains([H], [R]).
%subseq([H1|[T1]], [H2, [T2]])


%append([], X, X).
%append([X|Y], Z, [X|W]) :- append (Y, Z, W).

%subseq(S, L) :- .

%sublist(S, L) :- .

%without(L, E, S) :- .

%permutation(L, M) :- .

%split(L,P,Q) :- .
