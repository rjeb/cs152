last(E, [_|T]) :- last(E, T).
last(E, [H]) :- E = H.
last(_, []) :- false.

notlast([], [_]).
notlast([], []) :- false.
notlast([_], []) :- false.
notlast([H|T1], [H|T2]) :- notlast(T1, T2).

inverseCycle([H|T], RES) :- append([H], X, RES), inverseCycleHelper(T, X).

inverseCycleHelper([H|T], RES) :- last(X, [H|T]), notlast(RESTLST, [H|T]), inverseCycleHelper(RESTLST, X1), append([X], X1, RES).

inverseCycleHelper([H], [H]).

                     
inverse([H|T], RESLST) :- inverseCycle(H, X), inverse(T, TMPLST), append([X], TMPLST, RESLST).
inverse([], []).
