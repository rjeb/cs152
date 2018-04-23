%lab 20 Jason Thai and Vincient Diep
value(L, N) :- value(L, 1, N).
value([], _, 0).
value([X|Xs], S, N) :- P is X * S, Z is S + 1, value(Xs, Z, N1), N is P + N1.

flip(0, 1).
flip(1,2).
flip(2,3).
flip(3,4).

%flip(X, Y) :- Y is X + 1, Y < 5.

flipmove(F, T) :- append(A, [X|Xs], F), flip(X, Y), append(A, [Y|Xs], T).

move(F, T) :- flipmove(F, T), value(T,N), N < 32.

win(X) :- move(X,Y), not(win(Y)).

next(X,Y,S) :- move(X,Y), not(win(Y)), value(Y,S).
