last(E, [_|T]) :- last(E, T).
last(E, [H]) :- E == H.
last(_, []) :- false.

%notlast(S, L) :- .

%subseq(S, L) :- .

%sublist(S, L) :- .

%without(L, E, S) :- .

%permutation(L, M) :- .

%split(L,P,Q) :- .
