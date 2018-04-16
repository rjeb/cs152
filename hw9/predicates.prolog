last(E, [_|T]) :- last(E, T).
last(E, [H]) :- E == H.
last(_, []) :- false.

notlast([], [_]).
notlast([], []) :- false.
notlast([_], []) :- false.
notlast([H|T1], [H|T2]) :- notlast(T1, T2).

notfirst(T, [_|T]).

%tail([_|[T]], [T]).
%test([T], [_, [T]]).

contains([H], [H|_]).
contains([H], [_|T2]) :- contains([H], T2).

subseqcheck([], _).
subseqcheck([H1|T1], [H1| T2]) :- subseqcheck(T1, T2).

subseq([], _).
subseq([H|T1], [H|T2]) :- subseq(T1, T2), subseqcheck(T1, T2).
subseq([H1|T1], [_|T2]) :- subseq([H1|T1], T2).

sublist([], _).
sublist([H|T1], [H|T2]) :- subseq(T1, T2).
sublist([H1|T1], [_|T2]) :- subseq([H1|T1], T2).

append([], X, X).
append([X|Y], Z, [X|W]) :- append(Y, Z, W).


%without(L, E, S) :- .
without([], R,  _).
without([H|T1], R, [H|T2]) :- subseq(T1, T2), subseqcheck(T1, T2).
without([H1|T1], R, [_|T2]) :- subseq([H1|T1], T2).

%permutation(L, M) :- .

split(L,P,Q) :- append(P,Q,L).
