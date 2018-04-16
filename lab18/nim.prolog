append([], Ys, Ys).
append([X | Xs], Ys, [X | Zs]) :- append(Xs, Ys, Zs).

move(From, To) :- append(To, [o|_], From), append(To, To, X), append(From, [o|_], X).

win(X) :- move(X,Y), not(win(Y)).
