last(E, [_|T]) :- last(E, T).
last(E, [H]) :- E == H.
last(_, []) :- false.

notlast(S, L) :- last(_, S) == L.

%append([], X, X).
%append([X|Y], Z, [X|W]) :- append (Y, Z, W).

%subseq(S, L) :- .

%sublist(S, L) :- .

%without(L, E, S) :- .

%permutation(L, M) :- .

%split(L,P,Q) :- .
