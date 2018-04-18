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

subseqcheck([], _).
subseqcheck([H1|T1], [H1| T2]) :- subseqcheck(T1, T2).

subseq([], _).
subseq([H|T1], [H|T2]) :- subseq(T1, T2), subseqcheck(T1, T2).
subseq([H1|T1], [_|T2]) :- subseq([H1|T1], T2).

sublist([], _).
sublist([H|T1], [H|T2]) :- sublist(T1, T2).
sublist([H1|T1], [_|T2]) :- sublist([H1|T1], T2).

append([], X, X).
append([X|Y], Z, [X|W]) :- append(Y, Z, W).


%without(L, E, S) :- .
%below implements without taking out ALL INSTANCES of target element
%without([], _, []).
%without([R|T1], R, L) :- without(T1, R, L).
%without([H1|T1], R, [H1|T2]) :- without(T1, R, T2), dif(R, H1).

withouthelper([], _, []).
withouthelper(L, _, L) :- dif([], L).

without([R|T1], R, T1) :- withouthelper(T1, R, T1).
without([H1|T1], R, [H1|T2]) :- without(T1, R, T2), dif(R, H1).

contains(H, [H|_]).
contains(H, [_|T2]) :- contains(H, T2).

%permutation([H1|T1], L) :- permutation(T1, X), without(L, H1, X).
%permutation([], []).


split(L,P,Q) :- permutation(X1, L), sublist(P, L), sublist(Q, L), append(Q, P, X1).
