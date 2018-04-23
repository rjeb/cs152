value(L, N) :- value(L, 1, N).
value([], _, 0).
value([X|Xs], S, N) :- P is X * S, Z is S + 1, value(Xs, Z, N1), N is P + N1.

flip(X, Y) :- Y is X + 1, Y < 5.

flipmove(F, T) :- append(T1, X, F), flip(X, Y), append(T1, Y, T).
